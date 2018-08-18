package com.example.android.medic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText loginPassword;
    private EditText loginEmail;
    private Button buttonLogin;
    TextView registerLink;
    private static final String LOGIN_URL = "http://walke.comli.com/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        registerLink=(TextView) findViewById(R.id.registerLink);

        buttonLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonLogin:
                loginUser();
                break;

            case R.id.registerLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    private void loginUser() {
        String email = loginEmail.getText().toString().trim().toLowerCase();
        String password = loginPassword.getText().toString();
        login(email, password);
    }
    private void login(String email, String password) {
        String urlSuffix = "?email="+email+"&password="+password;
        class LoginUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                if(s.equals("Welcome!")) {
                    startActivity(new Intent(Login.this, Order.class));
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(LOGIN_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result = bufferedReader.readLine();
                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        LoginUser ru = new LoginUser();
        ru.execute(urlSuffix);
    }
}
