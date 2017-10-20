package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.CompanyMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.mapper.PositionMapper;
import thu.declan.xi.server.mapper.RateMapper;
import thu.declan.xi.server.model.Company;
import thu.declan.xi.server.model.Position;
import thu.declan.xi.server.model.Rate;
import thu.declan.xi.server.service.CompanyService;

/**
 *
 * @author declan
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseTableServiceImpl<Company> implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	PositionMapper positionMapper;
	
	@Autowired
	RateMapper rateMapper;

	@Override
	protected BaseMapper getMapper() {
		return companyMapper;
	}

	@Override
	public Company getByAccountId(int accountId) throws ServiceException {
		Company comp = companyMapper.selectByAccountId(accountId);
		if (comp == null) {
			throw new ServiceException(ServiceException.CODE_NO_SUCH_ELEMENT, "No such company");
		}			
		return comp;
	}
	
	@Override
	protected void postGet(Company comp) {
		Position psel = new Position();
		psel.setCompanyId(comp.getId());
		psel.setActive(Boolean.TRUE);
		comp.setActivePosCnt(positionMapper.selectCount(psel));
		Rate rsel = new Rate();
		rsel.setCompanyId(comp.getId());
		rsel.setDirection(Rate.Direction.STU_TO_COMP);
		comp.setRateCnt(rateMapper.selectCount(rsel));
	}
	
}
