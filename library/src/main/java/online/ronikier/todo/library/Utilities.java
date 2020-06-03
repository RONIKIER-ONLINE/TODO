package online.ronikier.todo.library;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utilities {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

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
        if (dateString == null) return null;
        return getDateFormat().parse(dateString);
    }

    private static DateFormat getDateFormat() {
        return new SimpleDateFormat(DATE_PATTERN);
    }

    public static String stringFromDate(Date date) {
        if (date == null) return null;
        return getDateFormat().format(date);
    }
}
