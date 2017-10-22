package thu.declan.xi.server.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Account;

/**
 *
 * @author declan
 */
@Path("admins")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT})
public class AdminResource extends BaseResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 学生账号注册
	public Account createAdmin(@Valid Account acc) throws ApiException {
		LOGGER.debug("==================== enter AdminResource createAdmin ====================");
		Account sel = new Account();
		sel.setRole(Account.Role.ADMIN);
		if (accountService.getCount(sel) > 0) {
			throw new ApiException(403, "Already registered admin", "无权限");
		}
		acc.setRole(Account.Role.ADMIN);
		AccountResource accRes = new AccountResource();
		beanFactory.autowireBean(accRes);
		String pwd = acc.getPassword();
		acc = accRes.createAccount(acc);
		try {
			authService.login(acc.getPhone(), pwd, Account.Role.ADMIN);
		} catch (ServiceException ex) {
			accRes.deleteAccount(acc.getId());
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AdminResource createAdmin ====================");
		return acc;
	}

	@POST
	@Path("/login")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Account login(Account acc) throws ApiException {
		acc.setRole(Account.Role.ADMIN);
		acc = loginAccount(acc);
		return acc;
	}

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public ListResponse<Admin> getAdmins() throws ApiException {
//		LOGGER.debug("==================== enter AdminResource getAdmins ====================");
//		Admin selector = new Admin();
//		List<Admin> admins = null;
//		try {
//			admins = adminService.getList(selector);
//		} catch (ServiceException ex) {
//			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
//			LOGGER.debug(devMsg);
//			handleServiceException(ex);
//		}
//		LOGGER.debug("==================== leave AdminResource getAdmins ====================");
//		return new ListResponse(admins);
//	}
	
}
