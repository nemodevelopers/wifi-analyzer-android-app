package ru.nemodev.wifi.analyzer.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.nemodev.wifi.analyzer.ui.auth.LoginActivity;
import ru.nemodev.wifi.analyzer.ui.auth.AuthContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AuthContext.isUserAuthComplete()) {
            Intent intent = new Intent(this, AppActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
