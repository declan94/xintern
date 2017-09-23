package thu.declan.xi.server.resource;

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
import thu.declan.xi.server.model.PointLog.PType;
import thu.declan.xi.server.model.Position;
import thu.declan.xi.server.model.Resume.RState;
import thu.declan.xi.server.service.PositionService;
import thu.declan.xi.server.service.ResumeService;

/**
 *
 * @author declan
 */
@Path("resumes")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT})
public class ResumeResource extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResumeResource.class);

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private PositionService positionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 投简历
    public Resume createResume(@Valid Resume resume) throws ApiException {
        LOGGER.debug("==================== enter ResumeResource createResume ====================");
        if (Account.Role.STUDENT.equals(currentRole())) {
            resume.setStuId(currentEntityId());
        }
        try {
            resumeService.add(resume);
        } catch (ServiceException ex) {
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave ResumeResource createResume ====================");
        return resume;
    }

    @PUT
    @Path("/{resumeId}")
    @RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT, Constant.ROLE_STUDENT})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Resume editResume(@PathParam("resumeId") int resumeId, Resume resume) throws ApiException {
        LOGGER.debug("==================== enter ResumeResource editResume ====================");
        LOGGER.debug("resumeId: " + resumeId);
        try {
            Resume oldRes = resumeService.get(resumeId);
            switch (currentRole()) {
                case STUDENT:
                    if (!Objects.equals(oldRes.getStuId(), currentEntityId())) {
                        throw new ApiException(403, "Student Id not equal to authorized one", "权限不足");
                    }
                    break;
                case COMPANY:
                    Position pos = positionService.get(oldRes.getPositionId());
                    if (!pos.getCompanyId().equals(currentEntityId())) {
                        throw new ApiException(403, "Company Id not equal to authorized one", "权限不足");
                    }
                    break;
                default:
                    break;
            }
        } catch (ServiceException ex) {
            throw new ApiException(404, "Resume not found", "简历id错误");
        }
        if (resume.getState() == RState.OFFERED || resume.getState() == RState.WORKING) {
            addPoint(PType.EMPLOY, resumeId);
        }
        if (resume.getCommentComp() != null || resume.getCommentStu() != null) {
            addPoint(PType.COMMENT, resumeId);
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
            addPoint(PType.RESUME, resume.getId());
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

}
