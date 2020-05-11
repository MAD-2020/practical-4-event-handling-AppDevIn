package sg.edu.np.WhackAMole;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //TODO: Constant Varibales
    final String TAG = "Whack-a-Mole";
    private static final String[] BUTTON__IDS_TAGS = {"Button Left Clicked!","Button Centre Clicked!","Button Right Clicked!"};
    Button[] mBUTTONS = new Button[3];

    Integer score = 0;
    TextView txt_score;
    Button btnMole;
    int randomLocation;



    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");

        txt_score = findViewById(R.id.txt_score);
        txt_score.setText(String.valueOf(score));

        Button button01 = findViewById(R.id.button_01);
        Button button02 = findViewById(R.id.button_02);
        Button button03 = findViewById(R.id.button_03);

        mBUTTONS [0] = button01;
        mBUTTONS [1] = button02;
        mBUTTONS [2] = button03;

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();


        Log.v(TAG, "Starting GUI!");


    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */

        //Check if hit or miss
        if(btnMole == checkButton){
            score++;
            Log.v(TAG, "Hit, score added!");
        }else{
            score--;
            Log.v(TAG, "Missed, score deducted!");
        }

        Log.v(TAG, BUTTON__IDS_TAGS[randomLocation]);
        txt_score.setText(String.valueOf(score));

        if(score%10 == 0 && score != 0){
            nextLevelQuery();
        }else{
            setNewMole();
        }




    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/

        Log.v(TAG, "Advance option given to user!");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        //Set dialog Characteristic
        builder.setTitle(R.string.dialog_title).setMessage(R.string.dialog_meesage);

        //Set Yes buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });

        //Set No buttons
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
                setNewMole();
            }
        });

        //Create alert Dialog
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private void nextLevel(){
        /* Launch advanced page */

        Intent advanceMole = new Intent(this,Main2Activity.class);
        advanceMole.putExtra("score",score);

        startActivity(advanceMole);

    }

    private void setNewMole()
    {

        Random ran = new Random();
        randomLocation = ran.nextInt(3);

        //Set the mole for the button
        btnMole = mBUTTONS[randomLocation];
        btnMole.setText(R.string.mole);

        //Set OnClickListenter
        btnMole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck(btnMole);
            }
        });

        //Set the empty in the buttons
        for (final Button button :
                mBUTTONS) {

            if(button != btnMole){
                button.setText(R.string.empty);
            }

            //Set OnClickListener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(button);
                }
            });
        }


    }
}