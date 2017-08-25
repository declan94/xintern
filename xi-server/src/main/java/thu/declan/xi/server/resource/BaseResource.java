package thu.declan.xi.server.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Admin;

/**
 *
 * @author declan
 */
public class BaseResource {

	@Context
	protected ContainerRequestContext requestContext;

	protected boolean isAdmin() {
		return requestContext.getProperty("admin") != null;
	}

	protected Admin currentAdmin() {
		return (Admin) requestContext.getProperty("admin");
	}

	protected void handleServiceException(ServiceException ex) throws ApiException {
		String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
		switch (ex.getCode()) {
			case ServiceException.CODE_DATABASE_ERR:
				throw new ApiException(500, devMsg, "未知错误.");
			default:
				throw new ApiException(500, devMsg, "未知错误.");
		}
	}

	protected Date startDateFromString(String startDate) {
		if (startDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		try {

			Date start = (new SimpleDateFormat("yyyy-MM-dd")).parse(startDate);
			cal.setTime(start);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
		} catch (ParseException ex) {
			return null;
		}
	}
	
	protected Date endDateFromString(String endDate) {
		if (endDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		try {

			Date start = (new SimpleDateFormat("yyyy-MM-dd")).parse(endDate);
			cal.setTime(start);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			return cal.getTime();
		} catch (ParseException ex) {
			return null;
		}
	}

}
