package com.example.ubun17.walmart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://api.walmartlabs.com/v1/search?query=desk&format=json&apiKey=usu6xmuc5dha75jepwnp2ds9";
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        data = (TextView) findViewById(R.id.searchResult);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "Network is active", Toast.LENGTH_SHORT).show();
            new DownloadTask().execute(URL);
        } else {
            Toast.makeText(this, "Check netWork", Toast.LENGTH_LONG).show();
        }
    }//End of onCreate

    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //return downloadUrl(urls[0]);
                return performPost();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            } catch (JSONException e) {
                return "JSON parsing issue: " + e.getMessage();
            }

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            data.setText(result);
        }
    }

    public String performPost() throws IOException, JSONException{
        DataOutputStream os = null;
        InputStream is = null;
        try {
            java.net.URL url = new URL("http://httpbin.org/post");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String urlParameters  = "param1=drew&param2=40&param3=c";
            byte[] postData = urlParameters.getBytes( Charset.forName("UTF-8") );
            int postDataLength = postData.length;
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            os = new DataOutputStream( conn.getOutputStream());
            os.write( postData );
            os.flush();

            is = conn.getInputStream();
            return readIt(is);
        }finally {
            if(is != null){
                is.close();
            }
            if(os != null){
                os.close();
            }
        }
    }

    public String readIt(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String read;

        while((read = br.readLine()) != null) {
            sb.append(read);
        }
        return sb.toString();
    }



}
