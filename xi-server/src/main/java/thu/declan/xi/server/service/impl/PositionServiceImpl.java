package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.PositionMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Position;
import thu.declan.xi.server.service.PositionService;

/**
 *
 * @author declan
 */
@Service("positionService")
public class PositionServiceImpl extends BaseTableServiceImpl<Position> implements PositionService {

	@Autowired
	PositionMapper positionMapper;
	
	@Override
	protected BaseMapper<Position> getMapper() {
		return positionMapper;
	}
	
	@Override
	public void preAdd(Position position) throws ServiceException {
        
	}
	
	@Override
	public void preUpdate(Position update) {
		
	}

	
}
