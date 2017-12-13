package com.global.winsoftandroidextension.report;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
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

public class reportsalesman_omzetsalesman extends AppCompatActivity {

    String dt1,dt2;
    TextView txtview;
    SQLclass sqlclass;
    String z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportsalesman_omzetsalesman);

        sqlclass = new SQLclass();
        LinearLayout container = (LinearLayout) findViewById(R.id.llsalesman_omzet);
        LayoutInflater inflater = LayoutInflater.from(this);
        Bundle extra = getIntent().getExtras();
        dt1 = generator.dt1;
        dt2 = generator.dt2;

        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        try {
            ResultSet fetch = sqlclass.querydata("select kode_salesman,nama_salesman from iamsalesman");
            float count=0.0f;
            String initiatetotalperday="";
            float totalsales=0f;
            String tempdate="";
            SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat Format1 = new SimpleDateFormat("dd-MMMM-yyyy");
            String customerchecker="";
            String datechecker="";
            float totalallsales=0;
            generator.list.clear();
            while (fetch.next()) {
                String salesman="";
                String namasalesman="";
                int enablecaching=0;
                salesman=fetch.getString("kode_salesman");
                namasalesman=fetch.getString("nama_Salesman");
                LinearLayout linearlayoutheader = (LinearLayout) inflater.inflate(R.layout.row_sample_text,null);
                TextView salesname=(TextView) linearlayoutheader.findViewById(R.id.sampletext);
                salesname.setText(namasalesman);
                ResultSet salesnol = sqlclass.querydata("select sum(jumlah_faktur_rp) as jumlah_faktur_rp from iatpenjualan where tanggal between '"+dt1+"' and '"+dt2+"' and kode_salesman='"+salesman+"'");
                while (salesnol.next()){
                    if(salesnol.getInt("jumlah_faktur_rp")!=0){
                        container.addView(linearlayoutheader);
                        enablecaching=1;
                    }
                }
                ResultSet Result = sqlclass.querydata("select * from iatpenjualan where tanggal between '"+dt1+"' and '"+dt2+"' and kode_salesman='"+salesman+"' order by kode_salesman,tanggal,kode_customer asc");
                totalsales = 0;
                while (Result.next()){
                    count = 0;

                    String strCurrentDate =Result.getString("tanggal");

                    Date newDate = Format.parse(strCurrentDate);

                    String date = Format1.format(newDate);
                    String dateori = Format.format(newDate);
                    LinearLayout linear_layout1 = (LinearLayout) inflater.inflate(R.layout.row_salesman_omzetsalesman, null);
                    TextView one1 = (TextView) linear_layout1.findViewById(R.id.tanggal);
                    one1.setText(date);
                    TextView one = (TextView) linear_layout1.findViewById(R.id.faktur);
                    one.setText(Result.getString("no_Faktur"));
                    TextView two = (TextView) linear_layout1.findViewById(R.id.customer);
                    ResultSet temp = sqlclass.querydata("select nama_customer from iamcustomer where kode_customer='"+Result.getString("kode_customer")+"'");
                    while(temp.next())
                    {
                        two.setText(temp.getString("nama_Customer"));
                    }
                    TextView five = (TextView) linear_layout1.findViewById(R.id.sales);
                    five.setText(Result.getString("kode_salesman"));
                    TextView six = (TextView) linear_layout1.findViewById(R.id.penjualan);
                    six.setText(String.valueOf(Result.getInt("jumlah_faktur_rp")));
                    count=count + Result.getInt("jumlah_faktur_rp");
                    totalsales = totalsales + count ;
                    TextView seven = (TextView) linear_layout1.findViewById(R.id.retur);
                    seven.setText("");
                    TextView eight = (TextView) linear_layout1.findViewById(R.id.omzet);
                    eight.setText(String.valueOf(count));
                    if(enablecaching==1) {
                        container.addView(linear_layout1);
                    }
                    if (!datechecker.equals(dateori) || !customerchecker.equals(Result.getString("kode_customer"))){
                        ResultSet retur = sqlclass.querydata("select * from iatrjual where tanggal='"+dateori+"' and kode_customer='"+Result.getString("kode_customer")+"'");
                        while (retur.next()){
                            LinearLayout returlayout =(LinearLayout) inflater.inflate(R.layout.row_salesman_omzetsalesman,null);
                            TextView returdate = (TextView) returlayout.findViewById(R.id.tanggal);
                            returdate.setText(date);
                            TextView returone = (TextView) returlayout.findViewById(R.id.faktur);
                            returone.setText(retur.getString("no_Faktur"));
                            TextView returthree = (TextView) returlayout.findViewById(R.id.customer);
                            ResultSet returname = sqlclass.querydata("select nama_customer from iamcustomer where kode_customer='"+retur.getString("kode_customer")+"'");
                            while(returname.next())
                            {
                                returthree.setText(returname.getString("nama_Customer"));
                            }
                            TextView returfive = (TextView) returlayout.findViewById(R.id.sales);
                            returfive.setText(Result.getString("kode_salesman"));
                            TextView retursix = (TextView) returlayout.findViewById(R.id.retur);
                            retursix.setText(String.valueOf(Result.getInt("jumlah_faktur_rp")));
                            count=count + Result.getInt("jumlah_faktur_rp");
                            totalsales=totalsales-count;
                            TextView returseven = (TextView) returlayout.findViewById(R.id.penjualan);
                            returseven.setText("");
                            TextView retureight = (TextView) returlayout.findViewById(R.id.omzet);
                            retureight.setText(String.valueOf(count));
                            if(enablecaching==1) {
                                container.addView(returlayout);
                            }
                        }

                    }
                }
                totalallsales=totalallsales+totalsales;
                LinearLayout subtotal =(LinearLayout) inflater.inflate(R.layout.row_sales_detail3,null);
                TextView total1 = (TextView) subtotal.findViewById(R.id.total1);
                total1.setText("Sub Total   "+namasalesman+"    :");
                TextView total2 = (TextView) subtotal.findViewById(R.id.total2);
                total2.setText(formatter.format(totalsales));
                if(enablecaching==1){
                container.addView(subtotal);}
                enablecaching=0;
            }
            LinearLayout omzet =(LinearLayout) inflater.inflate(R.layout.row_sales_detail5,null);
            TextView grand1 = (TextView) omzet.findViewById(R.id.grandtotal1);
            grand1.setText("Omzet    :  "+totalallsales+"");
            TextView grand2 = (TextView) omzet.findViewById(R.id.grandtotal2);
            grand1.setText(formatter.format(totalallsales));




        } catch (Exception e) {
            Toast.makeText(reportsalesman_omzetsalesman.this, "No Data", Toast.LENGTH_LONG).show();
            Log.e("ERRO",e.getMessage().toString());
        }
    }
}
