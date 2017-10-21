package thu.declan.xi.server.resource;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.model.Resume;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.model.Notification;
import thu.declan.xi.server.model.PointLog.PType;
import thu.declan.xi.server.model.Position;
import thu.declan.xi.server.model.Rate;
import thu.declan.xi.server.model.Resume.RState;
import thu.declan.xi.server.model.Student;
import thu.declan.xi.server.service.CompanyService;
import thu.declan.xi.server.service.PositionService;
import thu.declan.xi.server.service.RateService;
import thu.declan.xi.server.service.ResumeService;
import thu.declan.xi.server.service.StudentService;
import thu.declan.xi.server.task.AsyncTask;

/**
 *
 * @author declan
 */
@Path("resumes")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT, Constant.ROLE_COMPANY})
public class ResumeResource extends BaseResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResumeResource.class);

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private RateService rateService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private AsyncTask asyncTask;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 投简历
	@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT})
	public Resume createResume(@Valid Resume resume) throws ApiException {
		LOGGER.debug("==================== enter ResumeResource createResume ====================");
		if (Account.Role.STUDENT.equals(currentRole())) {
			resume.setStuId(currentEntityId());
		}
		try {
			resumeService.add(resume);
		} catch (ServiceException ex) {
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, "No such position", "职位不存在");
			}
			handleServiceException(ex);
		}
		if (Account.Role.STUDENT.equals(currentRole())) {
			addNoti(Notification.NType.RESUME, resume.getId(), Notification.TPL_RESUME_ADD);
		}
		LOGGER.debug("==================== leave ResumeResource createResume ====================");
		return resume;
	}

	@PUT
	@Path("/{resumeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Resume editResume(@PathParam("resumeId") int resumeId, Resume resume) throws ApiException {
		LOGGER.debug("==================== enter ResumeResource editResume ====================");
		LOGGER.debug("resumeId: " + resumeId);
		Resume oldRes = null;
		boolean curStu = true;
		Student stu = null;
		try {
			oldRes = resumeService.get(resumeId);
			switch (currentRole()) {
				case STUDENT:
					if (!Objects.equals(oldRes.getStuId(), currentEntityId())) {
						throw new ApiException(403, "Student Id not equal to authorized one", "权限不足");
					}
					curStu = true;
					break;
				case COMPANY:
					Position pos = positionService.get(oldRes.getPositionId());
					if (!pos.getCompanyId().equals(currentEntityId())) {
						throw new ApiException(403, "Company Id not equal to authorized one", "权限不足");
					}
					stu = studentService.get(oldRes.getStuId());
					curStu = false;
					break;
				default:
					break;
			}
		} catch (ServiceException ex) {
			throw new ApiException(404, "Resume not found", "简历id错误");
		}
		if (resume.getState() == RState.OFFERED || resume.getState() == RState.WORKING) {
			this.addPoint(PType.EMPLOY, resumeId);
		}
		if (resume.getCommentComp() != null || resume.getCommentStu() != null) {
			this.addPoint(PType.COMMENT, resumeId);
		}
		String compName = oldRes.getPosition().getCompany().getName();
		if (!curStu && stu != null) {
			switch (resume.getState()) {
				case CANCELED:
					if (oldRes.getState() == RState.NEW) {
						notiService.addNoti(stu.getAccountId(), Notification.NType.RESUME, resumeId, Notification.TPL_RESUME_CANCEL);
					} else {
						notiService.addNoti(stu.getAccountId(), Notification.NType.RESUME, resumeId, Notification.TPL_RESUME_CANCEL2, compName);
					}
					break;
				case WAIT_STU_CONFIRM:
					if (oldRes.getState() == RState.NEW) {
						notiService.addNoti(stu.getAccountId(), Notification.NType.RESUME, resumeId, Notification.TPL_RESUME_INTERVIEW, compName);
					} else if (resume.getInterviewTime() != null) {
						SimpleDateFormat fmt = new SimpleDateFormat("MM月dd日 HH时mm分");
						notiService.addNoti(stu.getAccountId(), Notification.NType.RESUME, resumeId, Notification.TPL_RESUME_TIME, compName, fmt.format(resume.getInterviewTime()));
					}
					break;
				case OFFERED:
					notiService.addNoti(stu.getAccountId(), Notification.NType.RESUME, resumeId, Notification.TPL_RESUME_JOIN, compName);
					break;
			}
		}
		try {
			resume.setId(resumeId);
			resumeService.update(resume);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "该职位不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave ResumeResource editResume ====================");
		return resume;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Resume> getResumes() throws ApiException {
		LOGGER.debug("==================== enter ResumeResource getResumes ====================");
		Resume selector = new Resume();
		List<Resume> resumes = null;
		try {
			resumes = resumeService.getList(selector);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave ResumeResource getResumes ====================");
		return new ListResponse(resumes);
	}

	@GET
	@Path("/{resumeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Resume getResume(@PathParam("resumeId") int resumeId) throws ApiException {
		LOGGER.debug("==================== enter ResumeResource getResume ====================");
		LOGGER.debug("resumeId: " + resumeId);
		Resume resume = null;
		try {
			resume = resumeService.get(resumeId);
			if (Account.Role.COMPANY.equals(currentRole())) {
				addPoint(PType.RESUME, resume.getId());
			}
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "该职位不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave ResumeResource getResume ====================");
		return resume;
	}

	@POST
	@Path("/{resumeId}/rate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constant.ROLE_STUDENT, Constant.ROLE_COMPANY})
	public Rate addRate(@PathParam("resumeId") int resumeId, Rate rate) throws ApiException {
		LOGGER.debug("==================== enter ResumeResource addRate ====================");
		Resume resume = getResume(resumeId);
		if (resume.getState() != Resume.RState.ENDED) {
			throw new ApiException(403, "Resume wrong state", "实习未结束");
		}
		if (Account.Role.STUDENT.equals(currentRole())) {
			rate.setDirection(Rate.Direction.STU_TO_COMP);
			if (!Objects.equals(resume.getStuId(), currentEntityId())) {
				throw new ApiException(401, "Stu id not match", "无权限");
			}
		} else {
			rate.setDirection(Rate.Direction.COMP_TO_STU);
			if (!Objects.equals(resume.getCompanyId(), currentEntityId())) {
				throw new ApiException(401, "Company id not match", "无权限");
			}
		}
		rate.setCompanyId(resume.getCompanyId());
		rate.setStuId(resume.getStuId());
		rate.setResumeId(resumeId);
		try {
			rateService.add(rate);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_UK_CONSTRAINT) {
				throw new ApiException(403, devMsg, "已经进行过评价！");
			}
			handleServiceException(ex);
		}
		if (rate.getDirection() == Rate.Direction.STU_TO_COMP) {
			asyncTask.refreshCompanyRate(rate.getCompanyId());
		} else {
			asyncTask.refreashStuRate(rate.getStuId());
		}
		LOGGER.debug("==================== leave ResumeResource addRate ====================");
		return rate;
	}

}
