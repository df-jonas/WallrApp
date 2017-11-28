package be.defrere.wallr.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    private static Date d;

    public static String getTimestamp(){
        d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        return sdf.format(d);
    }
}
