package thu.declan.xi.server.resource;

import java.util.List;
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
import thu.declan.xi.server.model.Company;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.service.CompanyService;

/**
 *
 * @author declan
 */
@Path("companys")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_COMPANY})
public class CompanyResource extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyResource.class);

    @Autowired
    private CompanyService companyService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    // 企业账号注册
    public Company createCompany(@Valid Company company) throws ApiException {
        LOGGER.debug("==================== enter CompanyResource createCompany ====================");
        Account acc = company.getAccount();
        AccountResource accRes = new AccountResource();
        acc = accRes.createAccount(acc);
        company.setAccount(acc);
        try {
            company.setAccountId(acc.getId());
            companyService.add(company);
        } catch (ServiceException ex) {
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
        if (currentRole() != Account.Role.ADMIN) {
            try {
                Company oldComp = companyService.get(companyId);
                if (companyId == 0) {
                    companyId = oldComp.getId();
                }
                if (oldComp.getId() != companyId) {
                    throw new ApiException(401, "Company Id not equal to authorized one", "权限不足");
                }
            } catch (ServiceException ex) {
                throw new ApiException(404, "Company not found", "公司id错误");
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
    public ListResponse<Company> getCompanys() throws ApiException {
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

    @GET
    @Path("/{companyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Company getCompany(@PathParam("companyId") int companyId) throws ApiException {
        LOGGER.debug("==================== enter CompanyResource getCompany ====================");
        LOGGER.debug("companyId: " + companyId);
        Company company = null;
        try {
            company = companyService.get(companyId);
        } catch (ServiceException ex) {
            String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
                throw new ApiException(404, devMsg, "该公司不存在！");
            }
            handleServiceException(ex);
        }
        LOGGER.debug("==================== leave CompanyResource getCompany ====================");
        return company;
    }

}
