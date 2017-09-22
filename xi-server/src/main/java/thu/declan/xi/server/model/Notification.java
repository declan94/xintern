package thu.declan.xi.server.model;

import java.sql.Date;

/**
 *
 * @author declan
 */
public class Notification {
    
    public enum NType {
        POINT, RESUME, SALARY, COMMENT, BACKEND;
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
