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

public class reportpembelian_detail extends AppCompatActivity {

    String dt1,dt2;
    TextView txtview;
    SQLclass sqlclass;
    String z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportpembelian_detail);

        sqlclass = new SQLclass();
        LinearLayout container = (LinearLayout) findViewById(R.id.llbuy_detail);
        LayoutInflater inflater = LayoutInflater.from(this);
        Bundle extra = getIntent().getExtras();
        dt1 = generator.dt1;
        dt2 = generator.dt2;

        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        try {
            ResultSet Result = sqlclass.querydata("select * from iatpembelian a join iamsupplier c on a.kode_supplier=c.kode_supplier where a.tanggal between '"+ dt1 + "' and '"  + dt2 + "' order by tanggal,no_faktur asc");
            float grandtotal=0.0f,totalperday=0f;
            String initiatetotalperday="",tempdate="";

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
                        LinearLayout linear_layout4 = (LinearLayout) inflater.inflate(R.layout.row_buy_detail4, null);
                        TextView thirtheen=(TextView) linear_layout4.findViewById(R.id.perday1);
                        thirtheen.setText("Subtotal per  "+tempdate+"  :  ");
                        TextView fourtheen=(TextView) linear_layout4.findViewById(R.id.perday2);
                        fourtheen.setText(String.valueOf(formatter.format(totalperday)));
                        container.addView(linear_layout4);

                    }
                    tempdate=date;
                    LinearLayout linear_layout = (LinearLayout) inflater.inflate(R.layout.row_buy_detail, null);
                    TextView datetext = (TextView) linear_layout.findViewById(R.id.tanggal);
                    totalperday=0;
                    datetext.setText(tempdate);
                    container.addView(linear_layout);
                    initiatetotalperday="count";
                }
                LinearLayout linear_layout1 = (LinearLayout) inflater.inflate(R.layout.row_buy_detail1, null);
                TextView one = (TextView) linear_layout1.findViewById(R.id.faktur);
                one.setText(Result.getString("no_Faktur"));
                TextView two = (TextView) linear_layout1.findViewById(R.id.supplier);
                two.setText(Result.getString("nama_supplier"));
                TextView three = (TextView) linear_layout1.findViewById(R.id.jangkakredit);
                three.setText(jangkaresult+"H");
                TextView five = (TextView) linear_layout1.findViewById(R.id.gudang);
                five.setText(Result.getString("kode_lokasi"));
                container.addView(linear_layout1);
                float total=0;
                ResultSet query = sqlclass.querydata("select * from iatpembelian1 where no_faktur='"+Result.getString("no_faktur")+"'");
                while(query.next()) {
                    LinearLayout linear_layout2 = (LinearLayout) inflater.inflate(R.layout.row_buy_detail2, null);
                    TextView six = (TextView) linear_layout2.findViewById(R.id.salesitemname);
                    six.setText(query.getString("nama_stock"));
                    TextView seven = (TextView) linear_layout2.findViewById(R.id.salesqty);
                    seven.setText(formatter.format(query.getFloat("qty")));
                    TextView eight = (TextView) linear_layout2.findViewById(R.id.salescpu);
                    eight.setText(formatter.format(query.getFloat("harga_beli")));
                    float subtotal = query.getFloat("qty")*query.getFloat("harga_beli");
                    TextView nine = (TextView) linear_layout2.findViewById(R.id.salesdiscount);
                    nine.setText(query.getString("discount"));
                    TextView ten = (TextView) linear_layout2.findViewById(R.id.salessubtotal);
                    ten.setText(formatter.format(query.getFloat("jumlah")));
                    container.addView(linear_layout2);
                    total=total+subtotal;
                }
                LinearLayout linear_layout3 = (LinearLayout) inflater.inflate(R.layout.row_buy_detail3, null);
                TextView eleven=(TextView) linear_layout3.findViewById(R.id.total1);
                eleven.setText("Sub Total "+Result.getString("no_faktur")+"  : ");
                TextView twelve=(TextView) linear_layout3.findViewById(R.id.total2);
                twelve.setText(String.valueOf(formatter.format(total)));
                grandtotal=grandtotal+total;
                totalperday=totalperday+total;
                container.addView(linear_layout3);
            }
            LinearLayout linear_layout5 = (LinearLayout) inflater.inflate(R.layout.row_buy_detail4, null);
            TextView thirtheen=(TextView) linear_layout5.findViewById(R.id.perday1);
            thirtheen.setText("Subtotal per  "+tempdate+"  :  ");
            TextView fourtheen=(TextView) linear_layout5.findViewById(R.id.perday2);
            fourtheen.setText(String.valueOf(formatter.format(totalperday)));
            container.addView(linear_layout5);
            LinearLayout linear_layout6 = (LinearLayout) inflater.inflate(R.layout.row_buy_detail5, null);
            TextView fiftheen=(TextView) linear_layout6.findViewById(R.id.grandtotal1);
            fiftheen.setText("Grand Total    :   ");
            TextView seventheen=(TextView) linear_layout6.findViewById(R.id.grandtotal2);
            seventheen.setText(String.valueOf(formatter.format(grandtotal)));
            container.addView(linear_layout6);
            //add generator adapter here

        } catch (Exception e) {
            Toast.makeText(reportpembelian_detail.this, "No Data", Toast.LENGTH_LONG).show();
            Log.e("ERRO",e.getMessage().toString());
        }
    }
}
