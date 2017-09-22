package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.AccountMapper;
import thu.declan.xi.server.mapper.PointLogMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.model.PointLog;
import thu.declan.xi.server.service.PointLogService;
import thu.declan.xi.server.util.EncryptionUtils;

/**
 *
 * @author declan
 */
@Service("plogService")
public class PointLogServiceImpl extends BaseTableServiceImpl<PointLog> implements PointLogService {

	@Autowired
	PointLogMapper pointLogMapper;
    
    @Autowired
    AccountMapper accountMapper;

	@Override
	protected BaseMapper<PointLog> getMapper() {
		return pointLogMapper;
	}
    
    @Override
	public void preAdd(PointLog pointLog) throws ServiceException {
        PointLog sel = new PointLog(pointLog.getAccountId(), pointLog.getType(), pointLog.getRefId());
        if (pointLogMapper.selectCount(sel) > 0) {
            throw new ServiceException(ServiceException.CODE_DUPLICATE_ELEMENT, "Already added");
        }
        accountMapper.addPoint(pointLog.getAccountId(), pointLog.getValue());
	}
	
}
