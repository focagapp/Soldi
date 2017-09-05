package com.focaga.soldi;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);
        val = (TextView) findViewById(R.id.textView);
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        File mov = new File(this.getFilesDir(),"movimenti"+thisMonth+""+thisYear+".txt");
        try {
            FileInputStream fis = new FileInputStream(mov);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            val.setText("Saldo: " + br.readLine());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}
