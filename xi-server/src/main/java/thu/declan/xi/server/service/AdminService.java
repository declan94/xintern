package thu.declan.xi.server.service;

import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Admin;

/**
 *
 * @author declan
 */
public interface AdminService extends BaseTableService<Admin>, LoginService<Admin> {
	
    Admin delete(int id) throws ServiceException;
    
}
