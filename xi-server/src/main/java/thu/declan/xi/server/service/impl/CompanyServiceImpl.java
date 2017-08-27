package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.CompanyMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Company;
import thu.declan.xi.server.service.CompanyService;

/**
 *
 * @author declan
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseTableServiceImpl<Company> implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

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
	
}
