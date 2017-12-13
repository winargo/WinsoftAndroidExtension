package com.global.winsoftandroidextension;

import android.app.Activity;
import android.app.ListActivity;
import android.widget.EditText;
import android.content.Context;
import android.util.Log;
import android.text.Editable;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.lang.Object;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;



/**
 * Created by Riandy on 7/18/2017.
 */
public class generator extends ListActivity {
    public static ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();

    public static String dt1 = new String();
    public static String dt2 = new String();

    public static String userlogin = "";

    public static String query = new String();
    public static int initializer = 0;
    public static String datafetch="";
    public static ListView templist=null;

    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";
    public static final String FOURTH_COLUMN = "Fourth";
    public static final String FIFTH_COLUMN = "Fifth";
    public static final String SIX = "SIX";
    public static final String SEVEN = "SEVEN";
    public static final String EIGHT = "EIGHT";
    public static final String NINE = "NINE";
    public static final String TEN = "TEN";
    public static final String ELEVEN = "ELEVEN";
    public static final String TWELVE = "TWELVE";
    public static final String THIRTHEEN = "THIRTHEEN";
    public static final String FOURTHEEN = "FOURTHEEN";
    public static final String FIFTHEEN = "FIFTHEEN";
    public static final String HIDDEN_COLUMN = "HIDDEN";

    public static String autoreload = "";


    public generator() {
    }
    //----------------------------date formatter---------------------------------//
    public static String parsedate(String date,String data){

        if(data=="1"){
            Date newDate=null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try{
                newDate = format.parse(date);
            }catch (Exception e){
                Log.e("ERRO", e.toString());
            }
            format = new SimpleDateFormat("dd-MM-yyyy");
            String tempdate = format.format(newDate);
            date = tempdate;
        }

        if(data=="2"){
            Date newDate=null;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try{
                newDate = format.parse(date);
            }catch (Exception e){
                Log.e("ERRO", e.toString());
            }
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String tempdate = format.format(newDate);
            date = tempdate;
        }
        return date;
    }

    //----------------------------date formatter---------------------------------//

    //----------------------------------penjualan rekap---------------------//
    public static class lvadapter extends BaseAdapter {

        public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Activity activity = new Activity();
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
        TextView txtFifth;

        public lvadapter() {
            list = new ArrayList<HashMap<String, String>>();

        }

        public lvadapter(Activity activity, ArrayList<HashMap<String, String>> list) {
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

                convertView = inflater.inflate(R.layout.row_sales, null);

                txtThird = (TextView) convertView.findViewById(R.id.sales);
                txtFourth = (TextView) convertView.findViewById(R.id.customer);
                txtFifth = (TextView) convertView.findViewById(R.id.jumlah);
                txtFirst = (TextView) convertView.findViewById(R.id.faktur);
                //        txtSecond = (TextView) convertView.findViewById(R.id.tanggal);

            }

            HashMap<String, String> map = list.get(position);
            txtFirst.setText(map.get(FIRST_COLUMN));
         //   txtSecond.setText(map.get(SECOND_COLUMN));
            txtThird.setText(map.get(THIRD_COLUMN));
            txtFourth.setText(map.get(FOURTH_COLUMN));
            txtFifth.setText(map.get(FIFTH_COLUMN));

            return convertView;
        }
    }
    //----------------------------------penjualan rekap---------------------//


//-------------------------Otoritas---------------------//


