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
import thu.declan.xi.server.model.Company;
import thu.declan.xi.server.model.Position;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.service.CompanyService;
import thu.declan.xi.server.service.PositionService;

/**
 *
 * @author declan
 */
@Path("positions")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_COMPANY})
public class PositionResource extends BaseResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionResource.class);

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private CompanyService companyService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 职位账号注册
	public Position createPosition(@Valid Position position) throws ApiException {
		LOGGER.debug("==================== enter PositionResource createPosition ====================");
		if (Account.Role.COMPANY.equals(currentRole())) {
			try {
				Company comp = companyService.getByAccountId(currentAccountId());
				position.setCompanyId(comp.getId());
			} catch (ServiceException ex) {
				handleServiceException(ex);
			}
		}
		try {
			positionService.add(position);
		} catch (ServiceException ex) {
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave PositionResource createPosition ====================");
		return position;
	}

	@PUT
	@Path("/{positionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 修改职位信息
	public Position editPosition(@PathParam("positionId") int positionId, Position position) throws ApiException {
		LOGGER.debug("==================== enter PositionResource editPosition ====================");
		LOGGER.debug("positionId: " + positionId);
		if (currentRole() == Account.Role.COMPANY) {
			try {
				Position oldPos = positionService.get(positionId);
				if (!Objects.equals(oldPos.getCompanyId(), currentEntityId())) {
					throw new ApiException(403, "Company Id not equal to authorized one", "权限不足");
				}
			} catch (ServiceException ex) {
				throw new ApiException(404, "Position not found", "职位id错误");
			}
		}
		try {
			position.setId(positionId);
			positionService.update(position);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "该职位不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave PositionResource editPosition ====================");
		return position;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Position> getPositions() throws ApiException {
		LOGGER.debug("==================== enter PositionResource getPositions ====================");
		Position selector = new Position();
		List<Position> positions = null;
		try {
			positions = positionService.getList(selector);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave PositionResource getPositions ====================");
		return new ListResponse(positions);
	}

	@GET
	@Path("/{positionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Position getPosition(@PathParam("positionId") int positionId) throws ApiException {
		LOGGER.debug("==================== enter PositionResource getPosition ====================");
		LOGGER.debug("positionId: " + positionId);
		Position position = null;
		try {
			position = positionService.get(positionId);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
				throw new ApiException(404, devMsg, "该职位不存在！");
			}
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave PositionResource getPosition ====================");
		return position;
	}

}
