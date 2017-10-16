package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.AccountMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.service.AccountService;
import thu.declan.xi.server.util.EncryptionUtils;

/**
 *
 * @author declan
 */
@Service("accountService")
public class AccountServiceImpl extends BaseTableServiceImpl<Account> implements AccountService {

	@Autowired
	AccountMapper accountMapper;
	
	@Override
	protected BaseMapper<Account> getMapper() {
		return accountMapper;
	}
	
	@Override
	public void preAdd(Account account) throws ServiceException {
        if (accountMapper.selectCount(account) > 0) {
            throw new ServiceException(ServiceException.CODE_DUPLICATE_ELEMENT, "Account already exists.");
        }
		account.setPassword(EncryptionUtils.genProtectedPassword(account.getPassword()));
	}
	
	@Override
	public void preUpdate(Account update) throws ServiceException {
        if (update.getPhone() != null) {
            if (accountMapper.selectCount(update) > 0) {
                throw new ServiceException(ServiceException.CODE_DUPLICATE_ELEMENT, "Account already exists.");
            }
        } 
		if (update.getPassword() != null) {
			update.setPassword(EncryptionUtils.genProtectedPassword(update.getPassword()));
		}
	}

    @Override
    public void delete(int id) {
        accountMapper.delete(id);
    }
	
}
