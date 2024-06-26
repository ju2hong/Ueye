package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private Switch switch1;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView1 = findViewById(R.id.textView);
        final TextView textView2 = findViewById(R.id.textView2);
        imageButton1 = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
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

        // ImageButton 클릭 시 해당 TextView의 텍스트 음성 출력
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()) {
                    TextView textView = null;
                    if (v.getId() == R.id.imageButton) {
                        textView = textView1;
                    } else if (v.getId() == R.id.imageButton2) {
                        textView = textView2;
                    }
                    if (textView != null) {
                        String text = textView.getText().toString();
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }
            }
        };

        imageButton1.setOnClickListener(onClickListener);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스위치가 켜져 있는지 확인
                if (switch1.isChecked()) {
                    // imageButton2 위에 있는 textView2의 텍스트 가져오기
                    String text = textView2.getText().toString();
                    // 텍스트를 음성으로 출력
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }

                // 회원가입 액티비티로 이동
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
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
                    // ImageButton 클릭 이벤트 활성화
                    imageButton1.setEnabled(true);
                    imageButton2.setEnabled(true);
                } else {
                    // 스위치가 꺼진 경우
                    textView3.setText("Off");
                    // "Off" 음성 출력
                    textToSpeech.speak("Off", TextToSpeech.QUEUE_FLUSH, null, null);
                    // ImageButton 클릭 이벤트 비활성화하지 않음
                }
            }
        });

        // 초기 상태 설정
        textView3.setText("ON/OFF");
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
