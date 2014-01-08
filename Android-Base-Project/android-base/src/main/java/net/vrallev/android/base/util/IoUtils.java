package net.vrallev.android.base.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
}
