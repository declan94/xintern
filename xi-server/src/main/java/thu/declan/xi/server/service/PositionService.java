package thu.declan.xi.server.service;

import java.util.List;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Pagination;
import thu.declan.xi.server.model.Position;

/**
 *
 * @author declan
 */
public interface PositionService extends BaseTableService<Position> {
	
    public List<Position> getCollectedList(int stuId, Pagination pagination) throws ServiceException;
    
    public int getCollectedCount(int stuId);
    
}
