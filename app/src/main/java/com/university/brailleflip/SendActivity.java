package com.university.brailleflip;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.ArraySet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.university.brailleflip.thread.ConnectedMsgThread;
import com.university.brailleflip.thread.ThreadManager;
import com.university.brailleflip.utils.RecognitionUtils;

import java.util.Locale;

public class SendActivity extends AppCompatActivity {

    private ArraySet<Integer> integers;
    String mark="markHide";
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String s = integers.toString();
          //  Toast.makeText(SendActivity.this, "Identify"+s, Toast.LENGTH_SHORT).show();
            String recognition = RecognitionUtils.recognition(integers);
            integers.clear();
            if (recognition.equals("Capital")){
             //The capital button light is on.
            capital.setVisibility(View.VISIBLE);
            speak("capital");
                return;
            }else {
                //The capital button light is off.
                capital.setVisibility(View.INVISIBLE);
            }
            if (recognition.equals("Number")){
                //The digit button light is on.
                number.setVisibility(View.VISIBLE);
                speak("number");
                return;
            }else {
                //The digit button light is off.
                number.setVisibility(View.INVISIBLE);
            }
            textView.append(recognition);
            speak(recognition);
            //send data
            send(recognition);
        }
    };
    private TextView textView;
    private TextToSpeech textToSpeech;
    private View capital;
    private View number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        setContentView(R.layout.activity_send);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        integers = new ArraySet<>();

        initView();
        initTTS();
    }

    private void send(String content){
        ConnectedMsgThread clientConnected = ThreadManager.getInstance().getClientConnected();
        if (clientConnected!=null){
            clientConnected.write(content.getBytes());
        }else {
            ConnectedMsgThread serviceConnected = ThreadManager.getInstance().getServiceConnected();
            if (serviceConnected!=null) {
                serviceConnected.write(content.getBytes());
            }else {
                Toast.makeText(SendActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initTTS() {
        // 参数Context,TextToSpeech.OnInitListener
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(SendActivity.this, "Data loss or not supported", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void speak(String strContent){
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            textToSpeech.setPitch(0.0f);
            textToSpeech.speak(strContent,
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.tv_input);
        capital = (View) findViewById(R.id.capital);
        number = (View) findViewById(R.id.number);


        findViewById(R.id.btn_1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (check(motionEvent)) {
                        Log.e("tap", "1");
                        integers.add(1);
                        handler.removeMessages(100);
                        handler.sendEmptyMessageDelayed(100, 1000);
                    }
                return false;
            }
        });
        findViewById(R.id.btn_4).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (check(motionEvent)) {
                    Log.e("tap", "4");
                    integers.add(4);
                    handler.removeMessages(100);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                return false;
            }
        });
        findViewById(R.id.btn_2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (check(motionEvent)) {
                    Log.e("tap", "2");
                    check(motionEvent);
                    integers.add(2);
                    handler.removeMessages(100);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                return false;
            }
        });
        findViewById(R.id.btn_5).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (check(motionEvent)) {
                    Log.e("tap", "5");
                    integers.add(5);
                    handler.removeMessages(100);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                return false;
            }
        });
        findViewById(R.id.btn_3).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (check(motionEvent)) {
                    Log.e("tap", "3");
                    integers.add(3);
                    handler.removeMessages(100);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                return false;
            }
        });
        findViewById(R.id.btn_6).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (check(motionEvent)) {
                    Log.e("tap", "6");
                    integers.add(6);
                    handler.removeMessages(100);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                return false;
            }
        });
        findViewById(R.id.btn_space).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mark.equals("markShow")) {
                    speak("exit edit mode first");
                    Toast.makeText(SendActivity.this, "exit edit mode first", Toast.LENGTH_SHORT).show();
                    return;
                }
                    textView.append(" ");
                    speak("space");
                    send("space");


            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mark.equals("markShow")) {
                    speak("exit edit mode first");
                    Toast.makeText(SendActivity.this, "exit edit mode first", Toast.LENGTH_SHORT).show();
                    return;
                }
                speak("delete");
                String s = textView.getText().toString();
                if (s.length()>0) {
                    String substring = s.substring(0, s.length() - 1);
                    textView.setText(substring);
                }
                send("delete");
            }
        });



        //Notify the other end to turn on the mask or turn off the mask
        findViewById(R.id.btn_edit).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mark.equals("markHide")){
                    speak("edit mode on");
                    send("markShow");
                    mark="markShow";
                }else {
                    speak("edit mode off");
                    send("markHide");
                    mark="markHide";
                }


                return true;
            }
        });



    }

    private boolean check(MotionEvent motionEvent){
        if (mark.equals("markShow")){
            if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                speak("exit edit mode first");
                Toast.makeText(SendActivity.this, "exit edit mode first", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // Whether you are reading TTS or not, it is interrupted.
        textToSpeech.shutdown(); // Close and release resources
    }

}
