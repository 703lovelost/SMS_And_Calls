# # Лабораторная №11 - Работа с телефонией

В лабораторной реализована возможность звонков и набора сообщений.
При первом пользовании запрашивается разрешение для устройства.
К сожалению, на моем устройстве нет возможности проверить доставку сообщений и звонки - нет сим-карты для этого.
Тем не менее, был проверен доступ к этим функциям - все работает.

Метод запроса на отправку СМС и звонки:

```java
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
```

Метод отправки СМС:

```java
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
```

Метод звонка:


```java
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
```
    

Apk-файл включен.
