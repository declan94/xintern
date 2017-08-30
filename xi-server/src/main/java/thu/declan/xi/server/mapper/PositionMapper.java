package thu.declan.xi.server.mapper;

import thu.declan.xi.server.model.Position;

/**
 *
 * @author declan
 */
public interface PositionMapper extends BaseMapper<Position> {
    
	Position selectByCompanyId(int companyId);
	
}
