package thu.declan.xi.server.resource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.util.HttpRequest;

/**
 *
 * @author declan
 */
@Path("wechat")
@PermitAll
public class WechatResource extends BaseResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatResource.class);
    
    @GET
    @Path("/accessToken")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccessToken(@QueryParam("code") String code, @QueryParam("mobile") Boolean mobile) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String appid, appsecret;
        if (mobile) {
            appid = Constant.WECHAT_APPID;
            appsecret = Constant.WECHAT_SECRET;
        } else {
            appid = Constant.WECHAT_OPEN_APPID;
            appsecret = Constant.WECHAT_OPEN_SECRET;
        }
        String params = String.format("appid=%s&secret=%s&code=%s&grant_type=authorization_code", 
                appid, appsecret, code);
        String ret = HttpRequest.sendGet(url, params);
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode obj = null;
//        try {
//            obj = mapper.readTree(ret);
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(WechatResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return ret;
    }
	    
}
