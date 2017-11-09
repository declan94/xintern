package thu.declan.xi.server.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.NotificationMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.model.Company;
import thu.declan.xi.server.model.Notification;
import thu.declan.xi.server.model.Student;
import thu.declan.xi.server.service.AccountService;
import thu.declan.xi.server.service.CompanyService;
import thu.declan.xi.server.service.EmailService;
import thu.declan.xi.server.service.NotificationService;
import thu.declan.xi.server.service.SMSService;
import thu.declan.xi.server.service.StudentService;

/**
 *
 * @author declan
 */
@Service("notiService")
public class NotificationServiceImpl extends BaseTableServiceImpl<Notification> implements NotificationService {

	@Autowired
	private NotificationMapper notiMapper;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private EmailService emailService;
    
    @Autowired
    private SMSService smsService;
	
	@Override
	protected BaseMapper getMapper() {
		return notiMapper;
	}
	
	@Override
	protected void postGetList(List<Notification> notis) {
		notiMapper.setRead(notis);
	}

	@Override
	public void addNoti(int accountId, Notification.NType type, int refId, String msgTpl, Object... args) {
		Notification noti = new Notification();
		noti.setAccountId(accountId);
		noti.setType(type);
		noti.setMsg(String.format(msgTpl, args));
		noti.setRefId(refId);
		try {
			add(noti);
		} catch (ServiceException ex) {
			Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			Account acc = accountService.get(accountId);
			String emailAddr;
            String phone;
			if (acc.getRole() == Account.Role.STUDENT) {
                phone = acc.getPhone();
				Student stu = studentService.getByAccountId(accountId);
				emailAddr = stu.getEmail();
			} else {
				Company comp = companyService.getByAccountId(accountId);
				emailAddr = comp.getEmail();
                phone = comp.getPhone();
			}
			if (acc.getRole() != Account.Role.ADMIN) {
				emailService.sendEmailInBackground("消息通知【享实习】", noti.getMsg(), emailAddr);
				smsService.sendMsg(phone, noti.getMsg());
			}
		} catch (ServiceException ex) {
			Logger.getLogger(NotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
    @Override
    public Integer unreadCnt(int accountId) {
        return notiMapper.unreadCnt(accountId);
    }

}
