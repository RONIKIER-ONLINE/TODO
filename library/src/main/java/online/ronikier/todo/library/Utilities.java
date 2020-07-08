package online.ronikier.todo.library;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online.ronikier.todo.Messages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utilities {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static Date dateCurrent() {
        return Calendar.getInstance().getTime();
    }

    public static Date dateFuture(int days) {
        return dateFutureFrom(days,dateCurrent());
    }

    public static Date dateFutureFrom(int days, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
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
        if (date == null) return Messages.DATE_NOT_SET;
        return getDateFormat().format(date);
    }

    public static String wrapString(String string) {
        if (string == null) return null;
        return "'" + string + "'";
    }


    public static boolean notEmpty(String value) {
        if (value == null) return false;
        if (value.trim().isEmpty()) return false;
        return true;
    }

    public static AtomicInteger counter() {

        return new AtomicInteger(1);

    }
}
