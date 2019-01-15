package com.example.prince.btotav01;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    int MY_DATA_CHECK_CODE=1000;
    TextToSpeech textToSpeech;

    EditText editText;
    Button button;
    Button bstop;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        bstop = (Button) findViewById(R.id.button2);
        Button btn = (Button) findViewById(R.id.btn);
        mSeekBarPitch=findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed=findViewById(R.id.seek_bar_speed);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (text.length()>0){
                    textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }
                float pitch=(float) mSeekBarPitch.getProgress()/50;
                if (pitch<0.1){
                    pitch=0.1f;
                }
                float speed=(float) mSeekBarSpeed.getProgress()/50;
                if (speed<0.1){
                    speed=0.1f;
                }
                textToSpeech.setPitch(pitch);
                textToSpeech.setSpeechRate(speed);
            }
        });
        bstop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (text.length()>0){
                    textToSpeech.stop();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Clear the EditText
                editText.getText().clear();
            }
        });



        Intent intent=new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, MY_DATA_CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                textToSpeech = new TextToSpeech(this,this);
            }else {
                Intent intent=new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS){
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        }else if (i == TextToSpeech.ERROR){
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }

    }

}