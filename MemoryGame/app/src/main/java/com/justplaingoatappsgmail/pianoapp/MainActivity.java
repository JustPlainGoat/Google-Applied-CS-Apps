package com.justplaingoatappsgmail.pianoapp;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int aquaSound;
    private int blackSound;
    private int blueSound;
    private int brownSound;
    private int graySound;
    private int greenSound;
    private int limeSound;
    private int orangeSound;
    private int pinkSound;
    private int purpleSound;
    private int redSound;
    private int yellowSound;
    private float actVolume, maxVolume, volume;

    private int moves = 3;

    //hashmap matches keys: color to values: view ID
    private HashMap<Integer, Integer> views;

    private int[] dark = new int[]{R.drawable.note_aqua, R.drawable.note_black,
            R.drawable.note_blue, R.drawable.note_brown, R.drawable.note_gray,
            R.drawable.note_green, R.drawable.note_lime, R.drawable.note_orange,
            R.drawable.note_pink, R.drawable.note_purple, R.drawable.note_red,
            R.drawable.note_yellow};

    private int[] light = new int[]{R.drawable.note_light_aqua, R.drawable.note_light_black,
            R.drawable.note_light_blue, R.drawable.note_light_brown, R.drawable.note_light_gray,
            R.drawable.note_light_green, R.drawable.note_light_lime, R.drawable.note_light_orange,
            R.drawable.note_light_pink, R.drawable.note_light_purple, R.drawable.note_light_red,
            R.drawable.note_light_yellow};

    private int[] sound;

    private LinkedListStack stack = new LinkedListStack();

    private ArrayDeque<Integer> queue = new ArrayDeque<>();

    private ArrayDeque<Integer> colorQueue = new ArrayDeque<>();

    private ImageView aqua;
    private ImageView black;
    private ImageView blue;
    private ImageView brown;
    private ImageView gray;
    private ImageView green;
    private ImageView lime;
    private ImageView orange;
    private ImageView pink;
    private ImageView purple;
    private ImageView red;
    private ImageView yellow;
    private TextView gameStatus;

    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MEMORY");

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        gameStatus = (TextView) findViewById(R.id.gameStatus);

        aqua = (ImageView) findViewById(R.id.aqua);
        aqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.aqua)) {
                    lose();
                    return;
                }
                soundPool.play(aquaSound, volume, volume, 1, 0, 1f);
                aqua.setImageResource(R.drawable.note_light_aqua);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        aqua.setImageResource(R.drawable.note_aqua);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        black = (ImageView) findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(v.getId())) {
                    lose();
                    return;
                }
                soundPool.play(blackSound, volume, volume, 1, 0, 1f);
                black.setImageResource(R.drawable.note_light_black);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        black.setImageResource(R.drawable.note_black);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        blue = (ImageView) findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.blue)) {
                    lose();
                    return;
                }
                soundPool.play(blueSound, volume, volume, 1, 0, 1f);
                blue.setImageResource(R.drawable.note_light_blue);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blue.setImageResource(R.drawable.note_blue);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        brown = (ImageView) findViewById(R.id.brown);
        brown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.brown)) {
                    lose();
                    return;
                }
                soundPool.play(brownSound, volume, volume, 1, 0, 1f);
                brown.setImageResource(R.drawable.note_light_brown);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        brown.setImageResource(R.drawable.note_brown);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        gray = (ImageView) findViewById(R.id.gray);
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.gray)) {
                    lose();
                    return;
                }
                soundPool.play(graySound, volume, volume, 1, 0, 1f);
                gray.setImageResource(R.drawable.note_light_gray);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gray.setImageResource(R.drawable.note_gray);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        green = (ImageView) findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.green)) {
                    lose();
                    return;
                }
                soundPool.play(greenSound, volume, volume, 1, 0, 1f);
                green.setImageResource(R.drawable.note_light_green);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        green.setImageResource(R.drawable.note_green);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        lime = (ImageView) findViewById(R.id.lime);
        lime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.lime)) {
                    lose();
                    return;
                }
                soundPool.play(limeSound, volume, volume, 1, 0, 1f);
                lime.setImageResource(R.drawable.note_light_lime);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lime.setImageResource(R.drawable.note_lime);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        orange = (ImageView) findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.orange)) {
                    lose();
                    return;
                }
                soundPool.play(orangeSound, volume, volume, 1, 0, 1f);
                orange.setImageResource(R.drawable.note_light_orange);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orange.setImageResource(R.drawable.note_orange);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        pink = (ImageView) findViewById(R.id.pink);
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.pink)) {
                    lose();
                    return;
                }
                soundPool.play(pinkSound, volume, volume, 1, 0, 1f);
                pink.setImageResource(R.drawable.note_light_pink);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pink.setImageResource(R.drawable.note_pink);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        purple = (ImageView) findViewById(R.id.purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.purple)) {
                    lose();
                    return;
                }
                soundPool.play(purpleSound, volume, volume, 1, 0, 1f);
                purple.setImageResource(R.drawable.note_light_purple);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        purple.setImageResource(R.drawable.note_purple);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        red = (ImageView) findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.red)) {
                    lose();
                    return;
                }
                soundPool.play(redSound, volume, volume, 1, 0, 1f);
                red.setImageResource(R.drawable.note_light_red);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        red.setImageResource(R.drawable.note_red);
                    }
                }, 5000);

                queueEmpty();
            }
        });
        yellow = (ImageView) findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoser(R.id.yellow)) {
                    lose();
                    return;
                }
                soundPool.play(yellowSound, volume, volume, 1, 0, 1f);
                yellow.setImageResource(R.drawable.note_light_yellow);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yellow.setImageResource(R.drawable.note_yellow);
                    }
                }, 5000);

                queueEmpty();
            }
        });

        setupAudio();
        sound = new int[]{aquaSound, blackSound, blueSound, brownSound, graySound,
                        greenSound, limeSound, orangeSound, pinkSound, purpleSound, redSound,
                        yellowSound};

        views = new HashMap<>();

        //do the mappings for imageView objects
        views.put(0, R.id.aqua);
        views.put(1, R.id.black);
        views.put(2, R.id.blue);
        views.put(3, R.id.brown);
        views.put(4, R.id.gray);
        views.put(5, R.id.green);
        views.put(6, R.id.lime);
        views.put(7, R.id.orange);
        views.put(8, R.id.pink);
        views.put(9, R.id.purple);
        views.put(10, R.id.red);
        views.put(11, R.id.yellow);

        //create 100 random colors
        Random rand = new Random();
        int color = 0;
        for(int i = 0; i < 100; i++){
            color = rand.nextInt(12);
            stack.add(color);
        }



    }

    private boolean isLoser(int id) {
        Log.d("TAG", "Is loser: " + queue.peekFirst());
        if(queue.remove() != id) {
            return true;
        }
        return false;
    }

    private Runnable runnable;

    private int count;

    public void start(){
        gameStatus.setText("COMPUTER TURN");
        count = 1;
        final Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(count <= moves){
                    if(count >= 2){
                        int col = colorQueue.peekLast();
                        Log.d("TAG", "COLOR PEEK " + count + ": " + col);
                        ImageView resView = (ImageView) findViewById(views.get(col));
                        resView.setImageResource(dark[col]);
                    }
                    final int color = stack.remove();
                    Log.d("TAG", "COLOR ADD " + count + ": " + color);
                    queue.add(views.get(color));
                    colorQueue.add(color);
                    final ImageView view = (ImageView) findViewById(views.get(color));
                    view.setImageResource(light[color]);
                    soundPool.play(sound[color], volume, volume, 1, 0, 1f);
                    count++;

                    handler.postDelayed(runnable, 5000);
                } else {
                    int col = colorQueue.peekLast();
                    ImageView resView = (ImageView) findViewById(views.get(col));
                    resView.setImageResource(dark[col]);
                    gameStatus.setText("YOUR TURN");
                }
            }
        };

        handler.postDelayed(runnable, 0);





    }

    private void queueEmpty() {
        if(queue.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    moves++;
                    start();

                }
            }, 7000);
        }
    }

   public void lose(){
       queue.clear();
       colorQueue.clear();
       moves = 3;
       gameStatus.setText("YOU LOSE IDIOT");
   }

    private void setupAudio() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        //Hardware buttons setting to adjust the media sound
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        aquaSound = soundPool.load(this,R.raw.aqua_sound,1);
        blackSound = soundPool.load(this,R.raw.black_sound,1);
        blueSound = soundPool.load(this,R.raw.blue_sound,1);
        brownSound = soundPool.load(this,R.raw.brown_sound,1);
        graySound = soundPool.load(this,R.raw.gray_sound,1);
        greenSound = soundPool.load(this,R.raw.green_sound,1);
        limeSound = soundPool.load(this,R.raw.lime_sound,1);
        orangeSound = soundPool.load(this,R.raw.orange_sound,1);
        pinkSound = soundPool.load(this,R.raw.pink_sound,1);
        purpleSound = soundPool.load(this,R.raw.purple_sound,1);
        redSound = soundPool.load(this,R.raw.red_sound,1);
        yellowSound = soundPool.load(this,R.raw.yellow_sound,1);
    }
}
