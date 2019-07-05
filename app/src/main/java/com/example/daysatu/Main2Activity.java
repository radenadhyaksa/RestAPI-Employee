package com.example.daysatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.daysatu.menu.AboutActivity;
import com.example.daysatu.menu.EmployeeActivity;
import com.example.daysatu.menu.KantorActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId()==R.id.data_kantor){
            startActivity(new Intent(this, KantorActivity.class));
        }

        else if (item.getItemId()==R.id.data_karyawan){
            startActivity(new Intent(this, EmployeeActivity.class));

        }

        else if (item.getItemId()==R.id.about_apps){
            startActivity(new Intent(this, AboutActivity.class));
        }
        return true;
    }
}


