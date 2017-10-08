package thu.declan.xi.server.model;

import java.sql.Date;

/**
 *
 * @author declan
 */
public class Notification {
	
	public final static String TPL_POINT = "获得积分";
	public final static String TPL_RESUME_ADD = "简历投递成功";
	public final static String TPL_RESUME_CANCEL = "简历淘汰";
	public final static String TPL_RESUME_INTERVIEW = "面试邀请";
	public final static String TPL_RESUME_TIME = "面试时间修改";
	public final static String TPL_RESUME_JOIN = "入职通知";
	public final static String TPL_SALARY_GET = "工资到账";
	public final static String TPL_WITHDRAW = "提现成功通知";
	public final static String TPL_WITHDRAW_FAIL = "提现失败通知";
	public final static String TPL_COMMENT = "实习结束评价通知";
	
    
    public enum NType {
        POINT, RESUME, SALARY, WITHDRAW, COMMENT, BACKEND;
    }
    
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    private NType type;

    public NType getType() {
        return type;
    }

    public void setType(NType type) {
        this.type = type;
    }

	private Integer refId;

	public Integer getRefId() {
		return refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Boolean read;

    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
