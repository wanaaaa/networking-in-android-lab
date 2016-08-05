package com.example.ubun17.walmart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private String URL = "http://api.walmartlabs.com/v1/search?query=desk&format=json&apiKey=usu6xmuc5dha75jepwnp2ds9";

    private TextView data;
    Button buCereal, buChoco, buTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = (TextView) findViewById(R.id.searchResult);

        buCereal = (Button) findViewById(R.id.buCereal) ;
        buChoco = (Button) findViewById(R.id.buChoco);
        buTea = (Button) findViewById(R.id.buTea) ;

        buCereal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                URL = "http://api.walmartlabs.com/v1/search?query=cereal&format=json&apiKey=usu6xmuc5dha75jepwnp2ds9";
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(MainActivity.this, "Network is active", Toast.LENGTH_SHORT).show();
                    new DownloadTask().execute(URL);
                } else {
                    Toast.makeText(MainActivity.this, "asdf", Toast.LENGTH_LONG).show();
                }
            }
        });

        buChoco.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                URL = "http://api.walmartlabs.com/v1/search?query=chocolate&format=json&apiKey=usu6xmuc5dha75jepwnp2ds9";
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(MainActivity.this, "Network is active", Toast.LENGTH_SHORT).show();
                    new DownloadTask().execute(URL);
                } else {
                    Toast.makeText(MainActivity.this, "asdf", Toast.LENGTH_LONG).show();
                }
            }
        });

        buTea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                URL = "http://api.walmartlabs.com/v1/search?query=tea&format=json&apiKey=usu6xmuc5dha75jepwnp2ds9";
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(MainActivity.this, "Network is active", Toast.LENGTH_SHORT).show();
                    new DownloadTask().execute(URL);
                } else {
                    Toast.makeText(MainActivity.this, "asdf", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
                //return performPost();
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

    // Given a URL, establishes an HttpUrlConnection and retrieves the web page content as a
    // InputStream, which it returns as a string.
    private String downloadUrl(String myUrl) throws IOException, JSONException {
        InputStream is = null;

        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // Starts the query
            conn.connect();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);

            /**
             * Uncomment this code for the demo part of the lesson to explain the students how
             * processing data returned from a network connection works. Change the return
             * value from contentAsString to processedJson that will be displayed in the text view.
             */
            String processedJson = parseJson(contentAsString);

            return processedJson;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String parseJson(String contentAsString) throws JSONException {
        String repoList = "";
        JSONArray array = new JSONArray(contentAsString);
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject repo = array.getJSONObject(i);
            String repoName = repo.getString("name");
            repoList += repoName + " \n";
        }
        return repoList;
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



}//End of MainActivity
