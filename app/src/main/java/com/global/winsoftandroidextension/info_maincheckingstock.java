package com.global.winsoftandroidextension;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.global.winsoftandroidextension.MainMenu.mainmenu;

import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Result;

import static com.global.winsoftandroidextension.generator.FIRST_COLUMN;
import static com.global.winsoftandroidextension.generator.SECOND_COLUMN;
import static com.global.winsoftandroidextension.generator.THIRD_COLUMN;
import static com.global.winsoftandroidextension.generator.FOURTH_COLUMN;
import static com.global.winsoftandroidextension.generator.FIFTH_COLUMN;

public class info_maincheckingstock extends Activity {

    SQLclass sqlclass;
    String sqldate1="",tempkodestock="";
    EditText dt1,d;
    ImageView iv;
    Calendar myCalendar = Calendar.getInstance();
    TextView infonamastock,infoproduk,infosatuan,infohargabeli,infoongkoskirim,infohargajual,infominimum,infomaximum;
    Button btnjual,btnbeli;
    ListView datalv,c;
    ProgressBar pbbar,pbbarjual;
    LayoutInflater inflater;
    int declarator=0;
    int data=0;
    Dolistdata dolistdata;
    generator.lvinfosales adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_maincheckingstock);

        LinearLayout mContainerView = (LinearLayout) findViewById(R.id.llhistoryjb);
         inflater= LayoutInflater.from(this);

        sqlclass = new SQLclass();
        infoproduk = (TextView) findViewById(R.id.infoproduk);
        infosatuan = (TextView) findViewById(R.id.infosatuan);
        infohargabeli = (TextView) findViewById(R.id.infohargabeli);
        infoongkoskirim = (TextView) findViewById(R.id.infoongkir);
        infohargajual = (TextView) findViewById(R.id.infohargajual);
        infominimum = (TextView) findViewById(R.id.infominstock);
        infomaximum = (TextView) findViewById(R.id.infomaxstock);
        infonamastock = (TextView) findViewById(R.id.infostockname);

        btnjual = (Button) findViewById(R.id.btnjual);
        btnbeli = (Button) findViewById(R.id.btnbeli);
        datalv = (ListView) findViewById(R.id.datalv);
        dt1=(EditText) findViewById(R.id.dt1);

        pbbar=(ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        initializedate();
        bindlistener();


        NumberFormat formatter = new DecimalFormat("###,###,###.##");

        Bundle bundle = getIntent().getExtras();

        String temp = bundle.getString("nama_stock", "");

        ResultSet result = sqlclass.querydata("select * from iamstock where nama_stock='"+temp+"'");

        try{
            while (result.next()){
                tempkodestock=result.getString("kode_Stock");
            infonamastock.setText(result.getString("nama_stock")+" ( "+result.getString("kode_Stock")+" ) ");
            ResultSet produk = sqlclass.querydata("select * from iamproduk where kode_produk='"+result.getString("kode_produk")+"'");
            while (produk.next()){
                infoproduk.setText(produk.getString("kode_produk")+" / "+produk.getString("nama_sub_produk")+" / "+produk.getString("nama_sub_produk2"));
            }
                String finale=""+ result.getString("kemas1");
                if(!result.getString("kemas2").equals("")){
                    finale=finale + " -> " + result.getString("kemas2") + " = " + result.getString("satuan2");
                }
                if(!result.getString("kemas3").equals("")){
                    finale=finale + " -> " + result.getString("kemas3") + " = " + result.getString("satuan3");
                }
                if(!result.getString("kemas4").equals("")){
                    finale=finale + " -> " + result.getString("kemas4") + " = " + result.getString("satuan4");
                }
                infosatuan.setText(finale);

                infohargabeli.setText(String.valueOf(formatter.format(result.getFloat("harga_beli"))));
                infoongkoskirim.setText(String.valueOf(formatter.format(result.getFloat("ongkos"))));

                String finale1="" + String.valueOf(formatter.format(result.getFloat("hargajual1")));
                if(!String.valueOf(formatter.format(result.getFloat("hargajual2"))).equals("")){
                    finale=finale + " / " + String.valueOf(formatter.format(result.getFloat("hargajual2")));
                }
                if(!String.valueOf(formatter.format(result.getFloat("hargajual3"))).equals("")){
                    finale=finale + " / " + String.valueOf(formatter.format(result.getFloat("hargajual3")));
                }
                infohargajual.setText(finale1);

                infominimum.setText(String.valueOf(formatter.format(result.getFloat("minimumstk"))));
                infomaximum.setText(String.valueOf(formatter.format(result.getFloat("maximumstk"))));
        }
        }catch (Exception e){
            Log.e("error",e.toString());
        }

    }

    private void initializedate(){

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        final String date = df.format(Calendar.getInstance().getTime());

        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        final String dateifempty = dateformat.format(Calendar.getInstance().getTime());

        dt1.setText(date);

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };

        dt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(info_maincheckingstock.this ,date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
        String strCurrentDate =sqldate1;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date newDate=null;
        try{
            newDate = format.parse(sqldate1);}
        catch (Exception e){
            Log.e("Erro",e.toString());
        }
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = format.format(newDate);
        sqldate1 = date;

        Docheck docheck = new Docheck();
        docheck.execute("");
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
            Toast.makeText(info_maincheckingstock.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                try {
                    generator.lvcheckstock adapter = new generator.lvcheckstock(info_maincheckingstock.this, generator.list);
                    datalv.setAdapter(adapter);
                    datalv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                            int pos = position + 1;}
                    });
                }catch (Exception e){
                    Log.e("Erro",e.toString());
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            int count=0;
            Boolean data=false;
            NumberFormat formatter = new DecimalFormat("###,###,###.##");
            generator.list.clear();
            try {
                    ResultSet result = sqlclass.querydata("SELECT LOKASI, DBO.GETQTYAWALBYTANGGALLOKASI('"+tempkodestock+"','"+sqldate1+"',NULL,KODE_LOKASI) AS SALDO, DBO.GETQTYSATUANALL('', DBO.GETQTYAWALBYTANGGALLOKASI('"+tempkodestock+"','"+sqldate1+"',NULL,KODE_LOKASI), '' ) AS SALDO_AWAL FROM IAMLOKASI");
                    String a="SELECT LOKASI, DBO.GETQTYAWALBYTANGGALLOKASI('"+tempkodestock+"','"+sqldate1+"',NULL,KODE_LOKASI) AS SALDO, DBO.GETQTYSATUANALL('', DBO.GETQTYAWALBYTANGGALLOKASI('"+tempkodestock+"','"+sqldate1+"',NULL,KODE_LOKASI), '' ) AS SALDO_AWAL FROM IAMLOKASI";

                Log.e("info", a);

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
    private void bindlistener(){
        btnjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declarator =1;
                View myView = inflater.inflate(R.layout.layout_historyjbstock, null);
                LinearLayout mContainerView = (LinearLayout) findViewById(R.id.llhistoryjb);
                mContainerView.removeAllViews();
                LinearLayout temp = (LinearLayout) inflater.inflate(R.layout.layout_historyjbstock,null);
                pbbarjual = (ProgressBar) temp.findViewById(R.id.pbbarjual);
                TextView e =(TextView) temp.findViewById(R.id.hjbtitle);
                e.setText("History Penjualan");
                pbbarjual.setVisibility(View.GONE);
                d = (EditText)  temp.findViewById(R.id.hjbhari);
                d.setText("30");
                c =(ListView) temp.findViewById(R.id.lvhjb);
                Button a = (Button) temp.findViewById(R.id.hjbbtn);
                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (d.getText().toString().equals("")){
                            AlertDialog.Builder a = new AlertDialog.Builder(info_maincheckingstock.this);
                            a.setTitle("Data kosong");
                            a.setMessage("masukan angka pada kolom history Penjualan");
                            a.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            a.show();
                        }else{
                            generator.list.clear();
                        Dolistdata a = new Dolistdata();
                        a.execute("");}
                    }
                });
                pbbarjual.setVisibility(View.GONE);
                mContainerView.addView(temp);
            }
        });

        btnbeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declarator=2;
                View myView = inflater.inflate(R.layout.layout_historyjbstock, null);
                LinearLayout mContainerView = (LinearLayout) findViewById(R.id.llhistoryjb);
                mContainerView.removeAllViews();
                LinearLayout temp = (LinearLayout) inflater.inflate(R.layout.layout_historyjbstock,null);
                pbbarjual = (ProgressBar) temp.findViewById(R.id.pbbarjual);
                TextView e =(TextView) temp.findViewById(R.id.hjbtitle);
                e.setText("History Pembelian");
                pbbarjual.setVisibility(View.GONE);
                d = (EditText)  temp.findViewById(R.id.hjbhari);
                d.setText("30");
                c =(ListView) temp.findViewById(R.id.lvhjb);
                Button a = (Button) temp.findViewById(R.id.hjbbtn);
                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (d.getText().toString().equals("")){
                            AlertDialog.Builder a = new AlertDialog.Builder(info_maincheckingstock.this);
                            a.setTitle("Data kosong");
                            a.setMessage("masukan angka pada kolom history Pembelian");
                            a.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            a.show();
                        }else{
                            generator.list.clear();
                             dolistdata = new Dolistdata();
                            dolistdata.execute("");}
                    }
                });
                mContainerView.addView(temp);
            }
        });
    }
    public class Dolistdata extends AsyncTask<String, String, String> {
        String z = "Error";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            if(adapter!=null) {
                adapter.notifyDataSetChanged();
            }
            pbbarjual.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(info_maincheckingstock.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                try {
                    adapter = new generator.lvinfosales(info_maincheckingstock.this, generator.list);
                    c.setAdapter(adapter);
                    pbbarjual.setVisibility(View.GONE);

                }catch (Exception e){
                    Log.e("Erro",e.toString());
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            int count=0;
            Boolean data=false;
            NumberFormat formatter = new DecimalFormat("###,###,###.##");

            try {
                ResultSet result = null;
                String divider="",column="";
                if (declarator==1){
                    divider="nama_customer from iamcustomer where kode_customer='";
                    column="nama_customer";
                     result = sqlclass.querydata("select a.tanggal,a.no_faktur,a.kode_customer as kode,b.qty,b.harga_jual as harga from iatpenjualan1 b join iatpenjualan a on a.no_faktur=b.no_faktur  where b.kode_stock='"+tempkodestock+"' order by a.tanggal desc");
                }
                else{
                    column="nama_supplier";
                    divider="nama_supplier from iamsupplier where kode_supplier='";
                    result = sqlclass.querydata("select  a.tanggal,a.no_faktur,a.kode_supplier as kode,b.qty,b.harga_beli as harga from iatpembelian1 b join iatpembelian a on a.no_faktur=b.no_faktur  where b.kode_stock='"+tempkodestock+"' order by a.tanggal desc");
                }

                while (result.next()){
                    HashMap<String, String> datanum = new HashMap<String, String>();
                    int temp=0;
                    temp=Integer.parseInt(d.getText().toString());
                    Log.e("ERRO",String.valueOf(temp));

                        count++;
                        datanum.put(SECOND_COLUMN,result.getString("no_faktur"));

                        String b ="";
                        ResultSet name = sqlclass.querydata("select "+divider+result.getString("kode")+"'");
                        while(name.next()){
                            datanum.put(THIRD_COLUMN ,name.getString(column));
                        }

                        b = generator.parsedate(result.getString("tanggal"),"3");
                        Log.e("ERRO",b);
                        datanum.put(FIRST_COLUMN, b );
                        String d="";
                        if(result.getInt("discount_nilai")!=0) {
                            int tempdisc = result.getInt("discout_nilai");
                            tempdisc = tempdisc / result.getInt("qty");
                             d = formatter.format(result.getInt("harga")-tempdisc);
                        }
                        else{
                            d = formatter.format(result.getInt("harga"));
                        }
                        Log.e("ERRO",d);
                        datanum.put(FIFTH_COLUMN, d);
                        datanum.put(FOURTH_COLUMN, formatter.format(result.getInt("qty")));
                        Log.e("Erro" , d.toString());
                        data=true;
                        generator.list.add(datanum);
                    if(count==temp) {
                        break;
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
}
