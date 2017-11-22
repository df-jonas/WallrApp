package be.defrere.wallr.http;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public static void setUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public URL getUrl() {
        try {
            return new URL(API_URL + api_path);
        } catch (MalformedURLException e) {
            e.getStackTrace();
        }
        return null;
    }

    private String convertKeyValuePair(Map.Entry<String, String> pair) throws UnsupportedEncodingException {
        return URLEncoder.encode(pair.getKey(), "UTF-8") + "=" + URLEncoder.encode(pair.getValue(), "UTF-8");
    }

    private String convertParams(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder queryString = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> pair : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                queryString.append("&");
            }
            queryString.append(convertKeyValuePair(pair));
        }
        return queryString.toString();
    }

    public String getVerb() {
        switch (verb) {
            case GET:
                return "GET";
            case POST:
                return "POST";
            case PUT:
                return "PUT";
            case DELETE:
                return "DELETE";
            default:
                return "GET";
        }
    }

    public String getParams() {
        try {
            if (params == null)
                return "";

            return convertParams(params);

        } catch (UnsupportedEncodingException e) {
            e.getStackTrace();
            return "";
        }
    }

    public HttpRequest(String api_path, HttpVerb verb, HashMap<String, String> params, HashMap<String, String> headers) {
        this.api_path = api_path;
        this.verb = verb;
        this.params = params;
        this.headers = headers;
    }

    public HttpRequest(String api_path, HttpVerb verb) {
        this(api_path, verb, null, null);
    }

    private String api_path;
    private HttpVerb verb;
    private HashMap<String, String> params;

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    private HashMap<String, String> headers;

    private static String API_URL;
}