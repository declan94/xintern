package thu.declan.xi.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.service.SMSService;
import thu.declan.xi.server.util.HttpRequest;

/**
 *
 * @author declan
 */
@Service("smsService")
public class SMSServiceImpl implements SMSService{
    
    final String ENDPOINT = "http://106.ihuyi.com/webservice/sms.php";
    final String ACCOUNT = "cf_cfwxx";
    final String KEY = "1327a2e6f97bb8f3f3f61e4da5b5033c";

    @Override
    public boolean sendMsg(String phone, String content) {
        StringBuilder params = new StringBuilder("method=Submit");
        try {
            params.append("&account=").append(ACCOUNT)
                    .append("&password=").append(KEY)
                    .append("&mobile=").append(phone)
                    .append("&content=").append(URLEncoder.encode(content, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SMSServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpRequest.sendGet(ENDPOINT, params.toString());
        return true;        
    }

    @Override
    public boolean sendCode(String phone, String code) {
        return sendMsg(phone, "您的验证码是：" + code + "。请不要把验证码泄露给其他人。");
    }

	@Override
	@Async
	public void sendMsgInBackground(String phone, String content) {
		sendMsg(phone, content);
	}
    
}


