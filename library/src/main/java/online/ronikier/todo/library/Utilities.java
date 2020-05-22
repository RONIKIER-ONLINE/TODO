package online.ronikier.todo.library;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class Utilities {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

    public static Date dateCurrent() {
        return Calendar.getInstance().getTime();
    }

    public static Date dateFuture(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCurrent());
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date dateFromString(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

}
