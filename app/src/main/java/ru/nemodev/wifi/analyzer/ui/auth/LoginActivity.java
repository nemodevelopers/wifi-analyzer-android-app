package ru.nemodev.wifi.analyzer.ui.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.network.RetrofitApiFactory;
import ru.nemodev.wifi.analyzer.core.network.api.oauth.OAuthApiFactory;
import ru.nemodev.wifi.analyzer.core.network.dto.oauth.OAuthTokenDto;
import ru.nemodev.wifi.analyzer.ui.AppActivity;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_input) EditText loginInput;
    @BindView(R.id.pass_input) EditText passInput;
    @BindView(R.id.btn_login) Button loginBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginBtn.setOnClickListener(v -> login());
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Аутентификация...");
        progressDialog.show();

        String login = loginInput.getText().toString();
        String password = passInput.getText().toString();

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", login);
        queryParams.put("password", password);
        queryParams.put("grant_type", "password");
        queryParams.put("scope", "read write");

        OAuthApiFactory oAuthApiFactory = new OAuthApiFactory("mobile", "1234");
        oAuthApiFactory.createApi().getToken(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OAuthTokenDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OAuthTokenDto oAuthTokenDto) {
                        Log.d("123", oAuthTokenDto.getAccess_token());
                        RetrofitApiFactory.tokenDto = oAuthTokenDto;
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("123", e.getMessage());
                        onLoginFailed();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginBtn.setEnabled(true);

        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Ошибка", Toast.LENGTH_LONG).show();

        loginBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String login = loginInput.getText().toString();
        String password = passInput.getText().toString();

        if (login.isEmpty()) {
            loginInput.setError("Введите корректный логин");
            valid = false;
        } else {
            loginInput.setError(null);
        }

        if (password.isEmpty()) {
            passInput.setError("Введите корректный пароль");
            valid = false;
        } else {
            passInput.setError(null);
        }

        return valid;
    }
}