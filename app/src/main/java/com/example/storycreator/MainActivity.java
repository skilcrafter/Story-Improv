package com.example.storycreator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    private Words mWords = new Words();
    private TextView mTimer;
    private TextView mTopic;
    private TextView mAddInn;
    private Button NewTopicButton;
    private Button StartButton;
    private static boolean run;
    public int soundFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = (TextView) findViewById(R.id.timer);
        mTopic = (TextView) findViewById(R.id.randomTopic);
        mAddInn = (TextView) findViewById(R.id.randomAddIn);
        NewTopicButton = (Button) findViewById(R.id.newTopicButton);
        StartButton = (Button) findViewById(R.id.startButton);

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartButton.setEnabled(false);
                NewTopicButton.setEnabled(false);
                run = true;
                addInTimer();
                addInWordTimer();
            }
        });

        NewTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNewTopic();
              }
        });
    }


    private void handleNewTopic() {
        String newNoun = mWords.getANoun();
        String newAdjective = mWords.getAnAdjective();
        mTopic.setText(newAdjective + " " + newNoun);
    }


    private void addInTimer() {
        new CountDownTimer(60000, 1000) {
        public void onTick(long millisUntilFinished) {
            mTimer.setText("" + millisUntilFinished / 1000);}

            public void onFinish() {
            run = false;
            mAddInn.setText("");
            mTimer.setText("Done!");
            mTopic.setText("");
            soundFile = R.raw.airhorn;
            playSound();
            StartButton.setEnabled(true);
            NewTopicButton.setEnabled(true);}

    }.start();
    }


    private void addInWordTimer() {
        final Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
          @Override
            public void run() {
                if(run) {
                 TimerMethod();
                }else {
                myTimer.cancel();}}
        }, 20100, 20100);

    }


    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            soundFile = R.raw.ding;
            playSound();
            String newNoun = mWords.getANoun();
            mAddInn.setText(newNoun);
             }
    };

    private void playSound() {
        MediaPlayer player = MediaPlayer.create(this,soundFile );
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Instructions();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Instructions() {

        new AlertDialog.Builder(this)
                .setTitle("Instructions")
                .setMessage(
                        "You have one minute to create a story based on the " +
                                "topic provided. After 20 seconds and 40 seconds " +
                                "you will also be provided with an additional word" +
                                " to add in to the story.")
                .setPositiveButton(R.string.instructions,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                            }
                        })

                .show();
    }
}
