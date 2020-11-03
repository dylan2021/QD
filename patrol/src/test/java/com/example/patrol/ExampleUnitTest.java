package com.example.patrol;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTwoHourInteval() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, 2);
        Date start = c.getTime();
        c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, 2);
        Date twoHourLater = c.getTime();
        boolean result = twoHourLater.after(start);
        assertTrue(result);
    }
}