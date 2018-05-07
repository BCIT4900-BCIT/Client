package dl2kten.com.s_timer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CountDOWN extends AppCompatActivity {

    private TextView timeTextView;
    private Button startBtn;
    private Button completeBtn;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    private boolean runTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down2);

        //Initialize components
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        startBtn = (Button) findViewById(R.id.startBtn);
        completeBtn = (Button) findViewById(R.id.completeBtn);

        Intent in = getIntent();
        int index = in.getIntExtra("dl2kten.com.s_timer.task",
                -1);
        timeLeft = 0;
        runTimer = true;

        if(index > -1 && index < 4) {
            int[][] times = getTime(index);
            timeLeft += times[0][0] * 60;
            timeLeft += times[0][1];
            timeLeft *= 1000;
        }

        updateTimer();

        //Button listeners
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timeLeft >= 0) {
                    //pass whether task was completed or not on to rewards class
                    Intent finished = new Intent(getApplicationContext(), Rewards.class);
                    startActivity(finished);
                } else {
                    Intent finished = new Intent(getApplicationContext(), TryAgain.class);
                    startActivity(finished);
                }
            }
        });
    }

    /**
     * Runs timer
     */
    private void startTimer() {

        if(runTimer) {
            countDownTimer = new CountDownTimer(timeLeft, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    //If timer runs out then did not finish on time
                    Intent finished = new Intent(getApplicationContext(), TryAgain.class);
                    startActivity(finished);
                }
            }.start();

            startBtn.setText("Pause");
            runTimer = false;
        } else {
            countDownTimer.cancel();
            startBtn.setText("Resume");
            runTimer = true;
        }

    }

    /**
     * Updates textview with count down
     */
    private void updateTimer() {
        int minutes = (int) timeLeft / 60000;
        int mod = (int) timeLeft % 60000;
        int seconds = mod / 1000;
        mod = mod % 1000;
        int ms = mod / 100;

        String timeLeftText;

        timeLeftText = "" + "0:";
        //adds 0 in front if single digit
        if(minutes < 10)
            timeLeftText += "0";

        timeLeftText += minutes + ":";

        if(seconds < 10)
            timeLeftText += "0";

        timeLeftText += seconds + "." + ms;

        timeTextView.setText(timeLeftText);
    }

    /**
     * Get the specific times of chosen task
     * @param index
     * @return
     */
    private int[][] getTime(int index) {
        int[][] times= new int[1][2];
        String[] numMins = getResources().getStringArray(R.array.minutes);
        String[] numSecs = getResources().getStringArray(R.array.seconds);

        switch(index) {
            case 0:
                times[0][0] = Integer.parseInt(numMins[0]);
                times[0][1] = Integer.parseInt(numSecs[0]);
                break;
            case 1:
                times[0][0] = Integer.parseInt(numMins[1]);
                times[0][1] = Integer.parseInt(numSecs[1]);
                break;
            case 2:
                times[0][0] = Integer.parseInt(numMins[2]);
                times[0][1] = Integer.parseInt(numSecs[2]);
                break;
            case 3:
                times[0][0] = Integer.parseInt(numMins[3]);
                times[0][1] = Integer.parseInt(numSecs[3]);
                break;
            default:
                System.out.println("Huh?");
                break;
        }

        return times;
    }
}
