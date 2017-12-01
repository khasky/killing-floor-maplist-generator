package com.khasky;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Khasky
 * @see www.khasky.com
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
            Logger.print("Error on reading file: " + filePath);
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
