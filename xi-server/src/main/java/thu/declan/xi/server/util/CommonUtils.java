package thu.declan.xi.server.util;

/**
 *
 * @author declan
 */
public class CommonUtils {
    
    public static double decimalDouble(Double v) {
		int v2 = (int) (v * 100 + 0.00001);
		return (double) v2 / 100;
	}
    
}
