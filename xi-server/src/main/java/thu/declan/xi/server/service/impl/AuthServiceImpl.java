package thu.declan.xi.server.service.impl;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.AccountMapper;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.service.AuthService;
import thu.declan.xi.server.util.EncryptionUtils;


/**
 *
 * @author declan
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	AccountMapper accountMapper;
    
    private HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true); 
        return session;
    }

	@Override
	public Account getAccount() {
		return (Account) session().getAttribute(Constant.SESSION_ACCOUNT);
	}

	@Override
	public Account login(String phone, String password, Account.Role authType) throws ServiceException {
		Account matcher = new Account();
		matcher.setPhone(phone);
		matcher.setRole(authType);
		Account account = accountMapper.selectByIdentity(matcher);
		if (account == null) {
			throw new ServiceException(ServiceException.CODE_NO_SUCH_ELEMENT, "No such account");
		}
		if (!EncryptionUtils.checkPassword(password, account.getPassword())) {
			throw new ServiceException(ServiceException.CODE_WRONG_PASSWORD, "Wrong password");
		}
		session().setAttribute(Constant.SESSION_ACCOUNT, account);
		return account;
	}

    
}
