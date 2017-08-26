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
    
    //uploads
	
	public static final String UPLOAD_DIR = "/opt/web/upload";
	
	public static final String UPLOAD_CONTEXT_PATH = "/upload";
    
    // rate configurations
    
    public static final Double WITHDRAW_FEE_RATE = 0.05;
    
    public static final Double REWARD_SCORE_RATE = 0.9;
	
	public static final Double SHOP_BONUS_RATE = 0.05;
    
	public static final Integer MAX_ACCOUNT_CNT_PER_PERSON = 11;
    
    public static final Double SINGLE_RECHARGE_MAX = 50000.0;
}
