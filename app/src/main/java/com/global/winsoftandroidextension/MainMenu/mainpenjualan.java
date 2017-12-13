package com.global.winsoftandroidextension.MainMenu;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.content.Context;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.global.winsoftandroidextension.R;
import com.global.winsoftandroidextension.SQLclass;
import com.global.winsoftandroidextension.generator;
import com.global.winsoftandroidextension.report.Reportpenjualan;
import com.global.winsoftandroidextension.report.reportpenjualan_detail;
import com.global.winsoftandroidextension.report.reportpenjualanrekap2baris;

import java.sql.ResultSet;
import java.text.*;
import java.util.Calendar;
import java.util.Locale;

public class mainpenjualan extends ActionBarActivity {

    String sqldate1="",sqldate2="";
    Calendar myCalendar = Calendar.getInstance();
    DatePicker dp1;
    EditText dt1,dt2,cust1,cust2,sales1,sales2;
    DatePickerDialog date;
    SQLclass sqlclass;
    Button btnprint;
    Bundle bundle;
    List<String> itemcust = new ArrayList<String>();
    List<String> itemsales = new ArrayList<String>();
    String customerrange="",salesrange="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpenjualan);

        final ProgressBar pblogin = (ProgressBar) findViewById(R.id.pbpenj);
        sqlclass = new SQLclass();
        bundle = new Bundle();
        this.loaddata();

        dt1 = (EditText) findViewById(R.id.dt1);
        dt2 = (EditText) findViewById(R.id.dt2);

        cust1 =(EditText)findViewById(R.id.cust1);
        cust1.setText("");
        cust2 =(EditText)findViewById(R.id.cust2);
        cust2.setText("");

        sales1 =(EditText)findViewById(R.id.sales1);
        sales1.setText("");
        sales2 =(EditText)findViewById(R.id.sales2);
        sales2.setText("");

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        final String date = df.format(Calendar.getInstance().getTime());

        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        final String dateifempty = dateformat.format(Calendar.getInstance().getTime());

        dt1.setText(date);
        dt2.setText(date);
        final Spinner mySpinner=(Spinner) findViewById(R.id.saleschoice);
        btnprint = (Button) findViewById(R.id.btnprint);
        pblogin.setVisibility(View.GONE);

