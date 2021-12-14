package com.example.smsandcalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mDialButton = (Button) findViewById(R.id.buttonCall);
        Button sendSMS = (Button) findViewById(R.id.buttonSendSMS);


        mDialButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Not allowed!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
            else {
                callPhone();
            }
        });

        sendSMS.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Not allowed!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
            }
            else {
                sendSMS();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) { // requestCode for saving a text file.

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Let's call!", Toast.LENGTH_SHORT).show();
                callPhone();
            }
            else {
                Toast.makeText(MainActivity.this, "We've got no permission to call.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2) { // requestCode for uploading a text file.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Let's send SMS!", Toast.LENGTH_SHORT).show();
                sendSMS();
            }
            else {
                Toast.makeText(MainActivity.this, "We've got no permission to send SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callPhone() {
        final EditText mPhoneNoEt = (EditText) findViewById(R.id.etPhone);

        String phoneNo = mPhoneNoEt.getText().toString();
        if(!TextUtils.isEmpty(phoneNo))
        {
            String dial = "tel:" + phoneNo;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
        else {
            Toast.makeText(MainActivity.this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS() {
        EditText edit_Number = (EditText) findViewById(R.id.etPhone);
        String phoneNo = edit_Number.getText().toString();
        EditText sms_edit = (EditText) findViewById(R.id.etSMS);
        String toSms = "smsto:" + edit_Number.getText().toString();
        String messageText = sms_edit.getText().toString();
        Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));

        sms.putExtra("sms_body", messageText);
        startActivity(sms);
        SmsManager.getDefault().sendTextMessage(phoneNo, null, messageText.toString(), null, null);
    }
}