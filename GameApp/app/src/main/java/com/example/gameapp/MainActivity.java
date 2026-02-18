package com.example.gameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Находим все элементы
        EditText playerOne = findViewById(R.id.playerOne);
        EditText playerTwo = findViewById(R.id.playerTwo);
        Button startGameButton = findViewById(R.id.startGameButton);
        CheckBox vsComputer = findViewById(R.id.vsComputerCheckbox);
        Spinner difficulty = findViewById(R.id.difficultySpinner);

        // 2. Логика переключения режима ПК
        vsComputer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                difficulty.setVisibility(View.VISIBLE);
                playerTwo.setText("Компьютер");
                playerTwo.setEnabled(false); // Чтобы нельзя было стереть имя бота
            } else {
                difficulty.setVisibility(View.GONE);
                playerTwo.setText("");
                playerTwo.setEnabled(true);
            }
        });

        // 3. обработчик кнопки Старт
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPlayerOneName = playerOne.getText().toString();
                String getPlayerTwoName = playerTwo.getText().toString();

                if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Пожалуйста, введите имена", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, PlayingField.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", getPlayerTwoName);

                    // Передаем данные о боте
                    intent.putExtra("isVsComputer", vsComputer.isChecked());
                    // Берем выбранную сложность из Spinner
                    intent.putExtra("difficulty", difficulty.getSelectedItem().toString());

                    startActivity(intent);
                }
            }
        });
    }
}
