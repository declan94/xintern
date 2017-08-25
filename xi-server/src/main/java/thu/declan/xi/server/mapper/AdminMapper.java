package thu.declan.xi.server.mapper;

import thu.declan.xi.server.model.Admin;

/**
 *
 * @author declan
 */
public interface AdminMapper extends BaseMapper<Admin> {
    
	public Admin selectByUsername(String username);
	
}
