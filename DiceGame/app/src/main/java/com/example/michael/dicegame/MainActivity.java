package com.example.michael.dicegame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Random random = new Random();

    private TextView userNumberView;
    private TextView computerNumberView;
    private TextView userTurnNumberView;
    private TextView computerTurnNumberView;
    private TextView titleView;
    private TextView messageView;

    private ImageView dice;

    private Button roll;
    private Button hold;
    private Button reset;

    private int totalComputerScore = 0;
    private int totalUserScore = 0;
    private int turnComputerScore = 0;
    private int turnUserScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Scarne's Dice");

        userNumberView = (TextView) findViewById(R.id.userNumberId);
        computerNumberView = (TextView) findViewById(R.id.computerNumberId);
        userTurnNumberView = (TextView) findViewById(R.id.userTurnNumId);
        computerTurnNumberView = (TextView) findViewById(R.id.computerTurnNumId);

        titleView = (TextView) findViewById(R.id.titleViewId);
        messageView = (TextView) findViewById(R.id.messageViewId);

        dice = (ImageView) findViewById(R.id.diceImageViewId);

        roll = (Button) findViewById(R.id.rollButtonId);
        roll.setOnClickListener(this);
        hold = (Button) findViewById(R.id.holdButtonId);
        hold.setOnClickListener(this);
        reset = (Button) findViewById(R.id.resetButtonId);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.rollButtonId:
                rollHandler(true);
                break;
            case R.id.holdButtonId:
                holdHandler(true);
                break;
            case R.id.resetButtonId:
                resetHandler();
                break;
            default:
                break;
        }
    }

    private void rollHandler(boolean playerRoll) {
        final int diceNumber = random.nextInt(6) + 1;
        if(playerRoll) {
            if(diceNumber == 1) {
                turnUserScore = 0;

                titleView.setText("COMPUTER TURN!");
                messageView.setText("You rolled a 1! \nYou lost your turn!");

                rollHandler(false);
            } else {
                turnUserScore += diceNumber;

                messageView.setText("You rolled a " + String.valueOf(diceNumber) + "!");

                if(totalUserScore + turnUserScore >= 100) {
                    titleView.setText("YOU WON!");
                    userNumberView.setText(String.valueOf(totalUserScore + turnUserScore));
                    turnUserScore = 0;
                    enableClickableButtons(false, true);
                }
            }
            userTurnNumberView.setText(String.valueOf(turnUserScore));
            changeDicePicture(diceNumber);
        } else {
            enableClickableButtons(false, false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(diceNumber == 1) {
                        turnComputerScore = 0;

                        titleView.setText("YOUR TURN!");
                        messageView.setText("Computer rolled a 1! \nComputer lost their turn!");
                        enableClickableButtons(true, true);
                    } else {
                        turnComputerScore += diceNumber;
                        messageView.setText("Computer rolled a " + String.valueOf(diceNumber) + "!");

                        if(totalComputerScore + turnComputerScore < 100 && turnComputerScore < getComputerTurnCap()) {
                            rollHandler(false);
                        } else {
                            if (totalComputerScore + turnComputerScore >= 100) {
                                titleView.setText("COMPUTER WON!");
                                computerNumberView.setText(String.valueOf(totalComputerScore + turnComputerScore));
                                turnComputerScore = 0;
                                enableClickableButtons(false, true);
                            } else {
                                if (turnComputerScore >= getComputerTurnCap()) {
                                    titleView.setText("YOUR TURN!");
                                    messageView.setText("Computer has chosen to hold!");
                                    holdHandler(false);
                                    enableClickableButtons(true, true);
                                }
                            }
                        }
                    }
                    computerTurnNumberView.setText(String.valueOf(turnComputerScore));
                    changeDicePicture(diceNumber);
                }
            }, 3000);
        }
    }

    private int getComputerTurnCap() {
        int scoreDifference = totalUserScore - totalComputerScore;
        int turnCap;
        if(scoreDifference >= 80) {
            turnCap = 50;
        } else if(scoreDifference >= 50) {
            turnCap = 30;
        } else if(scoreDifference >= 20) {
            turnCap = 20;
        } else {
            // Difference is below 20 or the computer is winning, set to 15
            turnCap = 15;
        }
        return turnCap;
    }

    private void changeDicePicture(int num) {
        switch(num) {
            case 1:
                dice.setImageResource(R.drawable.dice1);
                break;
            case 2:
                dice.setImageResource(R.drawable.dice2);
                break;
            case 3:
                dice.setImageResource(R.drawable.dice3);
                break;
            case 4:
                dice.setImageResource(R.drawable.dice4);
                break;
            case 5:
                dice.setImageResource(R.drawable.dice5);
                break;
            case 6:
                dice.setImageResource(R.drawable.dice6);
                break;
            default:
                break;
        }
    }

    private void holdHandler(boolean playerTurn) {
        if(playerTurn) {
            totalUserScore += turnUserScore;
            userNumberView.setText(String.valueOf(totalUserScore));

            turnUserScore = 0;
            userTurnNumberView.setText("0");
            titleView.setText("COMPUTER TURN!");

            rollHandler(false);
        } else {
            totalComputerScore += turnComputerScore;
            computerNumberView.setText(String.valueOf(totalComputerScore));

            turnComputerScore = 0;
            computerTurnNumberView.setText("0");
        }
    }

    private void resetHandler() {
        totalUserScore = 0;
        totalComputerScore = 0;
        turnUserScore = 0;
        turnComputerScore = 0;

        userNumberView.setText("0");
        computerNumberView.setText("0");
        userTurnNumberView.setText("0");
        computerTurnNumberView.setText("0");

        titleView.setText("YOUR TURN!");
        messageView.setText("");

        dice.setImageResource(R.drawable.dice1);

        enableClickableButtons(true, true);
    }

    private void enableClickableButtons(boolean enable, boolean resetButton) {
        roll.setEnabled(enable);
        hold.setEnabled(enable);
        reset.setEnabled(resetButton);
    }
}
















