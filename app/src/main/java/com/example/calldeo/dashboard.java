package com.example.calldeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.MalformedURLException;
import java.net.URL;

public class dashboard extends AppCompatActivity {
    EditText secretcode;
    Button Join, Send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        secretcode = findViewById(R.id.secretcode);
        Join = findViewById(R.id.Join);
        Send = findViewById(R.id.Send);
        URL server;
        try {
            server = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultoption = new JitsiMeetConferenceOptions.Builder().setWelcomePageEnabled(false)
                    .build();

            JitsiMeet.setDefaultConferenceOptions(defaultoption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretcode.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(dashboard.this, options);
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String share = secretcode.getText().toString();
                intent.putExtra(Intent.EXTRA_TEXT, "Please use this code to join me on Calldeo  " + "\"" + share + "\"");
                startActivity(Intent.createChooser(intent, "Share using"));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}