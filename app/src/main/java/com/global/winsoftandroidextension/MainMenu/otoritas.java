package com.global.winsoftandroidextension.MainMenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.global.winsoftandroidextension.generator.HIDDEN_COLUMN;
import static com.global.winsoftandroidextension.generator.FIFTH_COLUMN;
import static com.global.winsoftandroidextension.generator.FIRST_COLUMN;
import static com.global.winsoftandroidextension.generator.FOURTH_COLUMN;
import static com.global.winsoftandroidextension.generator.SECOND_COLUMN;
import static com.global.winsoftandroidextension.generator.THIRD_COLUMN;

public class otoritas extends AppCompatActivity {
    int count=0;
    String information="";
    String query="",tanggal="",keterangan="",id="",modul="",hiddendate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otoritas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshdata();
                Snackbar.make(view, information , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        refreshdata();
    }
    private void refreshdata(){
        Bundle extra= getIntent().getExtras();
        SQLclass sqlclass = new SQLclass();

        String info= "No Data";
        LayoutInflater inflater = LayoutInflater.from(this);
        ListView lvotor = (ListView) findViewById(R.id.lvotoritas);
        NumberFormat formatter = new DecimalFormat("###,###,###.##");
        NumberFormat floatformat = new DecimalFormat("#########.##");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yy");

        try {

            ResultSet Result = sqlclass.querydata("select * from xhistory where cek_pass=1");
            int total=0;
            generator.list.clear();
            count=0;
            while (Result.next()) {

                String strCurrentDate =Result.getString("tanggal");
                Date newDate = format.parse(strCurrentDate);
                String hiddendate=formattime.format(newDate);
                String date = format2.format(newDate);


                HashMap<String, String> datanum = new HashMap<String, String>();
                datanum.put(HIDDEN_COLUMN,date+"   "+formattime.format(newDate));
                datanum.put(FIRST_COLUMN,hiddendate);
                datanum.put(SECOND_COLUMN,Result.getString("user_id"));
                datanum.put(THIRD_COLUMN, Result.getString("modul"));
                datanum.put(FOURTH_COLUMN, Result.getString("keterangan"));
                datanum.put(FIFTH_COLUMN,date);
                count=count+1;
                generator.list.add(datanum);
            }
            lvadapterotor adapter=new lvadapterotor(this, generator.list);
            information = count + " Data retrived";
            lvotor.setAdapter(adapter);
            generator.templist=lvotor;
            lvotor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    int pos = position + 1;
                    Toast.makeText(otoritas.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
                }

            });
            //add generator adapter here

        } catch (Exception e) {
            Toast.makeText(otoritas.this, "No data", Toast.LENGTH_LONG).show();/*e.getMessage().toString()*/
        }
    }

    private void updatedata(){
        SQLclass sqlclass=new SQLclass();
        query="update xhistory set cek_pass=0,date_pass=getdate(),user_pass='"+generator.userlogin+"' where modul='"+modul+"' and keterangan='"+keterangan+"' and user_id='"+id+"' and CONVERT(varchar,tanggal,108)='"+hiddendate+"'";
        Log.e("query",query);
        try{
            int Result = sqlclass.queryexecute(query);
            if(Result>=1){
                refreshdata();
            }
            else{
                Log.e("error update","no data updated"+Result);
            }
        }
        catch (Exception e){
            Log.e("ERR",e.toString());
        }
        information="Data Refreshed";
    }

    public class lvadapterotor extends BaseAdapter {

        public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Activity activity = new Activity();
        String Datetime="";
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
        TextView hiddendate;
        Button btnotor;

        public lvadapterotor() {
            list = new ArrayList<HashMap<String, String>>();

        }

        public lvadapterotor(Activity activity, ArrayList<HashMap<String, String>> list) {
            super();
            this.activity = activity;
            this.list = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public int getViewTypeCount() {

            return getCount();
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub


            LayoutInflater inflater = activity.getLayoutInflater();

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.row_otoritas, null);

                txtFirst = (TextView) convertView.findViewById(R.id.otordate);
                txtSecond = (TextView) convertView.findViewById(R.id.otorid);
                txtThird = (TextView) convertView.findViewById(R.id.otormodul);
                txtFourth = (TextView) convertView.findViewById(R.id.otorketerangan);
                btnotor = (Button) convertView.findViewById(R.id.otorbutton);
                hiddendate =(TextView) convertView.findViewById(R.id.hiddendate);

            }

            HashMap<String, String> map = list.get(position);
            txtFirst.setText(map.get(FIRST_COLUMN));
            txtSecond.setText(map.get(SECOND_COLUMN));
            txtThird.setText(map.get(THIRD_COLUMN));
            txtFourth.setText(map.get(FOURTH_COLUMN));
            hiddendate.setText(map.get(HIDDEN_COLUMN));

            btnotor.setTag(position);
            btnotor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    int position = (Integer) arg0.getTag();
                    Log.e("ERR",String.valueOf(position));
                    generator.datafetch=String.valueOf(position);
                    Log.e("data",generator.datafetch);
                    otorbtnclick(position);
                }
            });


            return convertView;
        }
    }

    public void otorbtnclick(int position){

        View view = generator.templist.getChildAt(position);
        information="Permission Allowed";
        modul = ((TextView) view.findViewById(R.id.otormodul)).getText().toString();
        id = ((TextView) view.findViewById(R.id.otorid)).getText().toString();
        keterangan = ((TextView) view.findViewById(R.id.otorketerangan)).getText().toString();
        tanggal = ((TextView) view.findViewById(R.id.otordate)).getText().toString();
        hiddendate =((TextView) view.findViewById(R.id.otordate)).getText().toString();
        Log.e("infooo", modul+"    "+id+"   "+keterangan);
        Log.e("date",tanggal);
        updatedata();
    }
}
