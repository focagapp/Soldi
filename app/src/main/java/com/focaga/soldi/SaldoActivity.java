package com.focaga.soldi;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SaldoActivity extends Activity {
    protected Button bAmount;
    protected EditText etAmount;
    String amountText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        bAmount = (Button) findViewById(R.id.bAmount);
        etAmount = (EditText) findViewById(R.id.saldoAmount);
        bAmount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                amountText = etAmount.getText().toString();
                if(amountText.trim().isEmpty()) {
                    //se il testo è vuoto setto come valore 0.00
                    etAmount.setText("0.00");
                } else {
                    //test se la stringa non è valida

                    try {
                        Double amount = Double.parseDouble(amountText);
                    } catch(Exception e) {
                        //da modificare (Carle)
                        etAmount.setText("0.00");
                    }
                }
                try {
                    //TODO: troncare ai primi due decimali
                    FileOutputStream fos = new FileOutputStream(MainActivity.fileSave);
                    fos.write(etAmount.getText().toString().getBytes());
                    fos.close();
                    Intent mainActivity = new Intent(SaldoActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                } catch (Exception e) {
                    Intent mainActivity = new Intent(SaldoActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                }
            }
        });
    }
}
