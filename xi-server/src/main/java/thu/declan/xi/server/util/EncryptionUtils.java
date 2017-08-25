package thu.declan.xi.server.util;


import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionUtils {

    public static String md5(String data) {
        return DigestUtils.md5Hex(data);
    }

    public static String sha1(String data) {
        return DigestUtils.sha1Hex(data);
    }

    public static String sha256(String data) {
        return DigestUtils.sha256Hex(data);
    }

    public static String randomPassword(int length) {
        String seed = String.format("%s%f", new Date(), Math.random());
        return EncryptionUtils.sha256(seed).substring(0, length);
    }
	
}