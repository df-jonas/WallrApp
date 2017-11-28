package be.defrere.wallr.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

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
            System.out.println(source + ": " + message);
        }
    }
}
