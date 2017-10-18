package thu.declan.xi.server.service;

import thu.declan.xi.server.model.Notification;

/**
 *
 * @author declan
 */
public interface NotificationService extends BaseTableService<Notification> {

	public void addNoti(int accountId, Notification.NType type, int relativeId, String msgTpl, Object... args);
	
    public Integer unreadCnt(int accountId);
    
}
