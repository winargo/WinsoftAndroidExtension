package com.global.winsoftandroidextension.MainMenu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.global.winsoftandroidextension.R;
import com.global.winsoftandroidextension.generator;
import com.global.winsoftandroidextension.report.reportpenjualanrekap2baris;
import com.global.winsoftandroidextension.report.reportsalesman_omzetsalesman;
import com.global.winsoftandroidextension.report.reportsalesmanrekapomset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class mainsalesman extends AppCompatActivity {


    String sqldate1="",sqldate2="";
    Calendar myCalendar = Calendar.getInstance();
    DatePicker dp1;
    EditText dt1,dt2;
    DatePickerDialog date;
    Button btnprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsalesman);

        dt1 = (EditText) findViewById(R.id.dt1);
        final ProgressBar pblogin = (ProgressBar) findViewById(R.id.pbpenj);
        dt2 = (EditText) findViewById(R.id.dt2);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        final String dateifempty = dateformat.format(Calendar.getInstance().getTime());

        dt1.setText(date);
        dt2.setText(date);
        final Spinner mySpinner=(Spinner) findViewById(R.id.saleschoice);
        btnprint = (Button) findViewById(R.id.btnprint);
        pblogin.setVisibility(View.GONE);

        final Spinner dropdown = (Spinner)findViewById(R.id.saleschoice);
        String[] items = new String[]{"Rekap Omzet Salesman", "Omzet Salesman","Omzet Salesman Per Stock"};
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

        dt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(mainsalesman.this ,date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(mainsalesman.this ,date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnprint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pblogin.setVisibility(View.VISIBLE);
                String text = dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString();
                if (text=="Rekap Omzet Salesman"){
                    Intent i = new Intent(mainsalesman.this, reportsalesmanrekapomset.class);
                    if (sqldate1.trim().equals("")){
                        sqldate1=dateifempty;
                    }
                    if (sqldate2.trim().equals("")){
                        sqldate2=dateifempty;
                    }
                    generator.dt1=sqldate1;
                    generator.dt2=sqldate2;
                    i.putExtra("dt1",sqldate1);
                    i.putExtra("dt2",sqldate2);
                    startActivity(i);
                }
                if (text=="Omzet Salesman"){
                    Intent i = new Intent(mainsalesman.this, reportsalesman_omzetsalesman.class);
                    if (sqldate1.trim().equals("")){
                        sqldate1=dateifempty;
                    }
                    if (sqldate2.trim().equals("")){
                        sqldate2=dateifempty;
                    }
                    generator.dt1=sqldate1;
                    generator.dt2=sqldate2;
                    i.putExtra("dt1",sqldate1);
                    i.putExtra("dt2",sqldate2);

                    startActivity(i);
                }
                if (text=="Omzet Salesman Per Stock"){
                    Intent i = new Intent(mainsalesman.this, reportpenjualanrekap2baris.class);
                    if (sqldate1.trim().equals("")){
                        sqldate1=dateifempty;
                    }
                    if (sqldate2.trim().equals("")){
                        sqldate2=dateifempty;
                    }
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
        return super.onOptionsItemSelected(item);
    }
}
