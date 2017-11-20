package com.hd123.password;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iceuncle.passwordbox.PasswordBox;

public class MainActivity extends AppCompatActivity {
    private PasswordBox mPasswordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPasswordBox = (PasswordBox) findViewById(R.id.password_view);
        mPasswordBox.setInputCompleteListener(new PasswordBox.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Toast.makeText(MainActivity.this, mPasswordBox.getStrPassword(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Clear(View view) {
        mPasswordBox.clear();
    }
}
