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
	
	public void sendTemplateMessage(String tplId, String openid, String url, Map<String, String> data) throws ServiceException;
			
}
