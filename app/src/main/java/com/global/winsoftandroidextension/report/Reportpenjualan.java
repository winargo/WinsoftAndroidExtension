package com.global.winsoftandroidextension.report;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import static com.global.winsoftandroidextension.generator.FIRST_COLUMN;
import static com.global.winsoftandroidextension.generator.SECOND_COLUMN;
import static com.global.winsoftandroidextension.generator.THIRD_COLUMN;
import static com.global.winsoftandroidextension.generator.FOURTH_COLUMN;
import static com.global.winsoftandroidextension.generator.FIFTH_COLUMN;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.text.*;
import java.util.Date;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.global.winsoftandroidextension.R;
import com.global.winsoftandroidextension.SQLclass;
import com.global.winsoftandroidextension.generator;


public class Reportpenjualan extends Activity {

    String dt1="",dt2="",value="",valuesales="";
    TextView txtview;
    SQLclass sqlclass;
    ListView lvpenjualan;
    String z;
    generator data;
    ResultSet Result;
    Bundle bundle;
    String query="";

    private ArrayList<HashMap<String, String>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportpenjualan);

        bundle = getIntent().getExtras();

        value = bundle.getString("custr");
        valuesales = bundle.getString("salesr");

        sqlclass = new SQLclass();

        query="select * from iatpenjualan where tanggal between '"+ dt1 + "' and '"  + dt2 + "'";

        if(!value.trim().equals("nama_customer between '' and ''")){
            query=query+" and kode_customer in (select kode_customer from iamcustomer where "+value+")";
        }
        if(!valuesales.trim().equals("nama_salesman between '' and ''")){
            query=query+" and kode_salesman in (select kode_salesman from iamsalesman where "+valuesales+")";
        }
        String filter=" order by tanggal,no_faktur asc";
        query=query+filter;

        txtview= (TextView) findViewById(R.id.totalpenjualan);
        dt1 = generator.dt1;
        dt2 = generator.dt2;



        lvpenjualan = (ListView) findViewById(R.id.lvpenjualan);

        NumberFormat format1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        NumberFormat floatformat = new DecimalFormat("#########.##");

        try {
            Log.e("info",query);
                Result = sqlclass.querydata(query);
            Log.e("info",Result.toString());
            int total=0;
            generator.list.clear();
               while (Result.next()) {
                   HashMap<String, String> datanum = new HashMap<String, String>();
                   datanum.put(FIRST_COLUMN, Result.getString("no_faktur"));

                   String strCurrentDate =Result.getString("tanggal");
                   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                   Date newDate = format.parse(strCurrentDate);
                   format = new SimpleDateFormat("dd-MM-yyyy");
                   String date = format.format(newDate);

                   datanum.put(SECOND_COLUMN,date);
                   datanum.put(THIRD_COLUMN, Result.getString("kode_salesman"));
                   datanum.put(FOURTH_COLUMN, Result.getString("kode_customer"));
                   datanum.put(FIFTH_COLUMN,"Rp " + format1.format(Result.getFloat("jumlah_faktur_rp")));
                   total = total+Result.getInt("jumlah_faktur_rp");
                   generator.list.add(datanum);
                }
            txtview.setText("Total Penjualan   :    Rp " +formatter.format( total));
            generator.lvadapter adapter=new generator.lvadapter(this, generator.list);
            lvpenjualan.setAdapter(adapter);
            lvpenjualan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    int pos = position + 1;
                    Toast.makeText(Reportpenjualan.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
                }

            });
                //add generator adapter here

        } catch (Exception e) {
            Toast.makeText(Reportpenjualan.this,"No Data", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reportpenjualan, menu);
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
