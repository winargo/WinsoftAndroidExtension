package com.global.winsoftandroidextension;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;

import android.view.Menu;
import android.view.MenuItem;

import com.global.winsoftandroidextension.MainMenu.mainmenu;


public class loginprogram extends AppCompatActivity {

    SharedPreferences shp;
    Button btnlogin;
    EditText edtuserid,edtpass;
    ProgressBar pblogin;
    SQLclass sqlclass;
    MenuItem setdb;
    public String unpass(String data){
        Integer hit=0;
        String kembalian,mawal;
        hit=1;
        do {
            kembalian=data.substring(hit,hit+2);
          //  mawal=mawal+Character("124");
        }while (hit<=data.length());
        return "";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginprogram);

        setdb = (MenuItem) findViewById(R.id.setdb);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        sqlclass = new SQLclass();
        pblogin = (ProgressBar) findViewById(R.id.pblogin);
        pblogin.setVisibility(View.GONE);
        edtuserid = (AutoCompleteTextView) findViewById(R.id.edtuserid);
        edtpass = (EditText) findViewById(R.id.edtpass);
        shp =this.getSharedPreferences("cache", MODE_PRIVATE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");
            }
        });

        sqlclass.ip=shp.getString("svr","");
        sqlclass.db=shp.getString("db","");
        sqlclass.un=shp.getString("id","sa");
        sqlclass.password=shp.getString("pass","12345");
        sqlclass.port=shp.getString("port","");
    }

    public class DoLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();

        @Override
        protected void onPreExecute() {
            pblogin.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pblogin.setVisibility(View.GONE);
            Toast.makeText(loginprogram.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                SharedPreferences.Editor edit = shp.edit();
                edit.putString("UserId", userid);
                edit.commit();
                Intent i = new Intent(loginprogram.this, mainmenu.class);
                startActivity(i);
            }
        }

        ;

        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("") || password.trim().equals(""))
                z = "Please enter User Id and Password";
            else {

                try {
                    Connection con = sqlclass.CONN(sqlclass.ip,sqlclass.db,sqlclass.un,sqlclass.password,sqlclass.port,sqlclass.instance);
                    if (con == null) {
                        z = "Error in connection with SQL server";
                        Intent i = new Intent(loginprogram.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        ResultSet result = sqlclass.querydata("select * from xUser where User_id='"
                                     + userid + "' and dbo.GETPASS(password)='"+password+"'");
                        //String querydata = "select * from xUser where User_id='"
                        //        + userid + "' and password='" + password + "'";
                       // Statement stmt = con.createStatement();
                       // ResultSet rs = stmt.executeQuery(query);
                        if (result.next()) {
                            generator.userlogin=userid;
                            z = "Login successfull";
                            isSuccess = true;
                        }
                        if (z!="Login successfull"){
                            z = "Invalid Credentials";
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.toString();
                }
                }
            return z;
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loginprogram, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.setdb) {
            Intent i = new Intent(loginprogram.this, MainActivity.class);
            generator.autoreload=" ";
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class userlogin {

    }
}
