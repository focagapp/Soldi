package com.focaga.soldi;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;

public class StoricoActivity extends Activity {
    TextView val;
    TextView title;
    Button precedente, successivo;
    int thisYear, thisMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);
        val = (TextView) findViewById(R.id.textView);
        title = (TextView) findViewById(R.id.movTitle);
        precedente = (Button) findViewById(R.id.precedente);
        successivo = (Button) findViewById(R.id.successivo);
        //prendo mese e anno per visualizzare il file corretto
        Calendar calendar = Calendar.getInstance();
        thisYear = calendar.get(Calendar.YEAR);
        thisMonth = calendar.get(Calendar.MONTH);
        thisMonth ++;
        estraiMovimenti(thisMonth,thisYear);
        //TODO: mettere i totali di entrate e uscite del mese


        precedente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (precedente.getText().equals("Dicembre")){
                    thisMonth=12;
                    thisYear --;
                } else {
                    thisMonth --;
                }
                estraiMovimenti(thisMonth,thisYear);
            }
        });

        successivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (successivo.getText().equals("Gennaio")){
                    thisMonth=1;
                    thisYear ++;
                } else {
                    thisMonth ++;
                }
                estraiMovimenti(thisMonth,thisYear);
            }
        });

    }

    public String risolviMese (int mese){
        switch (mese) {
            case 0: return "Dicembre";
            case 1: return "Gennaio";
            case 2: return "Febbraio";
            case 3: return "Marzo";
            case 4: return "Aprile";
            case 5: return "Maggio";
            case 6: return "Giugno";
            case 7: return "Luglio";
            case 8: return "Agosto";
            case 9: return "Settembre";
            case 10: return "Ottobre";
            case 11: return "Novembre";
            case 12: return "Dicembre";
            case 13: return "Gennaio";
        }
        return "";
    }

    public void estraiMovimenti(int mese, int anno){
        title.setText(risolviMese(thisMonth)+" "+thisYear);
        precedente.setText(risolviMese(thisMonth-1));
        successivo.setText(risolviMese(thisMonth+1));
        File mov = new File(this.getFilesDir(),"movimenti"+mese+""+anno+".txt");
        if (!mov.exists()) {
            val.setText("Non ci sono movimenti per questo mese!");
        }
        String movimenti = "";
        try {
            FileInputStream fis = new FileInputStream(mov);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String s = br.readLine();
            while (s != null) {
                //se è un numero positivo aggiungo un + alla stringa, per visualizzare meglio
                if (!(s.substring(0,1).equalsIgnoreCase("-")))
                    movimenti += "\n+" + s;
                else
                    movimenti += "\n" + s;
                s = br.readLine();

            }
            val.setText("Movimenti: " + movimenti);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
