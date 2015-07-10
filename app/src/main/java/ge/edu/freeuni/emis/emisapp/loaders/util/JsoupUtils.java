package ge.edu.freeuni.emis.emisapp.loaders.util;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by giorgi on 7/10/15.
 */
public class JsoupUtils {
     public static Document getDocumentFromFile(String fileName, Context context) {
         Document ret = null;
         try {
             StringBuilder builder = new StringBuilder();
             InputStream stream = context.getAssets().open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

             String line = reader.readLine();
             while (line != null) {
                 builder.append(line);
                 line = reader.readLine();
             }

             ret = Jsoup.parse(builder.toString());
         } catch (IOException e) {
             e.printStackTrace();
         }

         return ret;
     }
}
