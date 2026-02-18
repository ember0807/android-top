package com.example.gameapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayingField extends AppCompatActivity {

    private final List<int[]> combinationList = new ArrayList<>();
    private TextView playerOneName, playerTwoName;
    private int activePlayer = 1;
    private int[] boxPosition = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int totalSelectBoxes = 1;
    private int currentScoreX = 0, currentScoreY = 0;

    private boolean isVsComputer;
    private String difficulty;
    private boolean isBotThinking = false;

    private ImageView[] images = new ImageView[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_field);

        isVsComputer = getIntent().getBooleanExtra("isVsComputer", false);
        difficulty = getIntent().getStringExtra("difficulty");

        playerOneName = findViewById(R.id.playerOneName1);
        playerTwoName = findViewById(R.id.playerTwoName2);
        playerOneName.setText(getIntent().getStringExtra("playerOne"));
        playerTwoName.setText(getIntent().getStringExtra("playerTwo"));

        initCombinations();
        initViews();
    }

    private void initCombinations() {
        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{0, 4, 8});
        combinationList.add(new int[]{2, 4, 6});
    }

    private void initViews() {
        for (int i = 0; i < 9; i++) {
            int resID = getResources().getIdentifier("image" + (i + 1), "id", getPackageName());
            images[i] = findViewById(resID);
            final int pos = i;
            images[i].setOnClickListener(v -> {
                if (isBoxSelectable(pos) && !isBotThinking) {
                    performAction((ImageView) v, pos);
                }
            });
        }
    }

    private void performAction(ImageView imageView, int selectedPosition) {
        boxPosition[selectedPosition] = activePlayer;
        imageView.setBackgroundResource(R.drawable.white_box);
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        TextView scoreX = findViewById(R.id.scoreX);
        TextView scoreY = findViewById(R.id.scoreY);

        if (activePlayer == 1) {
            imageView.setImageResource(R.drawable.ximage);
            if (checkResults()) {
                showResult(playerOneName.getText() + " выиграл!");
                currentScoreX++;
                scoreX.setText(String.valueOf(currentScoreX));
            } else if (totalSelectBoxes == 9) {
                showResult("Ничья!");
            } else {
                changePlayerTurn(2);
                totalSelectBoxes++;
                if (isVsComputer) botMove();
            }
        } else {
            imageView.setImageResource(R.drawable.oimage);
            if (checkResults()) {
                showResult(playerTwoName.getText() + " выиграл!");
                currentScoreY++;
                scoreY.setText(String.valueOf(currentScoreY));
            } else if (totalSelectBoxes == 9) {
                showResult("Ничья!");
            } else {
                changePlayerTurn(1);
                totalSelectBoxes++;
            }
        }
    }

    private void botMove() {
        isBotThinking = true;
        new Handler().postDelayed(() -> {
            int move = ("Легко".equals(difficulty)) ? getRandomMove() : getBestMove();
            if (move != -1) performAction(images[move], move);
            isBotThinking = false;
        }, 600);
    }

    private int getRandomMove() {
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < 9; i++) if (boxPosition[i] == 0) available.add(i);
        return available.isEmpty() ? -1 : available.get(new Random().nextInt(available.size()));
    }

    private int getBestMove() {
        // Логика: 1. Победить самому 2. Заблокировать врага
        for (int p : new int[]{2, 1}) {
            for (int[] combo : combinationList) {
                int count = 0, empty = -1;
                for (int pos : combo) {
                    if (boxPosition[pos] == p) count++;
                    else if (boxPosition[pos] == 0) empty = pos;
                }
                if (count == 2 && empty != -1) return empty;
            }
        }
        return getRandomMove();
    }

    private boolean checkResults() {
        for (int[] combo : combinationList) {
            if (boxPosition[combo[0]] == activePlayer &&
                    boxPosition[combo[1]] == activePlayer &&
                    boxPosition[combo[2]] == activePlayer) return true;
        }
        return false;
    }

    private void changePlayerTurn(int turn) {
        activePlayer = turn;
        findViewById(R.id.playerOneLayout1).setBackgroundResource(turn == 1 ? R.drawable.black_border : R.drawable.white_box);
        findViewById(R.id.playerTwoLayout2).setBackgroundResource(turn == 2 ? R.drawable.black_border : R.drawable.white_box);
    }

    private boolean isBoxSelectable(int index) { return boxPosition[index] == 0; }

    private void showResult(String msg) {
        ResultDialog dialog = new ResultDialog(this, msg, this);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void restartMatch() {
        boxPosition = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        activePlayer = 1;
        totalSelectBoxes = 1;
        for (ImageView img : images) img.setImageResource(R.drawable.white_box);
        changePlayerTurn(1);
    }
}
