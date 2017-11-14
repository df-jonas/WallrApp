package be.defrere.wallr.http;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import be.defrere.wallr.helpers.DateTimeHelper;

public class HttpTask extends AsyncTask<HttpRequest, Integer, HttpResponse> {

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
            client.setRequestProperty("Authorization", "Bearer yCooI73EUFHfBR5lOpPS0CQBQsuD04em8NcoMeOWUeAmSWrjsEhxzjO5ir0a");

            if (r.getVerb() != "GET") {
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
        System.out.println("========= [" + DateTimeHelper.getTimestamp() + "] Task ended.");
    }
}
