package com.global.winsoftandroidextension;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    SQLclass sqlclass;
    EditText edtuserid, edtpass, edtserver, edtdb, edtport,edtinstance;
    Button btnConnect,btnRefresh;
    ProgressBar pbbar;
    SharedPreferences shp;
    TextView status;

    //static instance


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlclass = new SQLclass();

        edtuserid = (EditText) findViewById(R.id.edtuserid);
        edtpass = (EditText) findViewById(R.id.edtpass);
        edtserver = (EditText) findViewById(R.id.edtserver);
        edtport = (EditText) findViewById(R.id.edtport);
        edtdb = (EditText) findViewById(R.id.edtdb);
        edtinstance =  (EditText) findViewById(R.id.edtinstance);

        status = (TextView) findViewById(R.id.status);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        shp =this.getSharedPreferences("cache", MODE_PRIVATE);

        if(generator.autoreload==""){
            String svr = shp.getString("svr","");
            String db = shp.getString("db","");
            String id = shp.getString("id","sa" );
            String pass = shp.getString("pass","12345");
            String port = shp.getString("port","");
            String instance = shp.getString("instance","");
            edtdb.setText(db);
            edtport.setText(port);
            edtpass.setText(pass);
            edtserver.setText(svr);
            edtuserid.setText(id);
            edtinstance.setText(instance);
            DoLogin doLogin = new DoLogin();
            doLogin.execute("");
        }

        if (edtserver.toString()!="" || edtport.toString()!="") {

            String svr = shp.getString("svr","");
            String db = shp.getString("db","");
            String id = shp.getString("id","" );
            String pass = shp.getString("pass","");
            String port = shp.getString("port","");
            String instance = shp.getString("instance","");
            edtdb.setText(db);
            edtport.setText(port);
            edtpass.setText(pass);
            edtserver.setText(svr);
            edtuserid.setText(id);
            edtinstance.setText(instance);
            }
        else
        {
            String z="Data Required for Auto Connect";
        }

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin doLogin = new DoLogin();
                generator.autoreload="";
                doLogin.execute("");
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                generator.autoreload=" ";
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public class DoLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String id = edtuserid.getText().toString();
        String password = edtpass.getText().toString();
        String db = edtdb.getText().toString();
        String server = edtserver.getText().toString();
        String port = edtport.getText().toString();
        String instance = edtinstance.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent i = new Intent(MainActivity.this, loginprogram.class);
                startActivity(i);
                finish();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (id.trim().equals("") || password.trim().equals(""))
                z = "Please enter User Id and Password";
            else if(server.trim().equals("")||db.trim().equals(""))
                z = "Please Check database Name and Server IP";
            else {
                if(instance.trim().equals("")) {
                    z = "Empty port or instance Filling with string";
                    instance = "";
                }
                if(port.trim().equals("")) {
                    z = "Empty port or instance Filling with string";
                    port = "";
                }
                try {
                    SharedPreferences.Editor edit = shp.edit();
                    edit.putString("svr", server);
                    edit.commit();
                    edit.putString("db", db);
                    edit.commit();
                    edit.putString("id" , id);
                    edit.putString("pass" , password);
                    edit.putString("port" , port);
                    edit.putString("instance" , instance);
                    edit.commit();
                    Connection con = sqlclass.CONN(server.trim(),db.trim(),id.trim(),password.trim(),port.trim(),instance.trim());
                    if (con != null) {
                        z = "Connect Succesfull";
                        isSuccess = true;
                    }
                    else
                    {
                        z = "Error in connection with SQL server";
                    }
                } catch (Exception con) {
                    isSuccess = false;
                    z = con.toString();
                    Log.e("ERRO",z);
                }
            }
            return z;
        }
    }
}
