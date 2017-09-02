package thu.declan.xi.server.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.model.Account.Role;
import thu.declan.xi.server.service.AuthService;

/**
 *
 * @author declan
 */
public class BaseResource {
	
	@Autowired
	protected AuthService authService;
    
    @Autowired 
    protected AutowireCapableBeanFactory beanFactory;

	protected Account currentAccount() {
		return authService.getAccount();
	}
	
	protected Role currentRole() {
		Account acc = authService.getAccount();
		return acc == null ? null : acc.getRole();
	}
	
	protected Integer currentAccountId() {
		Account acc = authService.getAccount();
		return acc == null ? null : acc.getId();
	}
	
	protected Account loginAccount(Account acc) throws ApiException {
		try {
			return authService.login(acc.getPhone(), acc.getPassword(), acc.getRole());
		} catch (ServiceException ex) {
			handleServiceException(ex);
			return null;
		}
	}

	protected void handleServiceException(ServiceException ex) throws ApiException {
		String devMsg = "Service Exception [" + ex.getCode() + "] " + ex.getReason();
		switch (ex.getCode()) {
			case ServiceException.CODE_DATABASE_ERR:
				throw new ApiException(500, devMsg, "未知错误.");
			case ServiceException.CODE_NO_SUCH_ELEMENT:
				throw new ApiException(404, devMsg, "账号不存在.");
			case ServiceException.CODE_WRONG_PASSWORD:
				throw new ApiException(403, devMsg, "密码错误.");
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
