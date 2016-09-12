package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean userStartedFirst = false;

    private static final String TAG = "TAG";
    private boolean calledOnStart = false;

    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    private TextView wordFragment;
    private TextView gameStatus;
    private Button challengeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        wordFragment = (TextView) findViewById(R.id.ghostText);
        gameStatus = (TextView) findViewById(R.id.gameStatus);

        challengeButton = (Button) findViewById(R.id.challengeButton);
        challengeButton.setOnClickListener(this);

        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.challengeButton:
                challengeButtonHandler();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_A:
                addLetterAndCheckWord("a");
                break;
            case KeyEvent.KEYCODE_B:
                addLetterAndCheckWord("b");
                break;
            case KeyEvent.KEYCODE_C:
                addLetterAndCheckWord("c");
                break;
            case KeyEvent.KEYCODE_D:
                addLetterAndCheckWord("d");
                break;
            case KeyEvent.KEYCODE_E:
                addLetterAndCheckWord("e");
                break;
            case KeyEvent.KEYCODE_F:
                addLetterAndCheckWord("f");
                break;
            case KeyEvent.KEYCODE_G:
                addLetterAndCheckWord("g");
                break;
            case KeyEvent.KEYCODE_H:
                addLetterAndCheckWord("h");
                break;
            case KeyEvent.KEYCODE_I:
                addLetterAndCheckWord("i");
                break;
            case KeyEvent.KEYCODE_J:
                addLetterAndCheckWord("j");
                break;
            case KeyEvent.KEYCODE_K:
                addLetterAndCheckWord("k");
                break;
            case KeyEvent.KEYCODE_L:
                addLetterAndCheckWord("l");
                break;
            case KeyEvent.KEYCODE_M:
                addLetterAndCheckWord("m");
                break;
            case KeyEvent.KEYCODE_N:
                addLetterAndCheckWord("n");
                break;
            case KeyEvent.KEYCODE_O:
                addLetterAndCheckWord("o");
                break;
            case KeyEvent.KEYCODE_P:
                addLetterAndCheckWord("p");
                break;
            case KeyEvent.KEYCODE_Q:
                addLetterAndCheckWord("q");
                break;
            case KeyEvent.KEYCODE_R:
                addLetterAndCheckWord("r");
                break;
            case KeyEvent.KEYCODE_S:
                addLetterAndCheckWord("s");
                break;
            case KeyEvent.KEYCODE_T:
                addLetterAndCheckWord("t");
                break;
            case KeyEvent.KEYCODE_U:
                addLetterAndCheckWord("u");
                break;
            case KeyEvent.KEYCODE_V:
                addLetterAndCheckWord("v");
                break;
            case KeyEvent.KEYCODE_W:
                addLetterAndCheckWord("w");
                break;
            case KeyEvent.KEYCODE_X:
                addLetterAndCheckWord("x");
                break;
            case KeyEvent.KEYCODE_Y:
                addLetterAndCheckWord("y");
                break;
            case KeyEvent.KEYCODE_Z:
                addLetterAndCheckWord("z");
                break;
            default:
                return super.onKeyUp(keyCode, event);
        }
        computerTurn();
        return true;
    }

    private void addLetterAndCheckWord(String letter) {
        String word = wordFragment.getText() + letter;
        wordFragment.setText(word);

        if(dictionary.isWord(word)) {
            gameStatus.setText("WINNER");
        }
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        if(!calledOnStart) {
            userStartedFirst = userTurn;
            calledOnStart = true;
        }

        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private boolean checkWordValidity() {
        String word = (String) wordFragment.getText();
        if(dictionary.isWord(word) && word.length() >= 4) {
            return true;
        }
        return false;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String word = (String) wordFragment.getText();
        if(checkWordValidity()) {
            label.setText("COMPUTER WON");
            return;
        }

        String longerWord = dictionary.getAnyWordStartingWith(word);
        if(longerWord == null) {
            challengeButtonHandler();
            label.setText("USER WON");
            return;
        } else {
            if(word.length() + 1 < longerWord.length()) {
                wordFragment.setText(word + longerWord.substring(word.length(), word.length() + 1));
            }
        }
        userTurn = true;
        label.setText(USER_TURN);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        wordFragment.setText(savedInstanceState.getString("wordFragment"));
        gameStatus.setText(savedInstanceState.getString("turn"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("wordFragment", (String) wordFragment.getText());
        outState.putString("turn", (String) gameStatus.getText());
        super.onSaveInstanceState(outState);
    }

    private void challengeButtonHandler() {
        String word = (String) wordFragment.getText();
        String longerWord = dictionary.getAnyWordStartingWith(word);
        if(checkWordValidity()) {
            gameStatus.setText("USER WINS");
        } else if(longerWord != null) {
            gameStatus.setText("COMPUTER WINS");
            wordFragment.setText(longerWord);
        } else {
            gameStatus.setText("USER WINS");
        }
    }

    public void restartButtonHandler(View view) {
        onStart(view);
    }

}
