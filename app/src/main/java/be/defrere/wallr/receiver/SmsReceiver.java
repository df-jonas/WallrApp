package be.defrere.wallr.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import be.defrere.wallr.R;
import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.entity.Event;
import be.defrere.wallr.util.http.HttpInterface;
import be.defrere.wallr.util.http.HttpRequest;
import be.defrere.wallr.util.http.HttpResponse;
import be.defrere.wallr.util.http.HttpTask;
import be.defrere.wallr.util.http.HttpVerb;
import be.defrere.wallr.entity.Text;

public class SmsReceiver extends BroadcastReceiver implements HttpInterface {

    private AppDatabase db;

    @Override
    public void onReceive(Context context, Intent intent) {

        db = AppDatabase.getAppDatabase(context);

        if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

            Bundle b = intent.getExtras();
            String message = "";
            String source = "";

            if (b != null) {
                try {
                    Object[] pdus = (Object[]) b.get("pdus");
                    if (pdus != null) {
                        for (int i = 0; i < pdus.length; i++) {
                            SmsMessage m = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            source = m.getOriginatingAddress();
                            message += m.getMessageBody();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Exception caught! " + e.getMessage());
                }
            }


            // TODO DEFTIG BEKIJKEN DIT

            // Create HTTP prerequisites
            HashMap<String, String> params = new HashMap<String, String>();
            HashMap<String, String> headers = new HashMap<String, String>();
            params.put("device_name", UUID.randomUUID().toString());
            headers.put("Authorization", "Bearer " + context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE).getString("api_token", ""));

            HttpRequest request = new HttpRequest("text", HttpVerb.POST, params, headers);

            // Execute and httpCallback
            new HttpTask(this).execute(request);

            List<Event> events = db.eventDao().all();
            for (Event e : events) {
                String k = e.getKeyword();

                if (message.length() > k.length() &&
                        message.substring(0, k.length() - 1).trim().toUpperCase().equals(k.trim().toUpperCase())) {
                    Text t = new Text();
                    t.setSource(source);
                    t.setContent(message);
                    db.textDao().insert(t);
                }
            }


            System.out.println(source + ": " + message);
        }
    }

    @Override
    public void httpCallback(HttpResponse httpResponse) {

    }
}
