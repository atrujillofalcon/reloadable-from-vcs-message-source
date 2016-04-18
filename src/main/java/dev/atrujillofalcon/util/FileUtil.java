package dev.atrujillofalcon.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static void deleteRecursiveFolderContent(File curFile, boolean saveRoot) {
        if (curFile.exists()) {
            if (curFile.isFile()) {
                curFile.delete();
            } else if (curFile.isDirectory()) {
                File[] children = curFile.listFiles();
                for (File child : children)
                    deleteRecursiveFolderContent(child, false);
                if (!saveRoot) curFile.delete();
            }
        }
    }

    public static File createCleanTempFolder(String name) throws IOException {
        File tmpFolder = new File("/tmp/" + name);
        if (!tmpFolder.exists() && !tmpFolder.mkdir())
            throw new IOException("Cannot create tmp folder");

        deleteRecursiveFolderContent(tmpFolder, true);
        return tmpFolder;
    }

    public static boolean deleteTempFolder(String tempFolderName) {
        File tmpFolder = new File("/tmp/" + tempFolderName);
        return tmpFolder.delete();
    }

    public static int nativeToAsciiFileCopy(File fromFile, File toFile) {
        String cmdNativeToAscii = "native2ascii -encoding iso8859-1 " + fromFile.getAbsolutePath() + " " + toFile.getAbsolutePath();
        String javaHomePath = System.getenv("JAVA_HOME");
        if (javaHomePath != null && !javaHomePath.isEmpty())
            cmdNativeToAscii = javaHomePath + "/bin/" + cmdNativeToAscii;

        int result;
        try {
            result = Runtime.getRuntime().exec(cmdNativeToAscii).waitFor();
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }


}
