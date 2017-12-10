package be.defrere.wallr.util.http;

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
