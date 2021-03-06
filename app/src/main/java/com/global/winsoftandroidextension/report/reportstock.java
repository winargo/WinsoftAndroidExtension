package com.global.winsoftandroidextension.report;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.global.winsoftandroidextension.R;


public class reportstock extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportstock);

        ProgressDialog dialog = new ProgressDialog(reportstock.this);
        dialog.setMessage("Loading Data...");
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reportstock, menu);
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
}
