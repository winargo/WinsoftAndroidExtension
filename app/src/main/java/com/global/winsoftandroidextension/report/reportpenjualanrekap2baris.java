package com.global.winsoftandroidextension.report;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
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

public class reportpenjualanrekap2baris extends Activity {

    String dt1="",dt2="";
    TextView txtview;
    SQLclass sqlclass;
    ListView lvpenjualan,lvpenjualandata;
    String z;
    String query1="";
    String value="",valuesales="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportpenjualanrekap2baris);

        sqlclass = new SQLclass();
        LinearLayout container = (LinearLayout) findViewById(R.id.llsales_rkp2);

        LayoutInflater inflater = LayoutInflater.from(this);
        Bundle bundle = getIntent().getExtras();

        dt1 = generator.dt1;
        dt2 = generator.dt2;

        value = bundle.getString("custr");
        valuesales = bundle.getString("salesr");

        //FILTER QUERY HERE
        query1="select * from iatpenjualan a join iamcustomer c on a.kode_customer=c.kode_customer join iamsalesman d on a.kode_salesman=d.kode_salesman where a.tanggal between '"+ dt1 + "' and '"  + dt2 + "'";

        if(!value.trim().equals("nama_customer between '' and ''")){
            query1=query1+" and c.kode_customer in (select kode_customer from iamcustomer where "+value+")";
        }
        if(!valuesales.trim().equals("nama_salesman between '' and ''")){
            query1=query1+" and d.kode_salesman in (select kode_salesman from iamsalesman where "+valuesales+")";
        }
        String filter=" order by tanggal,no_faktur asc";
        query1=query1+filter;
        Log.e("EROR",query1);

        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        try {
            ResultSet Result = sqlclass.querydata(query1);
            int total=0;
            generator.list.clear();
            while (Result.next()) {
                int jangkaresult=0;
                String strCurrentDate =Result.getString("tanggal");
                String strjangkakredit=Result.getString("j_tempo");
                SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date jangka=Format.parse(strjangkakredit);
                Date newDate = Format.parse(strCurrentDate);
                if(!jangka.equals(newDate)){
                    long diff= jangka.getTime()-newDate.getTime();
                    long seconds = diff / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    long days = hours / 24;
                    jangkaresult=(int) (long)days;
                }else
                {
                    jangkaresult=0;
                }
                Format = new SimpleDateFormat("dd-MMMM-yyyy");
                String date = Format.format(newDate);
                LinearLayout linear_layout1 = (LinearLayout) inflater.inflate(R.layout.row_sales_rekap2baris, null);
                TextView one = (TextView) linear_layout1.findViewById(R.id.rkp2tanggal);
                one.setText(date);
                TextView two = (TextView) linear_layout1.findViewById(R.id.rkp2pay);
                String temppay="";
                if(Result.getString("cara_bayar").equals("KREDIT")){
                    temppay="K";
                }
                else
                {
                    temppay="C";
                }
                two.setText(temppay);
                TextView three = (TextView) linear_layout1.findViewById(R.id.rkp2jk);
                three.setText(jangkaresult+"H");
                TextView four = (TextView) linear_layout1.findViewById(R.id.rkp2faktur);
                four.setText(Result.getString("no_faktur"));
                TextView fourth = (TextView) linear_layout1.findViewById(R.id.rkp2cust);
                fourth.setText(Result.getString("nama_customer"));
                TextView five = (TextView) linear_layout1.findViewById(R.id.rkp2sales);
                five.setText(Result.getString("kode_salesman"));
                TextView six = (TextView) linear_layout1.findViewById(R.id.rkp2subtotal);
                six.setText(formatter.format(Result.getInt("jumlah_faktur")));
                TextView seven = (TextView) linear_layout1.findViewById(R.id.rkp2disc);
                ResultSet query = sqlclass.querydata("select * from iatpenjualan1 where no_faktur='"+Result.getString("no_faktur")+"'");
                int disc=0;
                while(query.next()){
                    disc=disc+query.getInt("discount_nilai");
                }
                seven.setText(formatter.format(disc));
                TextView eight = (TextView) linear_layout1.findViewById(R.id.rkp2ppn);
                eight.setText(formatter.format(Result.getInt("ppn_nilai")));
                TextView nine = (TextView) linear_layout1.findViewById(R.id.rkp2exp);
                nine.setText(Result.getString("kode_expedisi"));
                TextView ten = (TextView) linear_layout1.findViewById(R.id.rkp2id);
                ten.setText(Result.getString("user_id"));
                TextView eleven = (TextView) linear_layout1.findViewById(R.id.rkp2total);
                eleven.setText(formatter.format(Result.getInt("jumlah_faktur_rp")));
                total=total+Result.getInt("jumlah_faktur_rp");
                container.addView(linear_layout1);
            }
            LinearLayout linear_layout3 = (LinearLayout) inflater.inflate(R.layout.row_main_grandtotal, null);
            TextView eleven=(TextView) linear_layout3.findViewById(R.id.grandtotal);
            eleven.setText("Total Penjualan     :     "+formatter.format(total));
            container.addView(linear_layout3);
            //add generator adapter here

        } catch (Exception e) {
            Toast.makeText(reportpenjualanrekap2baris.this, "No Data", Toast.LENGTH_LONG).show();
            Log.e("ERRO",e.getMessage().toString());
        }
    }
}