        final Spinner dropdown = (Spinner)findViewById(R.id.saleschoice);
        final String[] items = new String[]{"Rekap Penjualan", "Detail Penjualan","Rekap Penjualan (2 Baris)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };

        dt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(mainpenjualan.this ,date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        dt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(mainpenjualan.this ,date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        cust1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(mainpenjualan.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View custView = inflater.inflate(R.layout.dialog_searchandfilter, null);
                ListView temp=null;
                temp = (ListView) custView.findViewById(R.id.lvsnf);
                EditText editbox = (EditText) custView.findViewById(R.id.EditBox);

                builderSingle.setTitle("Select One Name:");


                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainpenjualan.this,
                        android.R.layout.simple_list_item_single_choice,
                        itemcust );
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

                temp.setOnItemClickListener(new OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3)
                    {
                        if(cust2.getText().toString().trim().equals("")){
                            String value = (String)adapter.getItemAtPosition(position);
                            /*try{
                            ResultSet temps = sqlclass.querydata("select kode_customer from iamcustomer where nama_customer='"+value+"'order by nama_customer asc");
                            while(temps.next()){
                                value= temps.getString("kode_customer");
                            }}
                            catch (Exception e){
                                Log.e("error",e.getMessage());
                            }*/
                            if(value.equals("Hapus Text")){
                                cust1.setText("");
                                cust2.setText("");
                            }else {
                                cust1.setText(value);
                                cust2.setText(value);
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
                                cust1.setText("");
                            }else {
                                cust1.setText(value);
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

        cust2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(mainpenjualan.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View custView = inflater.inflate(R.layout.dialog_searchandfilter, null);
                ListView temp=null;
                temp = (ListView) custView.findViewById(R.id.lvsnf);
                EditText editbox = (EditText) custView.findViewById(R.id.EditBox);

                builderSingle.setTitle("Select One Name:");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainpenjualan.this,
                        android.R.layout.simple_list_item_single_choice,
                        itemcust );
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

                temp.setOnItemClickListener(new OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3)
                    {
                        if(cust1.getText().toString().trim().equals("")){
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
                                cust1.setText("");
                                cust2.setText("");
                            }else {
                                cust1.setText(value);
                                cust2.setText(value);
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
                                cust2.setText("");
                            }else{
                            cust2.setText(value);
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

        sales1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(mainpenjualan.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View custView = inflater.inflate(R.layout.dialog_searchandfilter, null);
                ListView temp=null;
                temp = (ListView) custView.findViewById(R.id.lvsnf);
                EditText editbox = (EditText) custView.findViewById(R.id.EditBox);

                builderSingle.setTitle("Select One Name:");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainpenjualan.this,
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

                temp.setOnItemClickListener(new OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3)
                    {
                        if(sales1.getText().toString().trim().equals("")){
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
                                sales1.setText("");
                                sales2.setText("");
                            }
                            else {
                                sales1.setText(value);
                                sales2.setText(value);
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
                                sales1.setText("");
                            }else{
                            sales1.setText(value);
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

        sales2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(mainpenjualan.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View custView = inflater.inflate(R.layout.dialog_searchandfilter, null);
                ListView temp=null;
                temp = (ListView) custView.findViewById(R.id.lvsnf);
                EditText editbox = (EditText) custView.findViewById(R.id.EditBox);

                builderSingle.setTitle("Select or filter:");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainpenjualan.this,
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

                temp.setOnItemClickListener(new OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3)
                    {
                        if(sales1.getText().toString().trim().equals("")){
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
                                sales1.setText("");
                                sales2.setText("");
                            }else {
                                sales1.setText(value);
                                sales2.setText(value);
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
                                sales2.setText("");
                            }else {
                                sales2.setText(value);
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

        btnprint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pblogin.setVisibility(View.VISIBLE);
                String text = dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString();
                if (text=="Rekap Penjualan"){

                Intent i = new Intent(mainpenjualan.this, Reportpenjualan.class);
                    bundle.putString("custr", "nama_customer between '"+cust1.getText()+"' "+"and"+" '"+cust2.getText()+"' ");
                    bundle.putString("salesr", "nama_salesman between '"+sales1.getText()+"' "+"and"+" '"+sales2.getText()+"' ");
                    i.putExtras(bundle);
                if (sqldate1.trim().equals("")){
                    Log.e("err", sqldate1);
                    sqldate1=dateifempty;
                    Log.e("err", sqldate1);
                }
                if (sqldate2.trim().equals("")){
                    Log.e("err", sqldate2);
                    sqldate2=dateifempty;
                    Log.e("err", sqldate2);
                }
                generator.dt1=sqldate1;
                generator.dt2=sqldate2;
                i.putExtra("dt1",sqldate1);
                i.putExtra("dt2",sqldate2);
                startActivity(i);
                }

                if (text=="Detail Penjualan"){
                    Intent i = new Intent(mainpenjualan.this, reportpenjualan_detail.class);
                    bundle.putString("custr", "nama_customer between '"+cust1.getText()+"' "+"and"+" '"+cust2.getText()+"' ");
                    bundle.putString("salesr", "nama_salesman between '"+sales1.getText()+"' "+"and"+" '"+sales2.getText()+"' ");
                    i.putExtras(bundle);
                    if (sqldate1.equals("")){
                        sqldate1=dateifempty;
                    }
                    if (sqldate2.equals("")){
                        sqldate2=dateifempty;
                    }
                    generator.dt1=sqldate1;
                    generator.dt2=sqldate2;
                    i.putExtra("dt1",sqldate1);
                    i.putExtra("dt2",sqldate2);

                    startActivity(i);
                }

                if (text=="Rekap Penjualan (2 Baris)"){
                    Intent i = new Intent(mainpenjualan.this, reportpenjualanrekap2baris.class);
                    if (sqldate1.equals("")){
                        sqldate1=dateifempty;
                    }
                    if (sqldate2.equals("")){
                        sqldate2=dateifempty;
                    }
                    bundle.putString("custr", "nama_customer between '"+cust1.getText()+"' "+"and"+" '"+cust2.getText()+"' ");
                    bundle.putString("salesr", "nama_salesman between '"+sales1.getText()+"' "+"and"+" '"+sales2.getText()+"' ");
                    i.putExtras(bundle);
                    generator.dt1=sqldate1;
                    generator.dt2=sqldate2;
                    i.putExtra("dt1",sqldate1);
                    i.putExtra("dt2",sqldate2);

                    startActivity(i);
                }
                pblogin.setVisibility(View.GONE);
            }
        });
    }

    private void updateLabel1() {
        String sqlformat = "MM/dd/yyyy";
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sql = new SimpleDateFormat(sqlformat, Locale.US);
        sqldate1=sql.format(myCalendar.getTime());
        dt1.setText(sdf.format(myCalendar.getTime()));
    }


    private void updateLabel2() {
        String sqlformat = "MM/dd/yyyy";
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sql = new SimpleDateFormat(sqlformat, Locale.US);
        sqldate2=sql.format(myCalendar.getTime());
        dt2.setText(sdf.format(myCalendar.getTime()));
    }

    private void loaddata(){
        try{
            ResultSet result = sqlclass.querydata("select nama_customer from iamcustomer order by nama_customer asc");
            itemcust.add("Hapus Text");
            while(result.next()){
                Log.e("data",result.getString("nama_customer"));
                itemcust.add(result.getString("nama_customer"));
            }
            itemcust.add("Hapus Text");
        }
        catch (Exception e){
            Log.e("error",e.toString() );
        }
        try{
            ResultSet result = sqlclass.querydata("select nama_salesman from iamsalesman where tdk_aktif=0 order by nama_salesman asc");
            itemsales.add("Hapus Text");
            while(result.next()){
                Log.e("data",result.getString("nama_salesman"));
                itemsales.add(result.getString("nama_salesman"));
            }
            itemsales.add("Hapus Text");
        }
        catch (Exception e){
            Log.e("error",e.toString() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainpenjualan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.emptytext) {
            cust1.setText("");
            cust2.setText("");
            sales1.setText("");
            sales2.setText("");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