//-------------------------Otoritas---------------------//

    //----------------------------------pembelian rekap---------------------//
    public static class lvbuy extends BaseAdapter {

        public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Activity activity = new Activity();
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
        TextView txtFifth;

        public lvbuy() {
            list = new ArrayList<HashMap<String, String>>();

        }

        public lvbuy(Activity activity, ArrayList<HashMap<String, String>> list) {
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
                convertView = inflater.inflate(R.layout.row_buy, null);

                txtFirst = (TextView) convertView.findViewById(R.id.faktur);
                txtSecond = (TextView) convertView.findViewById(R.id.tanggal);
                txtThird = (TextView) convertView.findViewById(R.id.supplier);
                txtFourth = (TextView) convertView.findViewById(R.id.gudang);
                txtFifth = (TextView) convertView.findViewById(R.id.jumlah);

            }

            HashMap<String, String> map = list.get(position);
            txtFirst.setText(map.get(FIRST_COLUMN));
            txtSecond.setText(map.get(SECOND_COLUMN));
            txtThird.setText(map.get(THIRD_COLUMN));
            txtFourth.setText(map.get(FOURTH_COLUMN));
            txtFifth.setText(map.get(FIFTH_COLUMN));

            return convertView;
        }
    }
    //----------------------------------pembelian rekap---------------------//

    //----------------------------------rekap Omset Salesman---------------------//

    public static class rekapomset extends BaseAdapter {

        public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Activity activity = new Activity();
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
        TextView txtFifth;
        TextView six;

        public rekapomset() {
            list = new ArrayList<HashMap<String, String>>();

        }

        public rekapomset(Activity activity, ArrayList<HashMap<String, String>> list) {
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

                convertView = inflater.inflate(R.layout.row_salesman_rekapomset, null);
                txtFirst = (TextView) convertView.findViewById(R.id.salesmancode);
                txtSecond = (TextView) convertView.findViewById(R.id.salesmanname);
                txtThird = (TextView) convertView.findViewById(R.id.salesmansales);
                txtFourth = (TextView) convertView.findViewById(R.id.salesmanretour);
                txtFifth = (TextView) convertView.findViewById(R.id.salesmanomzet);
                six = (TextView) convertView.findViewById(R.id.number);

            }

            HashMap<String, String> map = list.get(position);
            txtFirst.setText(map.get(FIRST_COLUMN));
            txtSecond.setText(map.get(SECOND_COLUMN));
            txtThird.setText(map.get(THIRD_COLUMN));
            txtFourth.setText(map.get(FOURTH_COLUMN));
            txtFifth.setText(map.get(FIFTH_COLUMN));
            six.setText(map.get(SIX));

            return convertView;
        }
    }

    //----------------------------------Rekap Omset Salesman---------------------//

//------------------------------------Dialog seleksi customer-----unused---------------//

    static public class filterListDialog extends Dialog implements OnClickListener {

        private ListView list;
        private EditText filterText = null;
        ArrayAdapter<String> adapter = null;
        private static final String TAG = "CityList";

        public filterListDialog(Context context, ArrayList<String> cityList) {
            super(context);

            /** Design the dialog in main.xml file */
            setContentView(R.layout.dialog_searchandfilter);
            this.setTitle("Select Customer");
            filterText = (EditText) findViewById(R.id.EditBox);
            filterText.addTextChangedListener(filterTextWatcher);
            list = (ListView) findViewById(R.id.lvsnf);
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, cityList);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Log.d(TAG, "Selected Item is = "+list.getItemAtPosition(position));
                }
            });
        }
        @Override
        public void onClick(View v) {

        }
        private TextWatcher filterTextWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                adapter.getFilter().filter(s);
            }
        };
        @Override
        public void onStop(){
            filterText.removeTextChangedListener(filterTextWatcher);
        }}

//------------------------------------DIalog seleksi customer-------unused-------------//

    //----------------------------------checking stock---------------------//
    public static class lvcheckstock extends BaseAdapter {

        public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Activity activity = new Activity();
        TextView txtFirst;
        TextView txtSecond;

        public lvcheckstock() {
            list = new ArrayList<HashMap<String, String>>();

        }

        public lvcheckstock(Activity activity, ArrayList<HashMap<String, String>> list) {
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

                convertView = inflater.inflate(R.layout.row_checkingstock, null);

                txtFirst = (TextView) convertView.findViewById(R.id.location);
                txtSecond = (TextView) convertView.findViewById(R.id.qty);

            }

            HashMap<String, String> map = list.get(position);
            txtFirst.setText(map.get(FIRST_COLUMN));
            txtSecond.setText(map.get(SECOND_COLUMN));

            return convertView;
        }
    }

//----------------------------------Checking stock---------------------//
    //----------------------------------history jb---------------------//
public static class lvinfosales extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    Activity activity = new Activity();
    TextView txtFirst,txtSecond,txtThird;

    public lvinfosales() {
        list = new ArrayList<HashMap<String, String>>();

    }

    public lvinfosales(Activity activity, ArrayList<HashMap<String, String>> list) {
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

            convertView = inflater.inflate(R.layout.row_listviewhistoryjb, null);

            txtFirst = (TextView) convertView.findViewById(R.id.tanggal);

            txtSecond = (TextView) convertView.findViewById(R.id.faktur);
            txtThird = (TextView) convertView.findViewById(R.id.hjbjumlah);
        }

        HashMap<String, String> map = list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        return convertView;
    }
}
    //----------------------------------history jb---------------------//

}