package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private Button correctButton, incorrectButton;
    private TextView resultCalcul;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.correct_button:
                    setResult(RESULT_OK, intent);
                    break;
                case R.id.incorrect_button:
                    setResult(RESULT_CANCELED, intent);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        resultCalcul = (TextView)findViewById(R.id.result_calcul);

        Intent intent = getIntent();
        //Log.d(Constants.TAG, "gggg");
        if (intent != null && intent.getExtras().containsKey(Constants.RESULT_CALC)) {
            String result_calc = intent.getExtras().getString(Constants.RESULT_CALC);
            resultCalcul.setText(result_calc);
        }

        correctButton = (Button)findViewById(R.id.correct_button);
        correctButton.setOnClickListener(buttonClickListener);

        incorrectButton = (Button)findViewById(R.id.incorrect_button);
        incorrectButton.setOnClickListener(buttonClickListener);
    }
}