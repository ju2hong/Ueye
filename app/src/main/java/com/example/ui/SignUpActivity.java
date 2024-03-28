package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private Switch switch1;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextView textView4 = findViewById(R.id.textView4);
        switch1 = findViewById(R.id.switch1);
        textView3 = findViewById(R.id.textView3);

        // TextToSpeech 초기화
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        // 스위치 상태 변화 감지
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 스위치가 켜진 경우
                    textView3.setText("On");
                    // "On" 음성 출력
                    textToSpeech.speak("On", TextToSpeech.QUEUE_FLUSH, null, null);

                } else {
                    // 스위치가 꺼진 경우
                    textView3.setText("Off");
                    // "Off" 음성 출력
                    textToSpeech.speak("Off", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
