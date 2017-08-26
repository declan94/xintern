package thu.declan.xi.server.mapper;

import thu.declan.xi.server.model.Account;

/**
 *
 * @author declan
 */
public interface AccountMapper extends BaseMapper<Account> {
	
	public Account selectByIdentity(Account matcher);
	
}
