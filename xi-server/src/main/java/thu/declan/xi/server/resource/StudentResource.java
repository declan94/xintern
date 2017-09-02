package thu.declan.xi.server.resource;

import java.util.List;
import java.util.Objects;
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
        acc = accRes.createAccount(acc);
        student.setAccount(acc);
        try {
            student.setAccountId(acc.getId());
            studentService.add(student);
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
            try {
                Student oldComp = studentService.get(studentId);
                if (studentId == 0) {
                    studentId = oldComp.getId();
                }
                if (oldComp.getId() != studentId) {
                    throw new ApiException(401, "Student Id not equal to authorized one", "权限不足");
                }
            } catch (ServiceException ex) {
                throw new ApiException(404, "Student not found", "学生id错误");
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
    public ListResponse<Student> getStudents() throws ApiException {
        LOGGER.debug("==================== enter StudentResource getStudents ====================");
        Student selector = new Student();
        List<Student> students = null;
        try {
            students = studentService.getList(selector);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave StudentResource getStudents ====================");
        return new ListResponse(students);
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
        try {
			if (studentId == 0) {
				student = studentService.getByAccountId(currentAccountId());
			} else {
				student = studentService.get(studentId);
			}
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

}
