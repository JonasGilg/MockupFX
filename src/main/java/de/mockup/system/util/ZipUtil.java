package de.mockup.system.util;

import de.mockup.system.exceptions.SystemErrorCodes;
import de.mockup.system.exceptions.SystemException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /**
     * Creates a zip file at the specified path with the contents of the specified directory.
     * NB:
     *
     * @param directoryPath The path of the directory where the archive will be created. eg. c:/temp
     * @param zipPath       The full path of the archive to create. eg. c:/temp/archive.zip
     * @throws IOException If anything goes wrong
     */
    public static void createZip(String directoryPath, String zipPath) throws SystemException {
        FileOutputStream fOut = null;
        BufferedOutputStream bOut = null;
        ZipOutputStream tOut = null;
        try {
            try {
                File source = new File(directoryPath);
                fOut = new FileOutputStream(new File(zipPath));
                bOut = new BufferedOutputStream(fOut);
                tOut = new ZipOutputStream(bOut);
                for (File f : source.listFiles()) {
                    addFileToZip(tOut, f.getPath(), "");
                }
            } finally {
                tOut.finish();
                tOut.close();
                bOut.close();
                fOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(SystemErrorCodes.FILE_SYSTEM_ERROR);
        }
    }

    /**
     * Creates a zip entry for the path specified with a name built from the base passed in and the file/directory
     * name. If the path is a directory, a recursive call is made such that the full directory is added to the zip.
     *
     * @param zOut The zip file's output stream
     * @param path The filesystem path of the file/directory being added
     * @param base The base prefix to for the name of the zip file entry
     * @throws IOException If anything goes wrong
     */
    private static void addFileToZip(ZipOutputStream zOut, String path, String base) throws IOException {
        File f = new File(path);
        String entryName = base + f.getName();
        if (f.isFile()) {
            ZipEntry zipEntry = new ZipEntry(entryName);
            zOut.putNextEntry(zipEntry);
            FileInputStream fInputStream = null;
            try {
                fInputStream = new FileInputStream(f);
                IOUtils.copy(fInputStream, zOut);
                zOut.closeEntry();
            } finally {
                IOUtils.closeQuietly(fInputStream);
            }

        } else {
            File[] children = f.listFiles();

            if (children != null) {
                for (File child : children) {
                    addFileToZip(zOut, child.getAbsolutePath(), entryName + "/");
                }
            }
        }
    }

    /**
     * Extract zip file at the specified destination path.
     * NB:archive must consist of a single root folder containing everything else
     *
     * @param archivePath     path to zip file
     * @param destinationPath path to extract zip file to. Created if it doesn't exist.
     */
    public static void extractZip(String archivePath, String destinationPath) throws SystemException {
        File archiveFile = new File(archivePath);
        File unzipDestFolder = new File(destinationPath);
        unzipFolder(archiveFile, unzipDestFolder);
    }

    /**
     * Unzips a zip file into the given destination directory.
     * <p>
     * The archive file MUST have a unique "root" folder. This root folder is
     * skipped when unarchiving.
     *
     * @return true if folder is unzipped correctly.
     */
    @SuppressWarnings("unchecked")
    private static boolean unzipFolder(File archiveFile,
                                       File zipDestinationFolder) throws SystemException {

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(archiveFile);
            byte[] buf = new byte[65536];

            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();
                System.out.println(name);
                name = name.replace('\\', '/');
                Path path = Paths.get(zipDestinationFolder.getAbsolutePath(), name);
                Files.createDirectories(path.getParent());
                Files.deleteIfExists(path);
                Files.createFile(path);
                File destinationFile = new File(zipDestinationFolder, name);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(destinationFile);
                    int n;
                    InputStream entryContent = zipFile.getInputStream(zipEntry);
                    while ((n = entryContent.read(buf)) != -1) {
                        if (n > 0) {
                            fos.write(buf, 0, n);
                        }
                    }
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(SystemErrorCodes.FILE_SYSTEM_ERROR);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    throw new SystemException(SystemErrorCodes.FILE_SYSTEM_ERROR);
                }
            }
        }
    }
}
