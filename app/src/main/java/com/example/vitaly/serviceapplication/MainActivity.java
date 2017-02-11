package com.example.vitaly.serviceapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService myService;
    private boolean isBinded;
    private TextView tvOut;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBinded = true;
            MyService.MyBinder binder = (MyService.MyBinder) service;
            myService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tvOut = (TextView) findViewById(R.id.tvOutActivityMain);
        findViewById(R.id.btnGetTimeActivityMain).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,MyService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        MyIntentService.startActionBaz(this,"asd","dasdsa");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGetTimeActivityMain:
                if (isBinded)
                    tvOut.setText(myService.getTime());
                else
                    tvOut.setText("Disconnected");
                break;
        }
    }
}
