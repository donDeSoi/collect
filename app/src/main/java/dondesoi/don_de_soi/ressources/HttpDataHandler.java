package dondesoi.don_de_soi.ressources;

/// FROM HERE : Class used by geocoder

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpDataHandler{
    public HttpDataHandler() {
    }

    public String getHTTPData(String requestURL)
    {
        URL url;
        String response ="";
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setReadTimeout(1500000000);
            conn.setConnectTimeout(1500000000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                String Line;
                BufferedReader br = new BufferedReader (new InputStreamReader(conn.getInputStream()));
                while ((Line = br.readLine()) != null)
                    response += Line;

            }
            else{
                System.out.println("http response code : "+ conn.getResponseCode());
                response ="";
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}