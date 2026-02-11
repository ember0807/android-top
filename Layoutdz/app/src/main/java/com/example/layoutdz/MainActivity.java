package com.example.layoutdz;

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


    EditText input1, input2;
    TextView resultView;
    Button plus, minus, multiply, divide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_calculator);

        // связываем переменные с кнопками из XML
        input1 = findViewById(R.id.editTextNumber1);
        input2 = findViewById(R.id.editTextNumber2);
        plus = findViewById(R.id.button_p);
        minus = findViewById(R.id.button_min);
        multiply = findViewById(R.id.button_x);
        divide = findViewById(R.id.button_d);
        resultView = findViewById(R.id.result);

        // логика
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текст, переводим в числа и считаем
                double n1 = Double.parseDouble(input1.getText().toString());
                double n2 = Double.parseDouble(input2.getText().toString());
                double res = n1 + n2;
                resultView.setText("Result: " + n1 + " + " + n2 + " = " + res);
            }

        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текст, переводим в числа и считаем
                double n1 = Double.parseDouble(input1.getText().toString());
                double n2 = Double.parseDouble(input2.getText().toString());
                double res = n1 - n2;
                resultView.setText("Result: " + n1 + " - " + n2 + " = " + res);
            }

        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текст, переводим в числа и считаем
                double n1 = Double.parseDouble(input1.getText().toString());
                double n2 = Double.parseDouble(input2.getText().toString());
                double res = n1 * n2;
                resultView.setText("Result: " + n1 + " x " + n2 + " = " + res);
            }

        });

        divide = findViewById(R.id.button_d);
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = input1.getText().toString();
                String s2 = input2.getText().toString();

                if (!s1.isEmpty() && !s2.isEmpty()) {
                    double n1 = Double.parseDouble(s1);
                    double n2 = Double.parseDouble(s2);

                    // ПРОВЕРКА: Если второе число равно 0
                    if (n2 == 0) {

                        resultView.setText("Ошибка: на 0 делить нельзя!");
                    } else {
                        double res = n1 / n2;
                        resultView.setText("Result: " + n1 + " / " + n2 + " = " + res);
                    }
                }
            }
        });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}