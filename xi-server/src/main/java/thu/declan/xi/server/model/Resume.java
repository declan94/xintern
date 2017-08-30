package thu.declan.xi.server.model;

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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
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
    
}
