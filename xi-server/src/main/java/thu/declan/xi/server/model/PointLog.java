package thu.declan.xi.server.model;

import java.sql.Date;

/**
 *
 * @author declan
 */
public class PointLog {

    public enum PType {
        REGISTER, //注册
        PROFILE, //完善资料（简历）
        LOGIN, //登录
        POSITION, //企业添加职位
        RESUME, //企业浏览简历
        EMPLOY, //企业录用学生、学生被录用
        COMMENT, //评价
        STAR5, //学生收到企业五星评价
        RECOMMEND; //学生推荐朋友注册成功
        
        public static PType fromString(String str) {
            return Enum.valueOf(PType.class, str.toUpperCase());
        }
    }
    
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    private PType type;

    public PType getType() {
        return type;
    }

    public void setType(PType type) {
        this.type = type;
    }

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
