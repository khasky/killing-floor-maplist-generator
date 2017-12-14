package com.khasky;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Khasky
 */
public class Logger
{
    private static final DateFormat _dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void printLine(String message)
    {
        System.out.println(_dateFormat.format(Calendar.getInstance().getTime()) + " " + message);
    }

    public static void print(String message, boolean append)
    {
        if (!append)
        {
            message = _dateFormat.format(Calendar.getInstance().getTime()) + " " + message;
        }

        System.out.print(message);
    }
}
