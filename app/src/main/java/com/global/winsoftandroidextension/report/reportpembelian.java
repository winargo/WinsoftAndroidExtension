package com.global.winsoftandroidextension.report;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.global.winsoftandroidextension.R;
import com.global.winsoftandroidextension.SQLclass;
import com.global.winsoftandroidextension.generator;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.global.winsoftandroidextension.generator.FIFTH_COLUMN;
import static com.global.winsoftandroidextension.generator.SECOND_COLUMN;
import static com.global.winsoftandroidextension.generator.FOURTH_COLUMN;
import static com.global.winsoftandroidextension.generator.FIRST_COLUMN;
import static com.global.winsoftandroidextension.generator.THIRD_COLUMN;


public class reportpembelian extends Activity {

    String dt1,dt2;
    TextView txtview;
    SQLclass sqlclass;
    ListView lvpembelian;
    String z;
    generator data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportpembelian);


        Bundle extra= getIntent().getExtras();
        sqlclass = new SQLclass();

        txtview= (TextView) findViewById(R.id.totalpembelian);
        dt1 = generator.dt1;
        dt2 = generator.dt2;
        lvpembelian = (ListView) findViewById(R.id.lvpembelian);
        NumberFormat format1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        NumberFormat floatformat = new DecimalFormat("#########.##");
        try {
            ResultSet Result = sqlclass.querydata("select * from iatpembelian where tanggal between '"+ dt1 + "' and '"  + dt2 + "' order by tanggal,no_faktur asc");
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
                datanum.put(THIRD_COLUMN, Result.getString("kode_supplier"));
                datanum.put(FOURTH_COLUMN, Result.getString("kode_lokasi"));
                datanum.put(FIFTH_COLUMN,"Rp " + format1.format(Result.getFloat("jumlah_faktur_rp")));
                total = total+Result.getInt("jumlah_faktur_rp");
                generator.list.add(datanum);
            }
            txtview.setText("  Total Pembelian   :    Rp " +formatter.format( total));
            generator.lvbuy adapter=new generator.lvbuy(this, generator.list);
            if (generator.list.isEmpty()){
                HashMap<String, String> datanum = new HashMap<String, String>();
                datanum.put(THIRD_COLUMN,"No Data");
            }
            lvpembelian.setAdapter(adapter);
            lvpembelian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    int pos = position + 1;
                    Toast.makeText(reportpembelian.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
                }

            });
            //add generator adapter here

        } catch (Exception e) {
            Toast.makeText(reportpembelian.this, "No Data", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reportpembelian, menu);
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
