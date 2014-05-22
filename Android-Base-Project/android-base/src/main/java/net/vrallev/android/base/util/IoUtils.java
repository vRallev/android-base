package net.vrallev.android.base.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for several IO operations.
 *
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public final class IoUtils {

    private IoUtils() {
        // no op
    }

    /**
     * Reads the stream till the end is reached.
     *
     * @param stream The {@link java.io.InputStream}, which gets read.
     * @return A {@link java.util.List} containing all read lines.
     * @throws java.io.IOException If an IO issue occurs.
     */
    public static List<String> readLines(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> result = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

    /**
     * Reads the stream till end is reached.
     *
     * @param stream The {@link java.io.InputStream}, which gets read.
     * @return A {@link String} containing the whole content from the stream.
     * @throws java.io.IOException If an IO issue occurs.
     */
    public static String readFully(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    /**
     * Tries to close the {@link java.io.Closeable}, if it is not {@code null}.
     *
     * @param closeable The object, which should get closed.
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * Copy bytes from a large (over 2GB) InputStream to an OutputStream.
     * This method buffers the input internally, so there is no need to use a BufferedInputStream.
     *
     * @param input the InputStream to read from
     * @param output the OutputStream to write to
     * @return the number of bytes copied
     * @throws IOException if an I/O error occurs
     */
    public static long copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static long copy(File src, File dst) throws IOException {
        if (!dst.exists() && !dst.createNewFile()) {
            throw new IOException("Could not create dst file.");
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(src);
            outputStream = new FileOutputStream(dst);
            return copy(inputStream, outputStream);

        } finally {
            IoUtils.closeQuietly(inputStream);
            IoUtils.closeQuietly(outputStream);
        }
    }

    public static boolean deleteDir(File file) {
        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            boolean result = true;

            if (files != null) {
                for (File child : files) {
                    result &= deleteDir(child);
                }

                result &= file.delete();

            } else {
                result = false;
            }

            return result;
        }
    }
}
