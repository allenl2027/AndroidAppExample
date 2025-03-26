package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText num1;
    private EditText num2;
    private Button add;
    private TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        num1=(EditText) findViewById(R.id.editTextNumber);
        num2=(EditText) findViewById(R.id.editTextNumber2);
        add=(Button) findViewById(R.id.button);
        answer=(TextView) findViewById(R.id.textView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1=checkNumber(num1.getText().toString());
                int number2=checkNumber(num2.getText().toString());
                int sum=number1+number2;

                answer.setText("The answer is: "+sum);
            }
        });
    }

    private int checkNumber(String num){
        int number;
        try {
            number=Integer.parseInt(num);
        } catch (NumberFormatException e) {
            number=0;
        }
        return number;
    }


}