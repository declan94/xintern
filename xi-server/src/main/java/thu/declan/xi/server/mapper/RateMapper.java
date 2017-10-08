package thu.declan.xi.server.mapper;

import thu.declan.xi.server.model.AvgRate;
import thu.declan.xi.server.model.Rate;

/**
 *
 * @author declan
 */
public interface RateMapper extends BaseMapper<Rate> {
    
	public AvgRate selectAvgRate(Rate sel);
	
}
