package thu.declan.xi.server.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author declan
 */
public class SqlTimeAdapter extends XmlAdapter<java.util.Date, java.sql.Time> {

    @Override
    public java.util.Date marshal(java.sql.Time sqlTime) throws Exception {
        if(null == sqlTime) {
            return null;
        }
        return new java.util.Date(sqlTime.getTime());
    }

    @Override
    public java.sql.Time unmarshal(java.util.Date utilDate) throws Exception {
        if(null == utilDate) {
            return null;
        }
        return new java.sql.Time(utilDate.getTime());
    }

}
