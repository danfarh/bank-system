package Manager;

import java.util.Date;

public class Time {
    private static Date date = new Date(System.currentTimeMillis());

    public static Date getDate() {
        return date;
    }

    public static void setDate(Date date) {
        Time.date = date;
    }
}
