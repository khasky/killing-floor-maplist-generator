package com.khasky;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Khasky
 */
public class Generator
{
    public static void main(String[] args)
    {
        if (args.length < 4)
        {
            Logger.printLine("Wrong commandline arguments");
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
            Logger.printLine("There are no files in " + mapsFolder + " folder.");
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
                Logger.printLine("Map skipped: " + fileMapName);
                continue;
            }

            actualMapsList.add(fileMapName);
        }

        if (actualMapsList.size() == 0)
        {
            Logger.printLine("There are no valid maps to generate the list.");
            return;
        }

        Logger.printLine("Generating maps list...");

        writeMapsListToFile(actualMapsList, outputFile);
    }

    public static boolean writeMapsListToFile(ArrayList<String> mapsList, String filePath)
    {
        if (mapsList == null || mapsList.isEmpty())
        {
            return false;
        }

        if (filePath == null || filePath.isEmpty())
        {
            return false;
        }

        Util.saveFileBackupIfExists(filePath);

        int processed = 0;
        double progressBefore = 0;

        for (String mapName : mapsList)
        {
            Util.appendLineToFile(filePath, "Maps=" + mapName, null);

            processed++;

            double progress = processed * 100 / mapsList.size();

            if (progress % 10 == 0 && Math.round(progress) > Math.round(progressBefore))
            {
                Logger.print(((int) progress) + "% ", progressBefore != 0);
            }

            progressBefore = progress;
        }

        return true;
    }
}
