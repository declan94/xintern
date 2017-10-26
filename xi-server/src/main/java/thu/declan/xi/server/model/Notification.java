package thu.declan.xi.server.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import thu.declan.xi.server.util.CustomJsonDateSerializer;

/**
 *
 * @author declan
 */
public class Notification {
	
	public final static String TPL_POINT = "恭喜你获得%d个积分";
	public final static String TPL_RESUME_ADD = "简历投递成功，请等待面试通知。";
	public final static String TPL_RESUME_CANCEL = "您的简历与企业相关要求不符，让我们再接再厉吧。";
	public final static String TPL_RESUME_CANCEL2 = "很抱歉，您参加的%s公司面试未通过，让我们继续在享实习寻找机会吧！";
	public final static String TPL_RESUME_INTERVIEW = "你收到来自%s公司的面试邀请，请尽快确认面试时间。";
	public final static String TPL_RESUME_TIME = "您的%s公司面试时间已修改为%s，请准时参加。";
	public final static String TPL_RESUME_JOIN = "恭喜你已通过%s公司面试，请尽快确认入职";
	public final static String TPL_SALARY_GET = "工资到账";
	public final static String TPL_WITHDRAW = "提现成功通知";
	public final static String TPL_WITHDRAW_FAIL = "提现失败通知";
	public final static String TPL_COMMENT = "实习结束啦，请及时对该公司进行评价。";
	public final static String TPL_RESUME_NEW = "你收到来自%s的简历，请尽快查看。";
	
    
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

	@JsonSerialize(using = CustomJsonDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
