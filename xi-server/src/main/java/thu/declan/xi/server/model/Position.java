package thu.declan.xi.server.model;

import java.sql.Time;
import java.sql.Date;
import thu.declan.xi.server.model.Student.Education;
import thu.declan.xi.server.model.Student.Gender;
import thu.declan.xi.server.model.Student.LangLevel;

/**
 *
 * @author declan
 */
public class Position {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer companyId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String addr;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    private Date startDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    private Date endDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private Time startTime;

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    private Time endTime;

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    private Integer salary;

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    private Integer stuSalary;

    public Integer getStuSalary() {
        return stuSalary;
    }

    public void setStuSalary(Integer stuSalary) {
        this.stuSalary = stuSalary;
    }

    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private Integer minDays;

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    private Boolean continuous;

    public Boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(Boolean continuous) {
        this.continuous = continuous;
    }

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private Boolean retention;

    public Boolean isRetention() {
        return retention;
    }

    public void setRetention(Boolean retention) {
        this.retention = retention;
    }

    private String intro;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    private Gender reqGender;

    public Gender getReqGender() {
        return reqGender;
    }

    public void setReqGender(Gender reqGender) {
        this.reqGender = reqGender;
    }

    private Education reqEdu;

    public Education getReqEdu() {
        return reqEdu;
    }

    public void setReqEdu(Education reqEdu) {
        this.reqEdu = reqEdu;
    }

    private Integer reqGrade;

    public Integer getReqGrade() {
        return reqGrade;
    }

    public void setReqGrade(Integer reqGrade) {
        this.reqGrade = reqGrade;
    }

    private String reqMajor;

    public String getReqMajor() {
        return reqMajor;
    }

    public void setReqMajor(String reqMajor) {
        this.reqMajor = reqMajor;
    }

    private String reqLang;

    public String getReqLang() {
        return reqLang;
    }

    public void setReqLang(String reqLang) {
        this.reqLang = reqLang;
    }

    private LangLevel reqLangLevel;

    public LangLevel getReqLangLevel() {
        return reqLangLevel;
    }

    public void setReqLangLevel(LangLevel reqLangLevel) {
        this.reqLangLevel = reqLangLevel;
    }

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
