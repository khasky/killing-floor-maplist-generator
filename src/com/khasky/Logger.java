package com.khasky;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Khasky
 * @see www.khasky.com
 */
public class Logger
{
    private static final DateFormat _dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void print(String message)
    {
        System.out.println(_dateFormat.format(Calendar.getInstance().getTime()) + " " + message);
    }
}
