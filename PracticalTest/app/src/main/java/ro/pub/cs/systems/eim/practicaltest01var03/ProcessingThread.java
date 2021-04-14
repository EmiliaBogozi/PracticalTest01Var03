package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.Date;

public class ProcessingThread extends Thread {
    private Context context = null;
    private int r1;
    private int r2;

    public ProcessingThread(Context context, int a, int b) {
        this.context = context;
        r1 = a + b;
        r2 = a - b;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread has started! ");
        sendMessage("Suma este ", String.valueOf(r1));
        sleep();
        sendMessage("Diferenta este ", String.valueOf(r2));
        sleep();
        Log.d(Constants.TAG, "Thread has stopped!");
    }

    private void sendMessage(String m, String msg) {
        Intent intent = new Intent();
        intent.setAction(Constants.INTENT_ACTION);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                m + msg);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
