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
import thu.declan.xi.server.model.Withdraw;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.model.Pagination;
import thu.declan.xi.server.service.WithdrawService;

/**
 *
 * @author declan
 */
@Path("withdraws")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT})
public class WithdrawResource extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawResource.class);

    @Autowired
    private WithdrawService withdrawService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Withdraw createWithdraw(@Valid Withdraw withdraw) throws ApiException {
        LOGGER.debug("==================== enter WithdrawResource createWithdraw ====================");
        if (currentRole() == Account.Role.STUDENT) {
            withdraw.setAccountId(currentAccountId());
        }
        withdraw.setState(Withdraw.WState.NEW);
        try {
            withdrawService.add(withdraw);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_VERIFY_FAILED) {
                throw new ApiException(403, devMsg, "可提现余额不足");
            }
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave WithdrawResource createWithdraw ====================");
        return getWithdraw(withdraw.getId());
    }

    @PUT
    @Path("/{withdrawId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Constant.ROLE_ADMIN})
    public Withdraw editWithdraw(@PathParam("withdrawId") int withdrawId, Withdraw withdraw) throws ApiException {
        LOGGER.debug("==================== enter WithdrawResource editWithdraw ====================");
        LOGGER.debug("withdrawId: " + withdrawId);
        Withdraw oldWd = getWithdraw(withdrawId);
        if (oldWd.getState() != Withdraw.WState.NEW) {
            throw new ApiException(403, "Withdraw state not new", "不能修改此提现申请");
        }
        withdraw.setValue(null);
        try {
            withdraw.setId(withdrawId);
            withdrawService.update(withdraw);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave WithdrawResource editWithdraw ====================");
        return withdraw;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_STUDENT})
    public ListResponse<Withdraw> getWithdraws(@QueryParam("pageIndex") Integer pageIndex,
            @QueryParam("pageSize") Integer pageSize,
			@QueryParam("state") Withdraw.WState state,
			@QueryParam("accountId") Integer accountId) throws ApiException {
        LOGGER.debug("==================== enter WithdrawResource getWithdraws ====================");
        if (currentRole() == Account.Role.STUDENT) {
            accountId = currentAccountId();
        }
        Withdraw selector = new Withdraw();
        selector.setAccountId(accountId);
        selector.setState(state);
        List<Withdraw> withdraws = null;
        Pagination pagination = new Pagination(pageSize, pageIndex);
        try {
            withdraws = withdrawService.getList(selector, pagination);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave WithdrawResource getWithdraws ====================");
        return new ListResponse(withdraws, pagination);
    }
    
    @GET
    @Path("/{withdrawId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Withdraw getWithdraw(@PathParam("withdrawId") int withdrawId) throws ApiException {
        LOGGER.debug("==================== enter WithdrawResource getWithdraw ====================");
        LOGGER.debug("withdrawId: " + withdrawId);
        Withdraw withdraw = null;
        try {
			withdraw = withdrawService.get(withdrawId);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
                throw new ApiException(404, devMsg, "该资讯不存在！");
            }
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave WithdrawResource getWithdraw ====================");
        return withdraw;
    }
    
	
}
