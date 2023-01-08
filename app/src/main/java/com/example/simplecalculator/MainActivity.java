package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;




import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;
    String buttonText;
    String finalResult = "";
    String dataToCalculate = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        if (isNightModeEnabled()) {

            resultTv.setTextColor(Color.WHITE);
            solutionTv.setTextColor(Color.WHITE);
        } else {
            resultTv.setTextColor(Color.BLACK);
            solutionTv.setTextColor(Color.BLACK);
        }

        assignId(buttonC, R.id.button_c);
        assignId( buttonBracketOpen, R.id.button_open_bracket);
        assignId(buttonBracketClose, R.id.button_close_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_add);
        assignId(buttonMinus, R.id.button_subtract);
        assignId(buttonEquals, R.id.button_equal);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDot, R.id.button_dot);
    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }






    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view; //whatever button is clicked

            buttonText = button.getText().toString(); // it will get the text assigned initially to the button





             dataToCalculate = solutionTv.getText().toString();





        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }


        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }

        Log.d("TAG", "dataToCalculate: " + dataToCalculate);

        Log.d("TAG", "buttonText: " + buttonText);
        if(buttonText.equals("C") && dataToCalculate.length() == 0){
            Log.d("test", "test");
            resultTv.setText("0");
            return;
        } else if (buttonText.equals("C") && dataToCalculate.length() > 0) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        }








      if(!buttonText.equals("C") ){
            dataToCalculate = dataToCalculate+buttonText;

        }

        solutionTv.setText(dataToCalculate);

        if(dataToCalculate.length() > 0){
            dataToCalculate = dataToCalculate.replace("X", "*");
            finalResult = getResult(dataToCalculate);

        }



        if(!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }




    }

    //get result method

    String getResult(String data) {
        try {
            //doesn't process bracket
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
        String finalResult =    context.evaluateString(scriptable,data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");

            }
        return finalResult;
        } catch (Exception e) {
            return  "Err";

        }

    }


    private boolean isNightModeEnabled() {
        int uiMode = getResources().getConfiguration().uiMode; //checks the night mode of the device
        return (uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }


}