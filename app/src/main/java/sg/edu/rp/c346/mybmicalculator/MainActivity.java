package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvInfo = findViewById(R.id.textViewInfo);

        etHeight.requestFocus();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strWeight = etWeight.getText().toString();
                String strHeight = etHeight.getText().toString();
                double dblWeight = Double.parseDouble(strWeight);
                double dblHeight = Double.parseDouble(strHeight);
                double dblBMI = (dblWeight/(dblHeight*dblHeight));
                tvBMI.setText("Last calculated BMI: " + Double.toString(dblBMI));

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date: " + datetime);

                if (dblBMI < 18.5) {
                    tvInfo.setText("You are underweight.");
                }
                else if (dblBMI <25) {
                    tvInfo.setText("Your BMI is normal");
                }
                else if (dblBMI < 30) {
                    tvInfo.setText("You are overweight");
                }
                else {
                    tvInfo.setText("You are obese");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last calculated date: ");
                tvBMI.setText("Last calculated BMI: ");
                tvInfo.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 1: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Step 2: Retrieve the saved data from the SharedPreferences object
        String msgBMI = prefs.getString("BMI", tvBMI.getText().toString());
        String msgDate = prefs.getString("date", tvDate.getText().toString());
        //Step 3: Update the UI element with the value
        tvBMI.setText(msgBMI);
        tvDate.setText(msgDate);


    }

    @Override
    protected void onPause() {
        super.onPause();

        //Step 1: Get the user input from the EditText and store it in a variable
        String strBMI = tvBMI.getText().toString();
        String strDate = tvDate.getText().toString();
        //Step 2: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Step 3: Obtain an instance of the SharedPreferences Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();
        //Step 4: Add the value-key pair
        //          The value should be from the variable defined in Step 1
        prefEdit.putString("BMI", strBMI);
        prefEdit.putString("date", strDate);
        //Step 5: Call commit() method to save the changes into SharedPreferences
        prefEdit.commit();

    }

}



