package thu.declan.xi.server.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.NotificationMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Notification;
import thu.declan.xi.server.service.NotificationService;

/**
 *
 * @author declan
 */
@Service("notiService")
public class NotificationServiceImpl extends BaseTableServiceImpl<Notification> implements NotificationService {

	@Autowired
	private NotificationMapper notiMapper;

	@Override
	protected BaseMapper getMapper() {
		return notiMapper;
	}

	@Override
	public void addNoti(int accountId, Notification.NType type, int relativeId, String msg) {
		Notification noti = new Notification();
		noti.setAccountId(accountId);
		noti.setType(type);
		noti.setMsg(msg);
		try {
			add(noti);
		} catch (ServiceException ex) {
			Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
