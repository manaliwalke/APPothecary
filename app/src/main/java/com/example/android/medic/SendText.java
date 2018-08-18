package com.example.android.medic;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendText extends AppCompatActivity implements View.OnClickListener {
    private EditText editOrderName;
    private EditText editOrderQuantity;
    private Button buttonSendOrder;

    private static final String ORDER_URL = "http://walke.comli.com/textorder.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);
        editOrderName = (EditText) findViewById(R.id.editOrderName);
        editOrderQuantity = (EditText) findViewById(R.id.editOrderQuantity);
        buttonSendOrder = (Button) findViewById(R.id.buttonSendOrder);

        buttonSendOrder.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == buttonSendOrder){
            sendOrder();
        }
    }
    private void sendOrder() {
        String orderName = editOrderName.getText().toString();
        String quantity = editOrderQuantity.getText().toString();
        send(orderName, quantity);
    }
    private void send(String orderName, String quantity) {
        String urlSuffix = "?orderName="+orderName+"&quantity="+quantity;
        class SendOrder extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SendText.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(ORDER_URL+s);
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

        SendOrder so = new SendOrder();
        so.execute(urlSuffix);
    }
}
