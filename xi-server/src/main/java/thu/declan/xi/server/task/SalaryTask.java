package thu.declan.xi.server.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import thu.declan.xi.server.mapper.AccountMapper;
import thu.declan.xi.server.mapper.ResumeMapper;
import thu.declan.xi.server.mapper.SalaryMapper;
import thu.declan.xi.server.mapper.StudentMapper;
import thu.declan.xi.server.model.Resume;
import thu.declan.xi.server.model.Salary;
import thu.declan.xi.server.model.Student;

/**
 * backup databases
 *
 * @author declan
 */
@Component
public class SalaryTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalaryTask.class);
	
	private static final double SALARY_COMMON_DAYS = 22;

	@Autowired
    private SalaryMapper salaryMapper;
    
    @Autowired
    private ResumeMapper resumeMapper;
    
    @Autowired
    private StudentMapper studentMapper;
    
    @Autowired
    private AccountMapper accountMapper;
	
    // 每月1日1点
	@Scheduled(cron = "0 0 1 1 * ? ")
	public void generateSalaries() {
        LOGGER.info("******************************** Start Generate Salaries ********************************");
        Resume sel = new Resume();
        sel.setState(Resume.RState.WORKING);
        List<Resume> resumes = resumeMapper.selectList(sel);
        for (Resume r : resumes) {
            LOGGER.info("Generate for resume %d", r.getId());
            Salary s = new Salary();
            s.setCompanyId(r.getCompanyId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            s.setMonth(format.format(new Date((new Date()).getTime() - 24 * 60 * 60 * 1000)));
            s.setResumeId(r.getId());
			s.setStuId(r.getStuId());
            s.setState(Salary.SState.NEW_GENERATED);
            s.setWorkDays(SALARY_COMMON_DAYS);
            s.updateValue(r);
            salaryMapper.insert(s);
        }
        LOGGER.info("******************************** Finish Generate Salaries ********************************");
	}
    
    // 每天2点
    @Scheduled(cron = "0 0 2 * * ? ")
    public void paySalaries() {
        LOGGER.info("******************************** Start Pay Salaries ********************************");
        Salary sel = new Salary();
        sel.setState(Salary.SState.CONFIRMED);
        List<Salary> salaries = salaryMapper.selectList(sel);
        for (Salary s : salaries) {
            LOGGER.info("pay for salary %d", s.getId());
            Student stu = studentMapper.selectOne(s.getStuId());
            if (stu == null) {
                LOGGER.info("Student not found for stuid: %d", s.getStuId());
                continue;
            }
            accountMapper.addBalance(stu.getAccountId(), s.getStuValue());
            s.setPayTime(new Date());
            s.setState(Salary.SState.PAID);
            salaryMapper.update(s);
        }
        LOGGER.info("******************************** Finish Pay Salaries ********************************");
    }

}
