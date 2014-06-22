package net.vrallev.android.base.security;

import android.util.Base64;

import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class EncodingHelper {

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    public static final Charset ASCII = Charset.forName("US-ASCII");

    public static String bin2hex(byte[] data) {
        // http://stackoverflow.com/questions/7166129/how-can-i-calculate-the-sha-256-hash-of-a-string-in-android
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }

    public static byte[] hex2bin(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] convertUtf8(String string) {
        return string.getBytes(UTF_8);
    }

    public static String convertUtf8(byte[] bytes) {
        return new String(bytes, UTF_8);
    }

    public static byte[] convertUtf16(String string) {
        return string.getBytes(UTF_16);
    }

    public static String convertUtf16(byte[] bytes) {
        return new String(bytes, UTF_16);
    }

    public static byte[] convertAscii(String string) {
        return string.getBytes(ASCII);
    }

    public static String convertAscii(byte[] bytes) {
        return new String(bytes, ASCII);
    }

    public static String base64Encode(String string) {
        return base64Encode(convertUtf16(string));
    }

    public static String base64Encode(byte[] bytes) {
        return convertAscii(Base64.encode(bytes, Base64.NO_WRAP | Base64.URL_SAFE));
    }

    public static String base64Decode(String string) {
        return base64Decode(convertAscii(string));
    }

    public static String base64Decode(byte[] bytes) {
        return convertUtf16(Base64.decode(bytes, Base64.NO_WRAP | Base64.URL_SAFE));
    }
}
