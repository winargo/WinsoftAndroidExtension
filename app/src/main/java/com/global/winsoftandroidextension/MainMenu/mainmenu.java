package com.global.winsoftandroidextension.MainMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.LinearLayout;

import com.global.winsoftandroidextension.MainActivity;
import com.global.winsoftandroidextension.R;
import com.global.winsoftandroidextension.SQLclass;
import com.global.winsoftandroidextension.generator;

import java.sql.ResultSet;


public class mainmenu extends AppCompatActivity {

    LinearLayout btnjual,btnbeli,btnstock,btnhutang,btnpiutang,btnsetting,btnotor,btnsalesman,btncs;
    ImageButton ibsell,ibbuy,ibhutang,ibpiutang,ibstock,ibotor ,ibsalesman,ibcs;
    SQLclass sqlclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        sqlclass= new SQLclass();

        btnbeli = (LinearLayout) findViewById(R.id.buy);
        btnjual = (LinearLayout) findViewById(R.id.sell);
        btnstock = (LinearLayout) findViewById(R.id.stock);
        btnhutang = (LinearLayout) findViewById(R.id.hutang);
        btnpiutang = (LinearLayout) findViewById(R.id.piutang);
        btnotor=(LinearLayout) findViewById(R.id.otor);
        btnsalesman=(LinearLayout) findViewById(R.id.salesman);
        btncs=(LinearLayout) findViewById(R.id.checkstock);

        ibbuy=(ImageButton) findViewById(R.id.ibbuy);
        ibsell=(ImageButton) findViewById(R.id.ibsell);
        ibhutang=(ImageButton) findViewById(R.id.ibhutang);
        ibpiutang=(ImageButton) findViewById(R.id.ibpiutang);
        ibstock=(ImageButton) findViewById(R.id.ibstock);
        ibotor=(ImageButton) findViewById(R.id.ibotor);
        ibsalesman=(ImageButton) findViewById(R.id.ibsalesman);
        ibcs=(ImageButton) findViewById(R.id.ibcheckstock);

        btnbeli.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainpembelian.class);
                startActivity(i);
            }
        });
        ibbuy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainpembelian.class);
                startActivity(i);
            }
        });
        btnjual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    int count = 0;
                    ResultSet check = sqlclass.querydata("select * from xgroupuser a join xgroupperm b on a.group_id=b.group_id where b.perm_id='118' and a.user_id='" + generator.userlogin + "'");
                    if (check.next()) {
                        count++;
                    }
                    Log.e("ERROR", String.valueOf(count));
                    if (count >= 1) {
                        Intent i = new Intent(mainmenu.this, mainpenjualan.class);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder a = new AlertDialog.Builder(mainmenu.this);
                        a.setTitle("Access Denied");
                        a.setMessage("Anda tidak memiliki otoritas");
                        a.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        a.show();
                    }
                } catch (Exception e) {
                    Log.e("ERRO ", e.toString());
                }
            }}
        );
        ibsell.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    int count = 0;
                    ResultSet check = sqlclass.querydata("select * from xgroupuser a join xgroupperm b on a.group_id=b.group_id where b.perm_id='118' and a.user_id='" + generator.userlogin + "'");
                    if (check.next()) {
                        count++;
                    }
                    Log.e("ERROR", String.valueOf(count));
                    if (count >= 1) {
                        Intent i = new Intent(mainmenu.this, mainpenjualan.class);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder a = new AlertDialog.Builder(mainmenu.this);
                        a.setTitle("Access Denied");
                        a.setMessage("Anda tidak memiliki otoritas");
                        a.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        a.show();
                    }
                } catch (Exception e) {
                    Log.e("ERRO ", e.toString());
                }
            }}
        );
        btnstock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainstock.class);
                startActivity(i);
            }
        });
        ibstock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainstock.class);
                startActivity(i);
            }
        });
        btnhutang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainhutang.class);
                startActivity(i);
            }
        });
        ibhutang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainhutang.class);
                startActivity(i);
            }
        });
        btnpiutang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainpiutang.class);
                startActivity(i);
            }
        });
        ibpiutang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainpiutang.class);
                startActivity(i);
            }
        });
        btnotor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    int count = 0;
                    ResultSet check = sqlclass.querydata("select * from xgroupuser a join xgroupperm b on a.group_id=b.group_id where b.perm_id='355' and a.user_id='"+ generator.userlogin +"'");
                    if (check.next()) {
                        count++;
                    }
                    Log.e("ERROR", String.valueOf(count));
                    if (count >= 1) {
                        Intent i = new Intent(mainmenu.this, otoritas.class);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder a = new AlertDialog.Builder(mainmenu.this);
                        a.setTitle("Access Denied");
                        a.setMessage("Anda tidak memiliki otoritas");
                        a.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        a.show();
                    }
                } catch (Exception e) {
                    Log.e("ERRO ", e.toString());
                }
            }
        });

        ibotor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    int count=0;
                    ResultSet check = sqlclass.querydata("select * from xgroupuser a join xgroupperm b on a.group_id=b.group_id where b.perm_id='355' and a.user_id='"+generator.userlogin+"'");
                    if (check.next()){
                        count++;
                    }
                    Log.e("ERROR", String.valueOf(count));
                    if(count>=1){
                        Intent i = new Intent(mainmenu.this, otoritas.class);
                        startActivity(i);
                    }
                    else
                    {
                        AlertDialog.Builder a= new AlertDialog.Builder(mainmenu.this);
                        a.setTitle("Access Denied");
                        a.setMessage("Anda tidak memiliki otoritas");
                        a.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        a.show();
                    }
                }catch (Exception e){
                    Log.e("ERRO ", e.toString());
                }
            }
        });
        btnsalesman.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainsalesman.class);
                startActivity(i);
            }
        });
        ibsalesman.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, mainsalesman.class);
                startActivity(i);
            }
        });
        btncs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, maincheckingstock.class);
                startActivity(i);
            }
        });
        ibcs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(mainmenu.this, maincheckingstock.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainmenu, menu);
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
            Intent i = new Intent(mainmenu.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
