package thu.declan.xi.server.resource;

import java.util.List;
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
import thu.declan.xi.server.model.Account.Role;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.model.News;
import thu.declan.xi.server.model.Pagination;
import thu.declan.xi.server.model.PointLog;
import thu.declan.xi.server.service.AccountService;

/**
 *
 * @author declan
 */
@Path("accounts")
@RolesAllowed({Constant.ROLE_ADMIN})
public class AccountResource extends BaseResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private AccountService accountService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Account createAccount(@Valid Account account) throws ApiException {
		LOGGER.debug("==================== enter AccountResource createAccount ====================");
//		if (account.getRole().equals(Role.ADMIN)) {
//			throw new ApiException(403, "access forbidden.", "不可以创建管理员账户");
//		}
		try {
			accountService.add(account);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_DUPLICATE_ELEMENT) {
				throw new ApiException(403, devMsg, "手机号已注册，请登录。");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AccountResource createAccount ====================");
		return account;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Account> getAccounts() throws ApiException {
		LOGGER.debug("==================== enter AccountResource getAccounts ====================");
		Account selector = new Account();
		List<Account> accounts = null;
		try {
			accounts = accountService.getList(selector);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AccountResource getAccounts ====================");
		return new ListResponse(accounts);
	}

	@GET
	@Path("/{accountId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("accountId") int accountId) throws ApiException {
		LOGGER.debug("==================== enter AccountResource getAccount ====================");
		LOGGER.debug("accountId: " + accountId);
		Account account = null;
		try {
			account = accountService.get(accountId);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "该管理员不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AccountResource getAccount ====================");
		return account;
	}
    
    @GET
    @Path("/{accountId}/pointLogs")
    @Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT, Constant.ROLE_COMPANY})
    public ListResponse<News> getPointLogs(@PathParam("accountId") int accountId,
            @QueryParam("pageIndex") Integer pageIndex,
			@QueryParam("pageSize") Integer pageSize) throws ApiException {
        LOGGER.debug("==================== enter AccountResource getPointLogs ====================");
        if (accountId == 0) {
            accountId = currentAccountId();
        }
        PointLog selector = new PointLog();
        selector.setAccountId(accountId);
        Pagination pagination = new Pagination(pageSize, pageIndex);
        List<PointLog> pls = null;
        try {
             pls = plogService.getList(selector, pagination);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AccountResource getPointLogs ====================");
        return new ListResponse(pls, pagination);
    }
	
	@PUT
    @Path("/{accountId}/wechat")
    @Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT, Constant.ROLE_COMPANY})
    public Account bindWechat(@PathParam("accountId") int accountId,
			@QueryParam("openid") String openid) throws ApiException {
        LOGGER.debug("==================== enter AccountResource bindWechat ====================");
        if (accountId == 0) {
            accountId = currentAccountId();
        }
        Account updater = new Account();
		updater.setId(accountId);
		updater.setWechat(openid);
		Account acc = null;
        try {
			accountService.update(updater);
			acc = accountService.get(accountId);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AccountResource bindWechat ====================");
        return acc;
    }

	@PUT
	@Path("/{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account editAccount(@PathParam("accountId") int accountId, Account account) throws ApiException {
		LOGGER.debug("==================== enter AccountResource editAccount ====================");
		LOGGER.debug("accountId: " + accountId);
		if (currentRole() != Account.Role.ADMIN && currentAccount().getId() != accountId) {
			throw new ApiException(401, "Account Id not equal to authorized one", "权限不足");
		}
		try {
			account.setId(accountId);
			accountService.update(account);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "该账号不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave AccountResource editAccount ====================");
		return account;
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT, Constant.ROLE_COMPANY})
	public Account logout() throws ApiException {
		return authService.logout();
	}			
    
    public void deleteAccount(int accountId) {
        accountService.delete(accountId);
    }

}

