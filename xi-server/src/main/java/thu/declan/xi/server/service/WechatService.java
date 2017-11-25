package thu.declan.xi.server.service;

import java.util.Map;
import thu.declan.xi.server.exception.ServiceException;

/**
 *
 * @author declan
 */
public interface WechatService {
	
	public String getAccessToken() throws ServiceException;

	public Map<String, String> getJSSDKSign(String url) throws ServiceException;
	
}
