package thu.declan.xi.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author declan
 */
public class Resume {
    
    public enum RState {
        NEW,
        WAIT_STU_CONFIRM,
        WAIT_COMP_CONFIRM,
        CONFIRMED,
        OFFERED,
        WORKING,
        ENDED,
        CANCELED;
        
        public static RState fromString(String str) {
            return Enum.valueOf(RState.class, str.toUpperCase());
        }
        
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer positionId;

    @NotNull
    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }	

	@JsonIgnore
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
	
    private RState state;

    public RState getState() {
        return state;
    }

    public void setState(RState state) {
        this.state = state;
    }
	
	@JsonIgnore
	private List<RState> queryStates;

	public List<RState> getQueryStates() {
		return queryStates;
	}

	public void setQueryStates(List<RState> queryStates) {
		this.queryStates = queryStates;
	}

    private String commentStu;

    public String getCommentStu() {
        return commentStu;
    }

    public void setCommentStu(String commentStu) {
        this.commentStu = commentStu;
    }

    private String commentComp;

    public String getCommentComp() {
        return commentComp;
    }

    public void setCommentComp(String commentComp) {
        this.commentComp = commentComp;
    }

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
}
