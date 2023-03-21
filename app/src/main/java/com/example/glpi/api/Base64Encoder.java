package com.example.glpi.api;

import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import okhttp3.ResponseBody;

public class Base64Encoder {

    private Base64Encoder() {
    }

    public static String base64decode(String text) {
        String rtext = "";
        if(text == null) { return ""; }
        try {
            byte[] bdata = Base64.decode(text, Base64.DEFAULT);
            rtext = new String(bdata, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rtext.trim();
    }


    public static String base64encode(String text) {
        String rtext = "";
        if(text == null) { return ""; }
        try {
            byte[] data = text.getBytes("UTF-8");
            rtext = Base64.encodeToString(data, Base64.DEFAULT);
            rtext = rtext.trim().replace("==", "");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return rtext.trim();
    }


    public static boolean writeResponseBodyToDisk(ResponseBody body, String pathname) {
        try {
            File futureStudioIconFile = new File(pathname);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
