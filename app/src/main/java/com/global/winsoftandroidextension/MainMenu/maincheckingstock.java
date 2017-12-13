package com.global.winsoftandroidextension.MainMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.global.winsoftandroidextension.R;
import com.global.winsoftandroidextension.SQLclass;
import com.global.winsoftandroidextension.generator;
import com.global.winsoftandroidextension.info_maincheckingstock;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.global.winsoftandroidextension.generator.FIRST_COLUMN;
import static com.global.winsoftandroidextension.generator.SECOND_COLUMN;

public class maincheckingstock extends AppCompatActivity {

    ListView listviewholder;
    ProgressBar pbbar;
    SQLclass sqlclass;
    TextView textstock;
    EditText searchbar;
    Button search;
    List<String> itemsales = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincheckingstock);

        listviewholder=(ListView) findViewById(R.id.lvcs);
        pbbar= (ProgressBar) findViewById(R.id.lodingbar);
        searchbar = (EditText) findViewById(R.id.csbox) ;
        search =(Button) findViewById(R.id.btncheck);
        textstock = (TextView) findViewById(R.id.textstock);

        pbbar.setVisibility(View.GONE);

        sqlclass = new SQLclass();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Docheck docheck = new Docheck();
                docheck.execute("");
            }
        });

        loaddata();
        bindclicklistener();

    }

    public class Docheck extends AsyncTask<String, String, String> {
        String z = "Insert Data";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(maincheckingstock.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                try {
                    generator.lvcheckstock adapter = new generator.lvcheckstock(maincheckingstock.this, generator.list);
                    listviewholder.setAdapter(adapter);
                    listviewholder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                            int pos = position + 1;
                            Intent b = new Intent(maincheckingstock.this , info_maincheckingstock.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("nama_stock",searchbar.getText().toString());
                            b.putExtras(bundle);
                            startActivity(b);
                        }

                    });
                }catch (Exception e){

                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            Boolean data=false;
            NumberFormat formatter = new DecimalFormat("###,###,###.##");
            generator.list.clear();
            try {
                int count=0;
                ResultSet stock = sqlclass.querydata("select kode_stock from iamstock where nama_stock='"+searchbar.getText().toString()+"'");
                while (stock.next()){
                ResultSet result = sqlclass.querydata("SELECT LOKASI, DBO.GETQTYAWALBYTANGGALLOKASI('"+stock.getString("kode_stock")+"',GETDATE(),NULL,KODE_LOKASI) AS SALDO, DBO.GETQTYSATUANALL('', DBO.GETQTYAWALBYTANGGALLOKASI('"+stock.getString("kode_stock")+"',GETDATE(),NULL,KODE_LOKASI), '' ) AS SALDO_AWAL FROM IAMLOKASI");
                    while (result.next()){
                        HashMap<String, String> datanum = new HashMap<String, String>();
                        if(result.getInt("saldo")!=0) {
                            count++;
                            datanum.put(FIRST_COLUMN, result.getString("Lokasi"));
                            datanum.put(SECOND_COLUMN, String.valueOf(formatter.format(result.getInt("saldo"))));
                            data=true;
                            generator.list.add(datanum);
                        }
                    }
                }
                if(data=true){
                    isSuccess=true;
                    z=String.valueOf(count)+" Data Retrived";
                }else{
                    z="No data";
                }
            }
            catch (Exception e) {
                Log.e("ERROR ", e.toString());
            }
            return z;
        }
    }

    public void loaddata(){
        try{
            ResultSet result = sqlclass.querydata("select nama_stock from iamstock where tdk_aktif=0 order by nama_stock asc");
            itemsales.add("Hapus Text");
            while(result.next()){
                itemsales.add(result.getString("nama_stock"));
            }
            itemsales.add("Hapus Text");
        }
        catch (Exception e){
            Log.e("error",e.toString() );
        }
    }
    public void bindclicklistener(){

        textstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchbar.getText().toString().equals("")){}
                else{
                Intent b = new Intent(maincheckingstock.this , info_maincheckingstock.class);
                Bundle bundle = new Bundle();
                bundle.putString("nama_stock",searchbar.getText().toString());
                b.putExtras(bundle);
                startActivity(b);}
            }
        });

        searchbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(maincheckingstock.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View custView = inflater.inflate(R.layout.dialog_searchandfilter, null);
                ListView temp=null;
                temp = (ListView) custView.findViewById(R.id.lvsnf);
                EditText editbox = (EditText) custView.findViewById(R.id.EditBox);

                builderSingle.setTitle("Select One Name:");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(maincheckingstock.this,
                        android.R.layout.simple_list_item_single_choice,
                        itemsales );
                temp.setAdapter(arrayAdapter);

                TextWatcher filterTextWatcher = new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        arrayAdapter.getFilter().filter(s);
                    }
                };
                editbox.addTextChangedListener(filterTextWatcher);

                temp.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3)
                    {
                        if(searchbar.getText().toString().trim().equals("")){
                            String value = (String)adapter.getItemAtPosition(position);
                        /*    try{
                                ResultSet temps = sqlclass.querydata("select kode_customer from iamcustomer where nama_customer='"+value+"'order by nama_customer asc");
                                while(temps.next()){
                                    value= temps.getString("kode_customer");
                                }}
                            catch (Exception e){
                                Log.e("error",e.getMessage());
                            }*/
                            if(value.equals("Hapus Text")){
                                searchbar.setText("");
                                textstock.setText("");
                            }
                            else {
                                searchbar.setText(value);
                                textstock.setText(value);
                            }
                        }
                        else{
                            String value = (String)adapter.getItemAtPosition(position);
                           /* try{
                                ResultSet temps = sqlclass.querydata("select kode_customer from iamcustomer where nama_customer='"+value+"'order by nama_customer asc");
                                while(temps.next()){
                                    value= temps.getString("kode_customer");
                                }}
                            catch (Exception e){
                                Log.e("error",e.getMessage());
                            }*/
                            if(value.equals("Hapus Text")){
                                searchbar.setText("");
                                textstock.setText("");
                            }else{
                                searchbar.setText(value);
                                textstock.setText(value);
                            }
                        }

                    }
                });

                builderSingle.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setView(custView);
                builderSingle.show();
            }
        });

    }
}
