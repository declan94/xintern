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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.model.Company;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.model.Pagination;
import thu.declan.xi.server.model.Position;
import thu.declan.xi.server.service.CompanyService;
import thu.declan.xi.server.service.PositionService;

/**
 *
 * @author declan
 */
@Path("companies")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_COMPANY})
public class CompanyResource extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyResource.class);

    @Autowired
    private CompanyService companyService;
	
	@Autowired
	private PositionService positionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    // 企业账号注册
    public Company createCompany(@Valid Company company) throws ApiException {
        LOGGER.debug("==================== enter CompanyResource createCompany ====================");
        Account acc = company.getAccount();
        acc.setRole(Account.Role.COMPANY);
        AccountResource accRes = new AccountResource();
        beanFactory.autowireBean(accRes);
        acc = accRes.createAccount(acc);
        company.setAccount(acc);
        try {
            company.setAccountId(acc.getId());
            companyService.add(company);
        } catch (ServiceException ex) {
            accRes.deleteAccount(acc.getId());
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave CompanyResource createCompany ====================");
        return company;
    }

    @PUT
    @Path("/{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
    // 修改企业信息
    public Company editCompany(@PathParam("companyId") int companyId, Company company) throws ApiException {
        LOGGER.debug("==================== enter CompanyResource editCompany ====================");
        LOGGER.debug("companyId: " + companyId);
        if (currentRole() == Account.Role.COMPANY) {
			if (companyId == 0) {
				companyId = currentEntityId();
			} else if (companyId != currentEntityId()) {
				throw new ApiException(403, "Company Id not equal to authorized one", "权限不足");
			}
        }
        try {
            company.setId(companyId);
            companyService.update(company);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
                throw new ApiException(404, devMsg, "该公司不存在！");
            }
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave CompanyResource editCompany ====================");
        return company;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ListResponse<Company> getCompanies() throws ApiException {
        LOGGER.debug("==================== enter CompanyResource getCompanys ====================");
        Company selector = new Company();
        List<Company> companys = null;
        try {
            companys = companyService.getList(selector);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave CompanyResource getCompanys ====================");
        return new ListResponse(companys);
    }
	
	@POST
	@Path("/login")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Company login(Account acc) throws ApiException {
		acc.setRole(Account.Role.COMPANY);
		acc = loginAccount(acc);
		try {
			Company comp = companyService.getByAccountId(acc.getId());
			comp.setAccount(acc);
			return comp;
		} catch (ServiceException ex) {
			handleServiceException(ex);
			return null;
		}
	}

    @GET
    @Path("/{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_COMPANY, Constant.ROLE_STUDENT})
    public Company getCompany(@PathParam("companyId") int companyId) throws ApiException {
        LOGGER.debug("==================== enter CompanyResource getCompany ====================");
        LOGGER.debug("companyId: " + companyId);
        Company company = null;
		if (Account.Role.COMPANY == currentRole() && companyId == 0) {
			companyId = currentEntityId();
		}
        try {
			company = companyService.get(companyId);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
                throw new ApiException(404, devMsg, "该企业不存在！");
            }
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave CompanyResource getCompany ====================");
        return company;
    }
	
	@GET
    @Path("/{companyId}/positions")
    @Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Position> getCompanyPositions(@PathParam("companyId") int companyId,
			@QueryParam("pageIndex") Integer pageIndex,
            @QueryParam("pageSize") Integer pageSize) throws ApiException {
		LOGGER.debug("==================== enter CompanyResource getPositiones ====================");
		if (companyId == 0) {
			companyId = currentEntityId();
		}
        Position selector = new Position();
		selector.setCompanyId(companyId);
        Pagination pagination = new Pagination(pageSize, pageIndex);
        List<Position> positions = null;
		try {
			positions = positionService.getList(selector, pagination);
		} catch (ServiceException ex) {
			handleServiceException(ex);
		}
        LOGGER.debug("==================== leave CompanyResource getPositiones ====================");
        return new ListResponse(positions, pagination);
	}

}
