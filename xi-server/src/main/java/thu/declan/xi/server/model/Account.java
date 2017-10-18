package thu.declan.xi.server.model;

/**
 *
 * @author declan
 */
public class Account {
    
    public enum Role {
        ADMIN,      //管理员
        COMPANY,    //企业
        STUDENT;    //学生//学生
        
        public static Role fromString(String str) {
            return Enum.valueOf(Role.class, str.toUpperCase());
        }
    }
    
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role type) {
        this.role = type;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String wechat;

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Integer point;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    private Integer unreadNotis;

    public Integer getUnreadNotis() {
        return unreadNotis;
    }

    public void setUnreadNotis(Integer unreadNotis) {
        this.unreadNotis = unreadNotis;
    }
    
}
