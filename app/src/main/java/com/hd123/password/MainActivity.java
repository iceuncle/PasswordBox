package com.hd123.password;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iceuncle.passwordbox.PasswordBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PasswordBox passwordBox = (PasswordBox) findViewById(R.id.password_view);
        passwordBox.setInputCompleteListener(new PasswordBox.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Log.d("qqq", "密码：  " + passwordBox.getStrPassword());
            }
        });
    }

}
