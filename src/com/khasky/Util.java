package com.khasky;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Khasky
 */
public class Util
{
    public static ArrayList<String> getLinesFromFile(String filePath)
    {
        ArrayList<String> linesList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            for (String line; (line = br.readLine()) != null; )
            {
                line = line.trim();

                // Accept INI comments
                if (line.isEmpty() || line.startsWith(";"))
                {
                    continue;
                }

                linesList.add(line);
            }
        }
        catch (Exception e)
        {
            Logger.printLine("Error on reading file: " + filePath);
            e.printStackTrace();
        }

        return linesList;
    }

    public static String getExtensionFromName(String fileName)
    {
        String extension = null;

        if (fileName == null || fileName.isEmpty())
        {
            return extension;
        }

        int pos = fileName.lastIndexOf('.');

        if (pos > 0)
        {
            extension = fileName.substring(pos + 1);
        }

        return extension;
    }

    public static String getNameWithoutExtension(String fileName)
    {
        String outName = null;

        if (fileName == null || fileName.isEmpty())
        {
            return outName;
        }

        int pos = fileName.lastIndexOf(".");

        if (pos > 0)
        {
            outName = fileName.substring(0, pos);
        }

        return outName;
    }

    public static void saveFileBackupIfExists(String filePath)
    {
        if (filePath == null || filePath.isEmpty())
        {
            return;
        }

        File file = new File(filePath);

        if (!file.exists() || !file.isFile())
        {
            return;
        }

        String baseFileName = file.getName();
        String extension = getExtensionFromName(baseFileName);
        String fileName = getNameWithoutExtension(baseFileName);

        String currentDateInFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());

        String backupFileName = fileName + "_" + currentDateInFormat + "." + extension;

        File backupFile = new File(backupFileName);

        file.renameTo(backupFile);
    }

    public static boolean appendLineToFile(String filePath, String line, String lineDelimiter)
    {
        if (line == null || line.isEmpty())
        {
            return false;
        }

        if (filePath == null || filePath.isEmpty())
        {
            return false;
        }

        if (lineDelimiter == null || lineDelimiter.isEmpty())
        {
            lineDelimiter = "\n";
        }

        try
        {
            File file = new File(filePath);
            FileWriter fw = new FileWriter(file.getName(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(line + lineDelimiter);

            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
