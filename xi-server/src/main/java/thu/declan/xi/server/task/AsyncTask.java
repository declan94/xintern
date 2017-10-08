package thu.declan.xi.server.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import thu.declan.xi.server.mapper.CompanyMapper;
import thu.declan.xi.server.mapper.RateMapper;
import thu.declan.xi.server.model.AvgRate;
import thu.declan.xi.server.model.Company;
import thu.declan.xi.server.model.Rate;

/**
 *
 * @author declan
 */
@Component
public class AsyncTask {
	
	@Autowired
	private CompanyMapper compMapper;
	
	@Autowired
	private RateMapper rateMapper;
	
	@Async
	public void refreshCompanyRate(Integer id) {
		Rate sel = new Rate();
		sel.setCompanyId(id);
		sel.setDirection(Rate.Direction.STU_TO_COMP);
		AvgRate ar = rateMapper.selectAvgRate(sel);
		Company updater = new Company();
		updater.setId(id);
		updater.setAvgRate(ar);
		compMapper.update(updater);
	}
	
	@Async
	public void refreashStuRate(Integer id) {
		
	}
	
}
