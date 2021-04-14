package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private Button addButton, diffButton, navigateToSecondaryActivity;
    private EditText firstNumber, secondNumber;
    private TextView result;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String text;
            String nr1, nr2;
            int nr1_1, nr2_2;

            nr1 = firstNumber.getText().toString();
            nr2 = secondNumber.getText().toString();

            nr1_1 = Integer.parseInt(nr1);
            nr2_2 = Integer.parseInt(nr2);

            if(v.getId() == R.id.sum_button && nr1 != "" && nr2 != "") {
                text = nr1 + " + " + nr2 + " = " + (nr1_1 + nr2_2);
                result.setText(text);
            }
            if(v.getId() == R.id.diff_button && nr1 != "" && nr2 != "") {
                text = nr1 + " - " + nr2 + " = " + (nr1_1 - nr2_2);
                result.setText(text);
            }
            if(nr1 == "" || nr2 == "")
                Log.d(Constants.TAG, "Nu s-au introdus valori");
            if(v.getId() == R.id.navigate_to_secondary_activity) {
                String message = result.getText().toString();
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                intent.putExtra(Constants.RESULT_CALC, message);
                startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
            }
            if(v.getId() == R.id.sum_button || v.getId() == R.id.diff_button) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                Log.d(Constants.TAG, "Main" + nr1_1 + " " + nr2_2);
                intent.putExtra(Constants.FIRST_NUMBER, nr1_1);
                intent.putExtra(Constants.SECOND_NUMBER, nr2_2);
                getApplicationContext().startService(intent);
            }
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        Log.d(Constants.TAG, "onCreate() method was invoked");

        addButton = (Button)findViewById(R.id.sum_button);
        addButton.setOnClickListener(buttonClickListener);

        diffButton = (Button)findViewById(R.id.diff_button);
        diffButton.setOnClickListener(buttonClickListener);

        navigateToSecondaryActivity = (Button)findViewById(R.id.navigate_to_secondary_activity);
        navigateToSecondaryActivity.setOnClickListener(buttonClickListener);

        firstNumber = (EditText)findViewById(R.id.number_1);
        firstNumber.setOnClickListener(buttonClickListener);

        secondNumber = (EditText)findViewById(R.id.number_2);
        secondNumber.setOnClickListener(buttonClickListener);

        result = (TextView)findViewById(R.id.result_calculate);
        result.setOnClickListener(buttonClickListener);

        intentFilter.addAction(Constants.INTENT_ACTION);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG, "onRestart() method was invoked");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.TAG, "onStart() method was invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop() method was invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.TAG, "onResume() method was invoked");
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
        Log.d(Constants.TAG, "onPause() method was invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "onDestroy() method was invoked");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(Constants.TAG, "onSaveInstanceState() method was invoked");

        savedInstanceState.putString(Constants.RESULT, result.getText().toString());
        savedInstanceState.putString(Constants.NUMBER1, firstNumber.getText().toString());
        savedInstanceState.putString(Constants.NUMBER2, secondNumber.getText().toString());

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(Constants.TAG, "onRestoreInstanceState() method was invoked");
        if(savedInstanceState.containsKey(Constants.RESULT))
        {
            result.setText(savedInstanceState.getString(Constants.RESULT));
            Log.d(Constants.TAG, String.valueOf(result));
        }
        if(savedInstanceState.containsKey(Constants.NUMBER1))
            firstNumber.setText(savedInstanceState.getString(Constants.NUMBER1));
        if(savedInstanceState.containsKey(Constants.NUMBER2))
            secondNumber.setText(savedInstanceState.getString(Constants.NUMBER2));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }
}