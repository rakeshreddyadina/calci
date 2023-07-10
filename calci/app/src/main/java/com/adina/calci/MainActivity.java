package com.adina.calci;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;



public class MainActivity extends AppCompatActivity {
    TextView solution, result;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, open, close, dot, add, sub, multi, div, ac, c, equal;
    String dataToCalculate = "";
    String finalResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solution = findViewById(R.id.solution);
        result = findViewById(R.id.result);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        open = findViewById(R.id.brac_open);
        close = findViewById(R.id.brac_close);
        dot = findViewById(R.id.dot);
        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        multi = findViewById(R.id.multi);
        div = findViewById(R.id.div);
        ac = findViewById(R.id.ac);
        c = findViewById(R.id.c);
        equal = findViewById(R.id.equal);
    }

    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        dataToCalculate = result.getText().toString();
        if (buttonText.equals("AC")) {
            dataToCalculate = "";
            solution.setText("");
            result.setText("");
        } else if (buttonText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0, result.getText().toString().length() - 1);
            result.setText(dataToCalculate);
            solution.setText(dataToCalculate);
            if(result.getText().toString().isEmpty()){
                dataToCalculate = "";
                result.setText(solution.getText());
                solution.setText(dataToCalculate);
            }
            else {
                finalResult = getResult(dataToCalculate);
                if (!finalResult.equals("Err")) {
                    solution.setText(finalResult);
                }
            }
        } else if (buttonText.equals("=")) {
            finalResult = getResult(dataToCalculate);
            if(!finalResult.equals("Err")){
                solution.setText(finalResult);
            }
            result.setText(solution.getText());
            dataToCalculate = result.getText().toString();
        } else {
            dataToCalculate = dataToCalculate + buttonText;
            result.setText(dataToCalculate);
            finalResult = getResult(dataToCalculate);
            if(!finalResult.equals("Err")){
                solution.setText(finalResult);
            }
        }

    }
    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}

