package com.chaplaincy.stlukeapp.DashboardActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


import com.chaplaincy.stlukeapp.DashboardActivities.HomeActivity;
import com.chaplaincy.stlukeapp.DashboardActivities.SingleNovena;
import com.chaplaincy.stlukeapp.R;

import java.io.InputStream;

public class Novena extends AppCompatActivity {
    private String[] novena_name,novena_content;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novena);

        novena_name = new String[]{"Divine Mercy", "St. Ritah", "St.Philomeno ","litany of Mary","Pecious Blood","St.Jude Novena","Seven Sorrows Rosary"," St. Padre Pio of Pietrelcina","Holy Cloak Novena of St. Joseph"};
        String divinemercy = "";
        String rita = "";
        String philomeno = "";
        String litanyofmary = "";
        String precious = "";
        String stjude = "";
        String sevensorrows = "";
        String pio = "";
        String clock = "";

        try {
            InputStream inputStream = getAssets().open("divinemercy.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            divinemercy = new String(buffer);

            InputStream inputStream2 = getAssets().open("strita.txt");
            int size2 = inputStream2.available();
            byte[] buffer2 = new byte[size2];
            inputStream2.read(buffer2);
            rita = new String(buffer2);

            InputStream inputStream3 = getAssets().open("stphilomeno.txt");
            int size3 = inputStream3.available();
            byte[] buffer3 = new byte[size3];
            inputStream3.read(buffer3);
            philomeno = new String(buffer3);

            InputStream inputStream4 = getAssets().open("litanyofmary.txt");
            int size4 = inputStream4.available();
            byte[] buffer4 = new byte[size4];
            inputStream4.read(buffer4);
            litanyofmary = new String(buffer4);

            InputStream inputStream5 = getAssets().open("preciousblood.txt");
            int size5 = inputStream5.available();
            byte[] buffer5 = new byte[size5];
            inputStream5.read(buffer5);
            precious = new String(buffer5);

            InputStream inputStream6 = getAssets().open("stjude.txt");
            int size6 = inputStream6.available();
            byte[] buffer6 = new byte[size6];
            inputStream6.read(buffer6);
            stjude = new String(buffer6);

            InputStream inputStream7 = getAssets().open("sevensorrows.txt");
            int size7 = inputStream7.available();
            byte[] buffer7 = new byte[size7];
            inputStream7.read(buffer7);
            sevensorrows = new String(buffer7);

            InputStream inputStream8 = getAssets().open("pio.txt");
            int size8 = inputStream8.available();
            byte[] buffer8 = new byte[size8];
            inputStream8.read(buffer8);
            pio = new String(buffer8);

            InputStream inputStream9 = getAssets().open("holyclock.txt");
            int size9 = inputStream9.available();
            byte[] buffer9 = new byte[size9];
            inputStream9.read(buffer9);
            clock = new String(buffer9);


        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView list = findViewById(R.id.mynovena);
        EditText search = findViewById(R.id.search);


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(back);
            }
        });



        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,novena_name);
        list.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String finalDivinemercy = divinemercy;
        String finalRita = rita;

        String finalPhilomeno = philomeno;
        String finalLitanyofmary = litanyofmary;

        String finalPrecious = precious;
        String finalStjude = stjude;
        String finalSevensorrows = sevensorrows;
        String finalPio = pio;
        String finalClock = clock;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent open_novena = new Intent(getApplicationContext(), SingleNovena.class);
                open_novena.putExtra("title",novena_name[i]);
//                open_novena.putExtra("content",novena_content[i]);
                if (i==0){
                    open_novena.putExtra("novena_content", finalDivinemercy);
                }else{
                    if(i==1){
                        open_novena.putExtra("novena_content", finalRita);
                    }else{
                        if(i==2){
                            open_novena.putExtra("novena_content", finalPhilomeno);
                        }else{
                            if (i==3) {
                                open_novena.putExtra("novena_content", finalLitanyofmary);
                            }else{
                                if(i==4){
                                    open_novena.putExtra("novena_content", finalPrecious);
                                }else{
                                    if(i==5){
                                        open_novena.putExtra("novena_content", finalStjude);
                                    }else{
                                        if(i==6){
                                            open_novena.putExtra("novena_content", finalSevensorrows);
                                        }else{
                                            if (i==7){
                                                open_novena.putExtra("novena_content", finalPio);
                                            }else{
                                                if (i==8){
                                                    open_novena.putExtra("novena_content", finalClock);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                startActivity(open_novena);
            }
        });
    }
}