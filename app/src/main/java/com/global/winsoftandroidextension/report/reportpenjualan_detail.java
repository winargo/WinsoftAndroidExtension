package com.global.winsoftandroidextension.report;

import android.app.Activity;
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

public class reportpenjualan_detail extends Activity {

    String dt1,dt2;
    TextView txtview;
    SQLclass sqlclass;
    String z;
    Bundle bundle;
    String query="";
    String value="",valuesales="";

    //private lvadapter_detail adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportpenjualan_detail);

        sqlclass = new SQLclass();

        LinearLayout container = (LinearLayout) findViewById(R.id.llsales_detail);
        LayoutInflater inflater = LayoutInflater.from(this);

        bundle = getIntent().getExtras();
        value = bundle.getString("custr");
        valuesales = bundle.getString("salesr");

        txtview = (TextView) findViewById(R.id.totalpenjualan);

        dt1 = generator.dt1;
        dt2 = generator.dt2;

        //FILTER QUERY HERE
        query="select * from iatpenjualan a join iamcustomer c on a.kode_customer=c.kode_customer join iamsalesman d on a.kode_salesman=d.kode_salesman where a.tanggal between '"+ dt1 + "' and '"  + dt2 + "'";

        if(!value.trim().equals("nama_customer between '' and ''")){
            query=query+" and c.kode_customer in (select kode_customer from iamcustomer where "+value+")";
        }
        if(!valuesales.trim().equals("nama_salesman between '' and ''")){
            query=query+" and d.kode_salesman in (select kode_salesman from iamsalesman where "+valuesales+")";
        }
        String filter=" order by tanggal,no_faktur asc";
        query=query+filter;

        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        try {
            ResultSet Result = sqlclass.querydata(query);

            Log.e("erro",query);

            float grandtotal=0.0f;
            String initiatetotalperday="";
            float totalperday=0f;
            String tempdate="";
            SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat Format1 = new SimpleDateFormat("dd-MMMM-yyyy");
            generator.list.clear();
            while (Result.next()) {
                int jangkaresult=0;
                String strCurrentDate =Result.getString("tanggal");
                String strjangkakredit=Result.getString("j_tempo");

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


                String date = Format1.format(newDate);
                if(!date.trim().equals(tempdate)){
                    if(!initiatetotalperday.equals("")){

                        LinearLayout linear_layout4 = (LinearLayout) inflater.inflate(R.layout.row_sales_detail4, null);
                        TextView thirtheen=(TextView) linear_layout4.findViewById(R.id.perday1);
                        thirtheen.setText("Subtotal per  "+tempdate+"  :  ");
                        TextView fourtheen=(TextView) linear_layout4.findViewById(R.id.perday2);
                        fourtheen.setText(String.valueOf(formatter.format(totalperday)));
                        container.addView(linear_layout4);

                    }
                    tempdate=date;
                    LinearLayout linear_layout = (LinearLayout) inflater.inflate(R.layout.row_sales_detail, null);
                    TextView datetext = (TextView) linear_layout.findViewById(R.id.tanggal);
                    totalperday=0;
                    datetext.setText(tempdate);
                    container.addView(linear_layout);
                    initiatetotalperday="count";
                }
                LinearLayout linear_layout1 = (LinearLayout) inflater.inflate(R.layout.row_sales_detail1, null);
                TextView one = (TextView) linear_layout1.findViewById(R.id.faktur);
                one.setText(Result.getString("no_Faktur"));
                TextView two = (TextView) linear_layout1.findViewById(R.id.customer);
                two.setText(Result.getString("nama_customer"));
                TextView three = (TextView) linear_layout1.findViewById(R.id.jangkakredit);
                three.setText(jangkaresult+"H");
                TextView four = (TextView) linear_layout1.findViewById(R.id.sales);
                four.setText(Result.getString("kode_Salesman"));
                TextView five = (TextView) linear_layout1.findViewById(R.id.gudang);
                five.setText(Result.getString("kode_lokasi"));
                container.addView(linear_layout1);
                float total=0;
                ResultSet query1 = sqlclass.querydata("select * from iatpenjualan1 where no_faktur='"+Result.getString("no_faktur")+"'");

                while(query1.next()) {
                    LinearLayout linear_layout2 = (LinearLayout) inflater.inflate(R.layout.row_sales_detail2, null);
                    TextView six = (TextView) linear_layout2.findViewById(R.id.salesitemname);
                    six.setText(query1.getString("nama_stock"));
                    TextView seven = (TextView) linear_layout2.findViewById(R.id.salesqty);
                    seven.setText(formatter.format(query1.getFloat("qty")));
                    TextView eight = (TextView) linear_layout2.findViewById(R.id.salescpu);
                    eight.setText(formatter.format(query1.getFloat("harga_jual")));
                    float subtotal = query1.getFloat("qty")*query1.getFloat("harga_jual");
                    TextView nine = (TextView) linear_layout2.findViewById(R.id.salesdiscount);
                    nine.setText(query1.getString("discount"));
                    TextView ten = (TextView) linear_layout2.findViewById(R.id.salessubtotal);
                    ten.setText(formatter.format(query1.getFloat("jumlah")));
                    container.addView(linear_layout2);
                    total=total+subtotal;
                }
                LinearLayout linear_layout3 = (LinearLayout) inflater.inflate(R.layout.row_sales_detail3, null);
                TextView eleven=(TextView) linear_layout3.findViewById(R.id.total1);
                eleven.setText("Sub Total "+Result.getString("no_faktur")+"  : ");
                TextView twelve=(TextView) linear_layout3.findViewById(R.id.total2);
                twelve.setText(String.valueOf(formatter.format(total)));
                grandtotal=grandtotal+total;
                totalperday=totalperday+total;
                container.addView(linear_layout3);
            }
            LinearLayout linear_layout5 = (LinearLayout) inflater.inflate(R.layout.row_sales_detail4, null);
            TextView thirtheen=(TextView) linear_layout5.findViewById(R.id.perday1);
            thirtheen.setText("Subtotal per  "+tempdate+"  :  ");
            TextView fourtheen=(TextView) linear_layout5.findViewById(R.id.perday2);
            fourtheen.setText(String.valueOf(formatter.format(totalperday)));
            container.addView(linear_layout5);
            LinearLayout linear_layout6 = (LinearLayout) inflater.inflate(R.layout.row_sales_detail5, null);
            TextView fiftheen=(TextView) linear_layout6.findViewById(R.id.grandtotal1);
            fiftheen.setText("Grand Total    :   ");
            TextView seventheen=(TextView) linear_layout6.findViewById(R.id.grandtotal2);
            seventheen.setText(String.valueOf(formatter.format(grandtotal)));
            container.addView(linear_layout6);
            //add generator adapter here

        } catch (Exception e) {
            Toast.makeText(reportpenjualan_detail.this, "No Data", Toast.LENGTH_LONG).show();
            Log.e("ERRO",e.getMessage().toString());
        }
    }

}