package thu.declan.xi.server.resource;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	@RolesAllowed({Constant.ROLE_COMPANY})
	// 完善企业信息
	public Company createCompany(@Valid Company company) throws ApiException {
		LOGGER.debug("==================== enter CompanyResource createCompany ====================");
		Integer companyId = null;
		try {
			Company comp = companyService.getByAccountId(currentAccountId());
			companyId = comp.getId();
		} catch (ServiceException ex) {
			if (ex.getCode() != ServiceException.CODE_NO_SUCH_ELEMENT) {
				handleServiceException(ex);
			}
		}
		try {
			if (companyId == null) {
				company.setAccountId(currentAccountId());
				companyService.add(company);
			} else {
				company.setId(companyId);
				companyService.update(company);
			}
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave CompanyResource createCompany ====================");
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
				throw new ApiException(404, devMsg, "该管理员不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave CompanyResource getCompany ====================");
		return company;
	}

}

