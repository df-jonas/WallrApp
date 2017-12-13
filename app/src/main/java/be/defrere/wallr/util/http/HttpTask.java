package be.defrere.wallr.util.http;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Map;

import be.defrere.wallr.util.helper.DateTimeHelper;

public class HttpTask extends AsyncTask<HttpRequest, Integer, HttpResponse> {

    private HttpInterface mContext;

    public HttpTask(Object context) {
        if (context instanceof HttpInterface) {
            this.mContext = (HttpInterface) context;
        }
    }

    @Override
    protected void onPreExecute() {
        System.out.println("========= [" + DateTimeHelper.getTimestamp() + "] Task started.");
    }

    @Override
    protected HttpResponse doInBackground(HttpRequest... request) {
        HttpRequest r = request[0];

        int responseCode = -1;
        HttpURLConnection client = null;
        StringBuilder content = new StringBuilder();

        try {
            client = (HttpURLConnection) r.getUrl().openConnection();
            client.setRequestMethod(r.getVerb());
            client.setRequestProperty("Accept", "application/json");
            if (r.getHeaders() != null) {
                for (Map.Entry<String, String> pair : r.getHeaders().entrySet()) {
                    client.setRequestProperty(pair.getKey(), pair.getValue());
                }
            }

            if (r.getVerb().equals("GET")) {
                client.setDoOutput(true);
                OutputStream os = client.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(r.getParams());

                writer.flush();
                writer.close();
                os.close();
            }

            responseCode = client.getResponseCode();

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while ((line = br.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e1) {
            System.err.println("Error: " + e1.getMessage());
            // Get error from request
            try {
                if (client != null) {
                    responseCode = client.getResponseCode();
                    System.err.println("Got response code " + responseCode + ".");

                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getErrorStream()));
                    while ((line = br.readLine()) != null) {
                        content.append(line);
                    }
                }
            } catch (IOException e2) {
                e2.getStackTrace();
            }

        } finally {
            if (client != null)
                client.disconnect();
        }

        return new HttpResponse(responseCode, content.toString());
    }

    @Override
    protected void onPostExecute(HttpResponse result) {
        if (mContext != null) {
            mContext.httpCallback(result);
        }
        System.out.println("Received response: " + result.getResponseText());
        System.out.println("========= [" + DateTimeHelper.getTimestamp() + "] Task ended.");
    }
}
