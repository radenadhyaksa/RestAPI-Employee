package com.example.daysatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class EmployeeActivity extends AppCompatActivity {

    //API proses
    private RestProcess rest_bebas;

    //variable listview
    ListView lvDataKaryawan;

    //deklarasi class adapter
    ListAdapter adapter;

    ArrayList<HashMap<String, String>> dataKaryawan_arrayList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        rest_bebas = new RestProcess();

        lvDataKaryawan = (ListView) findViewById(R.id.lvDataKaryawan);
        getDataKaryawan(null);
    }


    private void getDataKaryawan(final View view) {

        HashMap<String, String> data_api = new HashMap<String, String>();
        data_api = rest_bebas.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        String base_url;

        base_url = data_api.get("str_ws_addr") + "/api/training/employee/format/json";

        client.setBasicAuth(data_api.get("str_ws_user"), data_api.get("str_ws_pass"));
        client.post(base_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp_content = null;

                try {
                    resp_content = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    displayDataKaryawan(view, resp_content);
                } catch (Throwable t) {
                    Toast.makeText(EmployeeActivity.this, "koneksi GAGAL BRO 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(EmployeeActivity.this, "Koneksi gagal bro 2", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayDataKaryawan(View view, String resp_content) {

        try {
            dataKaryawan_arrayList = rest_bebas.getJsonData(resp_content);

             if (dataKaryawan_arrayList.get(0).get("var_result").equals("1")) {

                 dataKaryawan_arrayList.remove(0);
                 adapter = new ListAdapter(EmployeeActivity.this, dataKaryawan_arrayList, 1);
                 lvDataKaryawan.setAdapter(adapter);
                 Toast.makeText(EmployeeActivity.this, "koneksi Berhasil gan1", Toast.LENGTH_LONG).show();

            }else {
                 Toast.makeText(EmployeeActivity.this,"koneksi gagal gan2", Toast.LENGTH_LONG).show();
             }


        }catch (JSONException e) {
            Toast.makeText(EmployeeActivity.this, "koneksi gagal gan3", Toast.LENGTH_LONG).show();
        }
    }
}
