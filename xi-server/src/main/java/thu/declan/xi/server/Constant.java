package thu.declan.xi.server;

/**
 *
 * @author declan
 */
public class Constant {
	
    //Auth roles
    
    public static final String ROLE_USER = "USER";
    
	public static final String ADMIN_ROLE_SUPER = "SUPER";
    
    public static final String ADMIN_ROLE_FINANCE = "FINANCE";
    
    public static final String ADMIN_ROLE_CUSTOMER = "CUSTOMER";
    
    public static final String ADMIN_ROLE_DELIVERY = "DELIVERY";
    
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
