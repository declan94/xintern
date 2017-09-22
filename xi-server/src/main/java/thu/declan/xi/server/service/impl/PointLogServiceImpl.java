package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.mapper.PointLogMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.PointLog;
import thu.declan.xi.server.service.PointLogService;

/**
 *
 * @author declan
 */
@Service("plogService")
public class PointLogServiceImpl extends BaseTableServiceImpl<PointLog> implements PointLogService {

	@Autowired
	PointLogMapper pointLogMapper;

	@Override
	protected BaseMapper<PointLog> getMapper() {
		return pointLogMapper;
	}
	
}
