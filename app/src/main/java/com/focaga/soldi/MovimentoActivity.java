package com.focaga.soldi;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

public class MovimentoActivity extends Activity {
    Button procedi;
    EditText valore;
    String val;
    Double amount;
    float amountFloat;
    RadioButton togli;
    public static File mov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimento);

        procedi = (Button) findViewById(R.id.procedi);
        valore = (EditText) findViewById(R.id.valore);
        togli = (RadioButton) findViewById(R.id.delete);
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        mov = new File(this.getFilesDir(),"movimenti"+thisMonth+""+thisYear+".txt");



        procedi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                val = valore.getText().toString();
                if(val.trim().isEmpty()) {
                    //se il testo è vuoto setto come valore 0.00
                    valore.setText("0.00");
                } else {
                    //test se la stringa non è valida

                    try {
                        amount = Double.parseDouble(val);
                    } catch(Exception e) {
                        //da modificare (Carle)
                        valore.setText("0.00");
                    }
                    amountFloat = amount.floatValue();



                    if (togli.isChecked())
                        amountFloat=amountFloat*-1;
                    try {
                        //scrivo il movimento nel file movimentoMMYY
                        FileOutputStream fos = new FileOutputStream(mov,true);
                        String s = amountFloat+"";
                        fos.write(s.getBytes());
                        fos.close();
                        //cambio file e passo a saldo, per aggiornarlo
                        mov = new File(MovimentoActivity.this.getFilesDir(),"saldo.txt");
                        FileInputStream fis = new FileInputStream(mov);
                        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                        BufferedReader br = new BufferedReader(isr);
                        Double sal = Double.parseDouble(br.readLine());
                        fis.close();

                        float saldo = sal.floatValue()+amountFloat;
                        //aggiorno
                        fos = new FileOutputStream(mov);
                        s = saldo+"";
                        fos.write(s.getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(amountFloat);
                    //ritorno alla pagina principale
                    Intent mainActivity = new Intent(MovimentoActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                }
            }


            //TODO aggiungere rinvio a pagina iniziale e salvataggio su file

        });
    }
}
