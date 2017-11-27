package thu.declan.xi.server.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import thu.declan.xi.server.mapper.ResumeMapper;
import thu.declan.xi.server.mapper.SalaryMapper;
import thu.declan.xi.server.model.Resume;
import thu.declan.xi.server.model.Salary;

/**
 * backup databases
 *
 * @author declan
 */
@Component
public class SalaryTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalaryTask.class);
	
	private static final double SALARY_COMMON_DAYS = 24;

	@Autowired
    private SalaryMapper salaryMapper;
    
    @Autowired
    private ResumeMapper resumeMapper;
	
    // 每月1日1点
	@Scheduled(cron = "0 0 1 1 * ? ")
	public void generateSalaries() {
        LOGGER.info("******************************** Start Generate Salaries ********************************");
        Resume sel = new Resume();
        sel.setState(Resume.RState.WORKING);
        List<Resume> resumes = resumeMapper.selectList(sel);
        for (Resume r : resumes) {
            LOGGER.info("Generate for resume %d", r.getId());
            String unit = r.getUnit();
            Salary s = new Salary();
            s.setCompanyId(r.getCompanyId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            s.setMonth(format.format(new Date((new Date()).getTime() - 24 * 60 * 60 * 1000)));
            s.setResumeId(r.getId());
            s.setState(Salary.SState.NEW_GENERATED);
            s.setWorkDays(SALARY_COMMON_DAYS);
//            if (unit.contains("周")) {
//                s.setWorkDays(SALARY_COMMON_DAYS / 7 + 1);
//            } else if (unit.contains("时")) {
//                s.setWorkDays(SALARY_COMMON_DAYS*8);
//            }
            salaryMapper.insert(s);
        }
        LOGGER.info("******************************** Finish Generate Salaries ********************************");
	}

}
