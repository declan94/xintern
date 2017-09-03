package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.PositionMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.mapper.CompanyMapper;
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
	
	@Autowired
	CompanyMapper companyMapper;

	@Override
	protected BaseMapper<Position> getMapper() {
		return positionMapper;
	}
	
	@Override
	public void preAdd(Position position) throws ServiceException {
        if (companyMapper.selectOne(position.getCompanyId()) == null) {
			throw new ServiceException(ServiceException.CODE_FK_CONSTRAINT, "No such company");
		}
		position.setStuSalary((int)(position.getSalary() * (1 - Constant.SERVICE_FEE_RATE)));
	}
	
	@Override
	public void preUpdate(Position update) {
		if (update.getSalary() != null) {
			update.setStuSalary((int)(update.getSalary() * (1 - Constant.SERVICE_FEE_RATE)));
		}
	}

	
}
