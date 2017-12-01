package com.khasky;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

/**
 * @author Khasky
 * @see www.khasky.com
 */
public class Generator
{
    public static void main(String[] args)
    {
        if (args.length < 4)
        {
            Logger.print("Wrong commandline arguments");
            return;
        }

        String mapsFolder = args[0];
        String mapFileExtension = args[1];
        String excludeFile = args[2];
        String outputFile = args[3];

        File folderFile = new File(mapsFolder);
        File[] listOfFilesInFolder = folderFile.listFiles();

        if (listOfFilesInFolder.length == 0)
        {
            Logger.print("There are no files in " + mapsFolder + " folder.");
            return;
        }

        ArrayList<String> excludedMapsList = Util.getLinesFromFile(excludeFile);
        ArrayList<String> actualMapsList = new ArrayList<>();

        for (File file : listOfFilesInFolder)
        {
            // Skip empty files (broken?)
            if (file.length() == 0)
            {
                continue;
            }

            // Skip sub directories
            if (!file.isFile())
            {
                continue;
            }

            final String fileName = file.getName();
            final String fileExtension = Util.getExtensionFromName(fileName);

            // Skip invalid map file extension
            if (fileExtension == null || !fileExtension.equalsIgnoreCase(mapFileExtension))
            {
                continue;
            }

            final String fileMapName = Util.getNameWithoutExtension(fileName);

            if (fileMapName == null)
            {
                continue;
            }

            boolean isExcluded = excludedMapsList.stream().anyMatch(fileMapName::equalsIgnoreCase);

            // Skip excluded maps
            if (isExcluded)
            {
                Logger.print("Map skipped: " + fileMapName);
                continue;
            }

            actualMapsList.add(fileMapName);
        }

        if (actualMapsList.size() == 0)
        {
            Logger.print("There are no valid maps to generate the list.");
            return;
        }

        Logger.print("Generating maps list...");

        writeMapsListToFile(actualMapsList, outputFile);
    }

    public static boolean writeMapsListToFile(ArrayList<String> mapsList, String filePath)
    {
        if (mapsList == null || mapsList.isEmpty())
        {
            return false;
        }

        int count = 0;

        for (String mapName : mapsList)
        {
            Util.appendLineToFile(filePath, "Maps=" + mapName, null);

            count++;

            int progress = (int) (count / ((double) mapsList.size() / 100));

            if (progress % 5 == 0)
            {
                System.out.print(progress + "% ");
            }
        }

        return true;
    }
}
