package thu.declan.xi.server.resource;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.model.Admin;
import thu.declan.xi.server.service.AdminService;

/**
 *
 * @author declan
 */
@Path("admins")
@RolesAllowed({Constant.ROLE_ADMIN})
public class AdminResource extends BaseResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

	@Autowired
	private AdminService adminService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Admin createAdmin(@Valid Admin admin) throws ApiException {
		LOGGER.debug("==================== enter AdminResource createAdmin ====================");
		try {
			adminService.add(admin);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_DUPLICATE_ELEMENT) {
				throw new ApiException(403, devMsg, "用户名已被占用");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AdminResource createAdmin ====================");
		return admin;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Admin> getAdmins() throws ApiException {
		LOGGER.debug("==================== enter AdminResource getAdmins ====================");
		Admin selector = new Admin();
		List<Admin> admins = null;
		try {
			admins = adminService.getList(selector);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AdminResource getAdmins ====================");
		return new ListResponse(admins);
	}

	@GET
	@Path("/{adminId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Admin getAdmin(@PathParam("adminId") int adminId) throws ApiException {
		LOGGER.debug("==================== enter AdminResource getAdmin ====================");
		LOGGER.debug("adminId: " + adminId);
		Admin admin = null;
		try {
			admin = adminService.get(adminId);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "该管理员不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AdminResource getAdmin ====================");
		return admin;
	}

	@PUT
	@Path("/{adminId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Admin editAdmin(@PathParam("adminId") int adminId, Admin admin) throws ApiException {
		LOGGER.debug("==================== enter AdminResource editAdmin ====================");
		LOGGER.debug("adminId: " + adminId);
		if (currentAdmin().getRole() != Admin.Role.SUPER && currentAdmin().getId() != adminId) {
			throw new ApiException(401, "Admin Id not equal to authorized one", "权限不足");
		}
		try {
			admin.setId(adminId);
			adminService.update(admin);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "改管理员不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AdminResource editAdmin ====================");
		return admin;
	}

	@DELETE
	@Path("/{adminId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Admin deleteAdmin(@PathParam("adminId") int adminId) throws ApiException {
		LOGGER.debug("==================== enter AdminResource deleteAdmin ====================");
		LOGGER.debug("adminId: " + adminId);
		Admin admin = null;
		try {
			admin = adminService.delete(adminId);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "改管理员不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AdminResource deleteAdmin ====================");
		return admin;
	}
}
