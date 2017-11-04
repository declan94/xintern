package thu.declan.xi.server.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.model.Student;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.model.Pagination;
import thu.declan.xi.server.model.PointLog;
import thu.declan.xi.server.model.PointLog.PType;
import thu.declan.xi.server.model.Resume;
import thu.declan.xi.server.model.Salary;
import thu.declan.xi.server.service.ResumeService;
import thu.declan.xi.server.service.SalaryService;
import thu.declan.xi.server.service.StudentService;

/**
 *
 * @author declan
 */
@Path("students")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT})
public class StudentResource extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private ResumeService resumeService;
	
	@Autowired
	private SalaryService salaryService;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 学生账号注册
    public Student createStudent(@Valid Student student) throws ApiException {
        LOGGER.debug("==================== enter StudentResource createStudent ====================");
        Account acc = student.getAccount();
        acc.setRole(Account.Role.STUDENT);
        AccountResource accRes = new AccountResource();
        beanFactory.autowireBean(accRes);
        String pwd = acc.getPassword();
        acc = accRes.createAccount(acc);
        student.setAccount(acc);
        try {
            student.setAccountId(acc.getId());
            studentService.add(student);
            authService.login(acc.getPhone(), pwd, Account.Role.STUDENT);
            addPoint(PointLog.PType.REGISTER, acc.getId());
        } catch (ServiceException ex) {
            accRes.deleteAccount(acc.getId());
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave StudentResource createStudent ====================");
        return student;
    }

    @PUT
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    // 修改学生信息
    public Student editStudent(@PathParam("studentId") int studentId, Student student) throws ApiException {
        LOGGER.debug("==================== enter StudentResource editStudent ====================");
        LOGGER.debug("studentId: " + studentId);
        if (currentRole() != Account.Role.ADMIN) {
            if (studentId == 0) {
                studentId = currentEntityId();
            }
            if (currentEntityId() != studentId) {
                throw new ApiException(401, "Student Id not equal to authorized one", "权限不足");
            }
        }
        try {
            student.setId(studentId);
            studentService.update(student);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
                throw new ApiException(404, devMsg, "该学生不存在！");
            }
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave StudentResource editStudent ====================");
        return student;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_COMPANY})
    public ListResponse<Student> getStudents(@QueryParam("pageIndex") Integer pageIndex,
            @QueryParam("pageSize") Integer pageSize,
			@QueryParam("school") String school,
			@QueryParam("name") String name,
			@QueryParam("phone") String phone) throws ApiException {
        LOGGER.debug("==================== enter StudentResource getStudents ====================");
        Student selector = new Student();
		selector.setSchool(school);
		selector.setName(name);
		selector.setPhone(phone);
        List<Student> students = null;
        Pagination pagination = new Pagination(pageSize, pageIndex);
        try {
            students = studentService.getList(selector, pagination);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave StudentResource getStudents ====================");
        return new ListResponse(students, pagination);
    }

    @POST
    @Path("/login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student login(Account acc) throws ApiException {
        acc.setRole(Account.Role.STUDENT);
        acc = loginAccount(acc);
        try {
            Student stu = studentService.getByAccountId(acc.getId());
            stu.setAccount(acc);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            try {
                long refid = sdf.parse(date).getTime() / 1000;
                addPoint(PType.LOGIN, (int) refid);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(StudentResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            return stu;
        } catch (ServiceException ex) {
            handleServiceException(ex);
            return null;
        }
    }

    @POST
    @Path("login/wechat")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student wechatLogin(@QueryParam("openid") String openid) throws ApiException {
        try {
            Account acc = authService.wechatLogin(openid, Account.Role.STUDENT);
            Student stu = studentService.getByAccountId(acc.getId());
            stu.setAccount(acc);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            try {
                long refid = sdf.parse(date).getTime() / 1000;
                addPoint(PType.LOGIN, (int) refid);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(StudentResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            return stu;
        } catch (ServiceException ex) {
            handleServiceException(ex);
            return null;
        }
    }

    @GET
    @Path("/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("studentId") int studentId) throws ApiException {
        LOGGER.debug("==================== enter StudentResource getStudent ====================");
        LOGGER.debug("studentId: " + studentId);
        Student student = null;
        Account acc = null;
        try {
            if (studentId == 0) {
                studentId = currentEntityId();
                acc = accountService.get(currentAccountId());
            }
            student = studentService.get(studentId);
            student.setAccount(acc);
            if (!Account.Role.ADMIN.equals(currentRole()) && !Objects.equals(student.getAccountId(), currentAccountId())) {
                throw new ApiException(403, "Access Forbidden", "不允许获取其他学生信息！");
            }
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
                throw new ApiException(404, devMsg, "该学生不存在！");
            }
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave StudentResource getStudent ====================");
        return student;
    }

    @GET
    @Path("/{studentId}/resumes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ListResponse<Resume> getResumes(@PathParam("studentId") int studentId,
            @QueryParam("state") List<Resume.RState> states,
            @QueryParam("pageIndex") Integer pageIndex,
            @QueryParam("pageSize") Integer pageSize) throws ApiException {
        LOGGER.debug("==================== enter StudentResource getResumes ====================");
        if (studentId == 0) {
            studentId = currentEntityId();
        }
        Resume selector = new Resume();
        if (!states.isEmpty()) {
            selector.setQueryStates(states);
        }
        selector.setStuId(studentId);
        List<Resume> resumes = null;
        Pagination pagination = new Pagination(pageSize, pageIndex);
        try {
            resumes = resumeService.getList(selector, pagination);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave StudentResource getResumes ====================");
        return new ListResponse(resumes, pagination);
    }
	
	@GET
    @Path("/{studentId}/salaries")
    @Produces(MediaType.APPLICATION_JSON)
    public ListResponse<Salary> getStudentSalaries(@PathParam("studentId") int studentId,
			@QueryParam("state") List<Salary.SState> states,
            @QueryParam("pageIndex") Integer pageIndex,
            @QueryParam("pageSize") Integer pageSize) throws ApiException {
        LOGGER.debug("==================== enter StudentResource getStudentSalaries ====================");
        if (studentId == 0) {
            studentId = currentEntityId();
        }
        Salary selector = new Salary();
        if (!states.isEmpty()) {
            selector.setQueryStates(states);
        }
        selector.setStuId(studentId);
        List<Salary> salaries = null;
        Pagination pagination = new Pagination(pageSize, pageIndex);
        try {
            salaries = salaryService.getList(selector, pagination);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave StudentResource getStudentSalaries ====================");
        return new ListResponse(salaries, pagination);
    }
	
}
