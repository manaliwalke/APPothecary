package com.example.android.medic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Order extends AppCompatActivity implements View.OnClickListener {
    Button SendText;
    Button SendPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        SendText = (Button)findViewById(R.id.buttonSendText);
        SendPicture = (Button)findViewById(R.id.buttonSendPicture);
        SendText.setOnClickListener(this);
        SendPicture.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonSendText:
                startActivity(new Intent(this, SendText.class));
                break;

            case R.id.buttonSendPicture:
                startActivity(new Intent(this, SendPicture.class));
                break;
        }
    }
}
