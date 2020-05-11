package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    final String TAG = "Whack-A-Mole-2.0";
    int advancedScore;
    Button btnMole;
    TextView txt_score;

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
                Toast.makeText(getApplicationContext(), String.valueOf(millisUntilFinished / 1000), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();

                placeMoleTimer();
            }
        }.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                Log.i(TAG, "New Mole Location");
                setNewMole();
            }
        };

        handler.postDelayed(runnable,1000);


    }
    private static final int[] BUTTON_IDS = {
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Get the score
        advancedScore =  getIntent().getExtras().getInt("score");
        txt_score = findViewById(R.id.textScore);

        txt_score.setText(String.valueOf(advancedScore));
        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));


        readyTimer();



        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */

           final Button button = findViewById(id);

            //Set OnClickListener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(button);
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */


        //Check if hit or miss
        if(btnMole == checkButton){
            advancedScore++;
            Log.v(TAG, "Hit, score added!");
        }else{
            advancedScore--;
            Log.v(TAG, "Missed, score deducted!");
        }

        txt_score.setText(String.valueOf(advancedScore));


    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);


        //Set the mole for the button
        btnMole = findViewById(BUTTON_IDS[randomLocation]);
        btnMole.setText(R.string.mole);
        

        //Set the empty in the buttons
        for(final int id : BUTTON_IDS){
            
            Button button = findViewById(id);
            if(button != btnMole){
                button.setText(R.string.empty);
            }
            
        }
    }
}

