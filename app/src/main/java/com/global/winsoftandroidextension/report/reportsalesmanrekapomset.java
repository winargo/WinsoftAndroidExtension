package com.global.winsoftandroidextension.report;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Locale;

import static com.global.winsoftandroidextension.generator.FIFTH_COLUMN;
import static com.global.winsoftandroidextension.generator.FIRST_COLUMN;
import static com.global.winsoftandroidextension.generator.FOURTH_COLUMN;
import static com.global.winsoftandroidextension.generator.SECOND_COLUMN;
import static com.global.winsoftandroidextension.generator.THIRD_COLUMN;
import static com.global.winsoftandroidextension.generator.SIX;


public class reportsalesmanrekapomset extends AppCompatActivity {


    String dt1,dt2,sales="";
    TextView txtview;
    SQLclass sqlclass;
    ListView lvsalesmanomzet;
    String z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportsalesmanrekapomset);


        Bundle extra= getIntent().getExtras();
        sqlclass = new SQLclass();

        txtview= (TextView) findViewById(R.id.totalomzet);
        dt1 = generator.dt1;
        dt2 = generator.dt2;
        lvsalesmanomzet = (ListView) findViewById(R.id.lvsalesmanrekapomset);
        NumberFormat format1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        NumberFormat floatformat = new DecimalFormat("#########.##");
        int penjualan = 0,retur = 0,grandtotal=0;
        try {
            ResultSet query = sqlclass.querydata("select * from iamsalesman where tdk_aktif=0");
            int total=0,number=1,declarator=0;
            generator.list.clear();
            while (query.next()){
                sales = query.getString("kode_salesman");
                ResultSet Result = sqlclass.querydata("select sum(jumlah_faktur_rp) as jumlah from iatpenjualan where tanggal between '"+ dt1 + "' and '"  + dt2 + "' and kode_salesman='"+sales+"'");
                while (Result.next()) {
                    penjualan = penjualan + Result.getInt("jumlah");
                }
                Result = sqlclass.querydata("select sum(jumlah_faktur_rp) as jumlah from iatrjual where tanggal between '"+ dt1 + "' and '"  + dt2 + "' and kode_salesman='"+sales+"'");
                while (Result.next()) {
                    retur = retur + Result.getInt("jumlah");
                }
                total = penjualan + retur;
                HashMap<String, String> datanum = new HashMap<String, String>();
                if(total!=0) {
                    datanum.put(FIRST_COLUMN, query.getString("kode_salesman"));
                    datanum.put(SECOND_COLUMN, query.getString("nama_salesman"));
                    datanum.put(THIRD_COLUMN, String.valueOf(formatter.format(penjualan)));
                    datanum.put(FOURTH_COLUMN, String.valueOf(formatter.format(retur)));
                    grandtotal = grandtotal + total;
                    datanum.put(FIFTH_COLUMN, String.valueOf(formatter.format(total)));
                    datanum.put(SIX, String.valueOf(number) + ".");
                    generator.list.add(datanum);
                    number+=1;
                    penjualan=0;
                    retur=0;
                }

            }
            txtview.setText("Total Omzet   :    Rp " +formatter.format(grandtotal));
            generator.rekapomset adapter=new generator.rekapomset(this, generator.list);
            lvsalesmanomzet.setAdapter(adapter);
            lvsalesmanomzet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    int pos = position + 1;
                    Toast.makeText(reportsalesmanrekapomset.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
                }

            });
            //add generator adapter here

        } catch (Exception e) {
            Toast.makeText(reportsalesmanrekapomset.this, "No Data", Toast.LENGTH_LONG).show();
        }

    }
}
