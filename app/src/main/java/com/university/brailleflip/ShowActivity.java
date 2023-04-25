package com.university.brailleflip;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.university.brailleflip.thread.ThreadManager;
import com.university.brailleflip.ui.GestureUI;
import com.university.brailleflip.utils.Utils;

import java.util.Locale;

public class ShowActivity extends AppCompatActivity {

    private EditText mText;
    Button mNextLine;
    Button mClearAll,mReadAll;
    private TextToSpeech textToSpeech;
    private GestureUI mMark;
    private TextView tv_copy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        initView();
        initTTS();
    }

    private void initView() {
        mText = (EditText) findViewById(R.id.edit_text);
        tv_copy = (TextView) findViewById(R.id.tv_copy);
        mNextLine = (Button) findViewById(R.id.next_line_button);
        mClearAll = (Button) findViewById(R.id.clear_all_button);
        mReadAll = (Button) findViewById(R.id.read_all_button);
        mMark = (GestureUI) findViewById(R.id.mark);
        TextView mStatus = (TextView) findViewById(R.id.status_button);
        mText.setOnTouchListener((v, event) -> true);
        if (ThreadManager.connected){
            mStatus.setText("Status: \n Connected");
        }else {
            mStatus.setText("Status: \n Disconnected");
        }
        mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStatus.getText().toString().contains("Disconnected")) {
                    speak("Status Disconnected");
                }else {
                    speak("Status Connected");
                }
            }
        });


        mText.requestFocus();//get Cursor

        mNextLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectionStart = mText.getSelectionStart();
                StringBuffer stringBuffer = new StringBuffer(mText.getText().toString());
                stringBuffer.insert(selectionStart,"\n");
                mText.setText(stringBuffer);
                mText.setSelection(selectionStart+1);
                speak("Next Line");
            }
        });
        mClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText.setText("");
                speak("Clear All");
            }
        });
        mReadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mText.getText().toString();
                //  Toast.makeText(ShowActivity.this, "Read", Toast.LENGTH_SHORT).show();
                speak(s);

            }
        });
        findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Settings");
                finish();
            }
        });
        findViewById(R.id.copy_all_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Copy All");
                String s = mText.getText().toString();
                Utils.copyToClipboard(ShowActivity.this,s);
            }
        });


        //Monitor the gesture operation
        mMark.setOperate(new GestureUI.Operate() {
            String copyStr;
            @Override
            public void cursorLeft() {
                Log.e("action","one finger swipe right");
                int selectionStart = mText.getSelectionStart();
                if (selectionStart>0) {
                    mText.setSelection(selectionStart-1);//get cursor

                    //speak
                    String substring = mText.getText().toString().substring(selectionStart-1, selectionStart);
                    Log.e("speak",substring);
                    if (TextUtils.isEmpty(substring)||substring.equals(" ")){
                        speak("space");
                    }else {
                        speak(substring);
                    }
                }
            }

            @Override
            public void cursorRight() {
                Log.e("action","one finger swipe right");
                int selectionStart = mText.getSelectionStart();
                if (selectionStart<mText.getText().length()) {
                    mText.setSelection(selectionStart+1);//get cursor
                    //speak
                    String substring = mText.getText().toString().substring(selectionStart, selectionStart+1);
                    Log.e("speak",substring);

                    if (TextUtils.isEmpty(substring)||substring.equals(" ")){
                        speak("space");
                    }else {
                        speak(substring);
                    }
                }
            }

            @Override
            public void selectLeft() {
                Log.e("action","selectLeft");
                int selectionStart = mText.getSelectionStart();
                int selectionEnd = mText.getSelectionEnd();
                if (selectionStart>0) {
                    mText.setSelection(selectionStart-1, selectionEnd);

                    String substring = mText.getText().toString().substring(selectionStart-1, selectionStart);
                    Log.e("speak",substring);

                    if (TextUtils.isEmpty(substring)||substring.equals(" ")){
                        speak("space");
                    }else {
                        speak(substring);
                    }
                }
            }

            @Override
            public void selectRight() {
                Log.e("action","selectRight");
//                int selectionStart = mText.getSelectionStart();
//                int selectionEnd = mText.getSelectionEnd();
//                if (selectionEnd<mText.getText().length()) {
//                    mText.setSelection(selectionStart, selectionEnd+1);
//                }
                int selectionStart = mText.getSelectionStart();
                int selectionEnd = mText.getSelectionEnd();
                if (selectionStart<mText.getText().length()) {
                    mText.setSelection(selectionStart+1, selectionEnd);

                    String substring = mText.getText().toString().substring(selectionStart, selectionStart+1);
                    Log.e("speak",substring);

                    if (TextUtils.isEmpty(substring)||substring.equals(" ")){
                        speak("space");
                    }else {
                        speak(substring);
                    }
                }

            }

            @Override
            public void copy() {
                Log.e("action","copy");
                speak("copy");
                int selectionStart = mText.getSelectionStart();
                int selectionEnd = mText.getSelectionEnd();
                String strText = mText.getText().toString();
                if (selectionStart<=selectionEnd){
                    copyStr = strText.substring(selectionStart, selectionEnd);
                }else {
                    copyStr = strText.substring(selectionEnd, selectionStart);
                }


                tv_copy.setText(copyStr);
            }

            @Override
            public void paste() {
                Log.e("action","paste");
                speak("paste");
                int selectionStart = mText.getSelectionStart();//get cursor
                if (!TextUtils.isEmpty(copyStr)){
                    String s = mText.getText().toString();
                    StringBuffer stringBuffer = new StringBuffer(s);
                    stringBuffer.insert(selectionStart,copyStr);
                    mText.setText(stringBuffer);
                    mText.setSelection(selectionStart);
                }
            }

            @Override
            public void beginning() {
                Log.e("action","beginning of text");
                speak("beginning of text");
                mText.setSelection(0);//get cursor

            }

            @Override
            public void end() {
                Log.e("action","end of text");
                speak("end of text");
                mText.setSelection(mText.getText().length());//get cursor
            }
        });
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
                        Toast.makeText(ShowActivity.this, "Data loss or not supported", Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onResume() {
        super.onResume();

        ThreadManager.getInstance().setCoordinateData(new ThreadManager.CoordinateData() {
            @Override
            public void data(String str) {
                if (str.equals("markShow")){
                    mMark.setVisibility(View.VISIBLE);
                    return;
                }
                if (str.equals("markHide")){
                    mMark.setVisibility(View.GONE);
                    return;
                }
                //press space
                if (str.equals("space")){
                    int selectionStart = mText.getSelectionStart();
                    StringBuffer stringBuffer = new StringBuffer(mText.getText().toString());
                    stringBuffer.insert(selectionStart," ");
                    mText.setText(stringBuffer);
                    mText.setSelection(selectionStart+1);
                    return;
                }
                //press delete
                if (str.equals("delete")){
                    String s = mText.getText().toString();
                    if (s.length()>0) {
                        int selectionStart = mText.getSelectionStart();
                        StringBuffer stringBuffer = new StringBuffer(mText.getText().toString());

                        stringBuffer.delete(selectionStart-1,selectionStart);

                        mText.setText(stringBuffer);
                        mText.setSelection(selectionStart-1);
                    }
                    return;
                }

                int selectionStart = mText.getSelectionStart();
                StringBuffer stringBuffer = new StringBuffer(mText.getText().toString());
                stringBuffer.insert(selectionStart,str);



                mText.setText(stringBuffer);
                mText.setSelection(selectionStart+1);

                //  speak(str);
            }
        });
    }
}
