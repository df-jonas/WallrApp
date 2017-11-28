package be.defrere.wallr.http;

/**
 * Created by Jonas on 3/11/2017.
 */

public class HttpResponse {

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public HttpResponse(int responseCode, String responseText) {
        this.responseCode = responseCode;
        this.responseText = responseText;
    }

    private int responseCode;
    private String responseText;
}
