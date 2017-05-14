package com.hd123.password;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iceuncle.passwordbox.PasswordView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PasswordView passwordView = (PasswordView) findViewById(R.id.password_view);
        passwordView.setInputCompleteListener(new PasswordView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Log.d("qqq", "密码：  " + passwordView.getStrPassword());
            }
        });
    }

}
