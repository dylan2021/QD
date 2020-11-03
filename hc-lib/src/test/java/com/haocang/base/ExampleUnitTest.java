package com.haocang.base;

import com.haocang.base.utils.StringUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testFormatNumTail() throws Exception {
        assertEquals("7,758.72", StringUtils.format2Decimal("7758.7150","2"));
    }

    @Test
    public void testFormatOverMillion() throws Exception {
        assertEquals("2,134.78万", StringUtils.format2Decimal("21347758.7150","2"));
    }
}