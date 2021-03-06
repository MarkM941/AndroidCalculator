package com.personal.mmillstein.androidcalculator;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CalculatorUtils calculatorUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clearCalculator();
    }

    public void clearCalculator() {
        this.calculatorUtils = new CalculatorUtils();
        TextView calculatorTextView = (TextView) findViewById(R.id.text_calculator);
        calculatorTextView.setText("0");
    }

    public void handleClearButtonPress(View view) {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(50);
        clearCalculator();

    }

    public void handleNumberButtonPress(View view) {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(50);
        String calcString = calculatorUtils.addDigit(((Button) view).getText().toString());
        TextView calculatorTextView = (TextView) findViewById(R.id.text_calculator);
        calculatorTextView.setText(calcString);
    }

    public void handleOperationButtonPress(View view) {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(50);
        String calcString = calculatorUtils.addOperation((CalculatorUtils.Operation.from(((Button) view).getText().toString())));
        TextView calculatorTextView = (TextView) findViewById(R.id.text_calculator);
        calculatorTextView.setText(calcString);
    }

    public void handleEqualsButtonPress(View view) {
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(50);
        String calcString = calculatorUtils.calculate();
        TextView calculatorTextView = (TextView) findViewById(R.id.text_calculator);
        calculatorTextView.setText(calcString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
