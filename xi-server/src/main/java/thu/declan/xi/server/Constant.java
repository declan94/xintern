package thu.declan.xi.server;

/**
 *
 * @author declan
 */
public class Constant {
	
    //Auth roles

    public static final String ROLE_ADMIN = "ADMIN";
    
    public static final String ROLE_STUDENT = "STUDENT";

    public static final String ROLE_COMPANY = "COMPANY";
	
	//Session
	
	public static final String SESSION_ACCOUNT = "ACCOUNT";
	
	public static final String SESSION_ENTITY_ID = "ENTITY_ID";
    
    //uploads
	
	public static final String UPLOAD_DIR = "/opt/web/upload";
	
	public static final String UPLOAD_CONTEXT_PATH = "/upload";
    
    // rate configurations
    
    public static final Double SERVICE_FEE_RATE = 0.1;
	
	/*------------- email info -------------*/
	public static final String EMAIL_SMTPHOST = "smtp.ym.163.com";
	public static final String EMAIL_SMTPPORT = "25";
	public static final String EMAIL_USERNAME = "wangzc@xiangshixi.cc";
	public static final String EMAIL_PASSWORD = "1qaz2wsX";
    
    /*------------- wechat info -------------*/
    public static final String WECHAT_APPID = "wx44895bec275b61e6";
    public static final String WECHAT_SECRET = "15d2424d3ec81b316972fc72b6ba6cec";
}
