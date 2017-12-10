package be.defrere.wallr.util.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    private static Date d;

    public static String getTimestamp(){
        d = new Date();
        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        return df.format(d);
    }
}
