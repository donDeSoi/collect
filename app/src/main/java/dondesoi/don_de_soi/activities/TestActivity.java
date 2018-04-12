package dondesoi.don_de_soi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import dondesoi.don_de_soi.R;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_test, view_stub);
        String outputHtml = "";
        try{
            String ftpURL = "ftp://blah.com";
            URL url = new URL(ftpURL);
            URLConnection urlc = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedInputStream bis = new BufferedInputStream(urlc.getInputStream()); // ERROR HERE

            int inputLine;

            while ((inputLine = bis.read()) != -1) {
                outputHtml += inputLine;
            }

            bis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(outputHtml);
    }
}
