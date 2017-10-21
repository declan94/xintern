package thu.declan.xi.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

/**
 *
 * @author declan
 */
public class Salary {
	
	public enum SState {
		NEW_GENERATED,
		WAIT_STU_CONFIRM,
		WAIT_COMP_CONFIRM,
		CONFIRMED;
		
		public static SState fromString(String str) {
            return Enum.valueOf(SState.class, str.toUpperCase());
        }   
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer resumeId;

	public Integer getResumeId() {
		return resumeId;
	}

	public void setResumeId(Integer resumeId) {
		this.resumeId = resumeId;
	}

	private Resume resume;

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	private Integer companyId;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	private Integer stuId;

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	private String month;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	private Integer workDays;

	public Integer getWorkDays() {
		return workDays;
	}

	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}

	private SState state;

	public SState getState() {
		return state;
	}

	public void setState(SState state) {
		this.state = state;
	}

	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonIgnore
	private List<SState> queryStates;

	public List<SState> getQueryStates() {
		return queryStates;
	}

	public void setQueryStates(List<SState> queryStates) {
		this.queryStates = queryStates;
	}
	
}
