package simpletest;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kirahvi on 24.01.18.
 */

public class TimeTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void time_isCorrect() throws Exception {
        Date currentTime = Calendar.getInstance().getTime();
        System.out.println(currentTime);

        DateFormat df = new SimpleDateFormat("HH");
        int date = Integer.parseInt(df.format(Calendar.getInstance().getTime()));
        System.out.println(date);
        assertNotNull(date);
    }
}
