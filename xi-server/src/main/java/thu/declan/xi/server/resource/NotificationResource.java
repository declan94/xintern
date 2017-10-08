package thu.declan.xi.server.resource;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.ListResponse;
import thu.declan.xi.server.model.Notification;
import thu.declan.xi.server.model.Pagination;

/**
 *
 * @author declan
 */
@Path("notifications")
@RolesAllowed({Constant.ROLE_STUDENT, Constant.ROLE_COMPANY})
public class NotificationResource extends BaseResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationResource.class);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ListResponse<Notification> getNotificationList(
			@QueryParam("pageIndex") Integer pageIndex,
			@QueryParam("pageSize") Integer pageSize) throws ApiException {
        LOGGER.debug("==================== enter NotificationResource getNotificationes ====================");
        Notification selector = new Notification();
        Pagination pagination = new Pagination(pageSize, pageIndex);
        List<Notification> noties = null;
        try {
             noties = notiService.getList(selector, pagination);
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
			LOGGER.debug(devMsg);
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave NotificationResource getNotificationes ====================");
        return new ListResponse(noties, pagination);
    }
    
    @GET
    @Path("/{notiId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Notification getNotification(@PathParam("notiId") int notiId) throws ApiException {
        LOGGER.debug("==================== enter NotificationResource getNotification ====================");
        LOGGER.debug("notiId: " + notiId);
        Notification noti = null;
        try {
			noti = notiService.get(notiId);
			if (!noti.isRead()) {
				noti.setRead(true);
				notiService.update(noti);
			}
		} catch (ServiceException ex) {
			String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
            LOGGER.debug(devMsg);
            if (ex.getCode() == ServiceException.CODE_NO_SUCH_ELEMENT) {
                throw new ApiException(404, devMsg, "该通知不存在！");
            }
			handleServiceException(ex);
		}
		LOGGER.debug("==================== leave NotificationResource getNotification ====================");
        return noti;
    }
    
}
