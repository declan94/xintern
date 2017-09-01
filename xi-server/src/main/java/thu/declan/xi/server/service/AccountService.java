package thu.declan.xi.server.service;

import thu.declan.xi.server.model.Account;;

/**
 *
 * @author declan
 */
public interface AccountService extends BaseTableService<Account> {
	
    public void delete(int id);
    
}
