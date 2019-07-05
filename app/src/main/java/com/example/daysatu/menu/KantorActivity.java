package com.example.daysatu.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daysatu.ListAdapter;
import com.example.daysatu.R;
import com.example.daysatu.RestProcess;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class KantorActivity extends AppCompatActivity {

    //API proses
    private RestProcess rest_bebas;

    //variable listview
    ListView lvDataKantor;

    //deklarasi class adapter
    ListAdapter adapter;

    ArrayList<HashMap<String, String>> dataKantor_arrayList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kantor);

        rest_bebas = new RestProcess();

        lvDataKantor = (ListView) findViewById(R.id.lvDataKantor);
        getDataKantor(null);
    }


    private void getDataKantor(final View view) {

        HashMap<String, String> data_api = new HashMap<String, String>();
        data_api = rest_bebas.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        String base_url;

        base_url = data_api.get("str_ws_addr") + "/api/training/office/format/json";

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
                    displayDataKantor(view, resp_content);
                } catch (Throwable t) {
                    Toast.makeText(KantorActivity.this, "koneksi GAGAL BRO 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(KantorActivity.this, "Koneksi gagal bro 2", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayDataKantor(View view, String resp_content) {

        try {
            dataKantor_arrayList = rest_bebas.getJsonData(resp_content);

            if (dataKantor_arrayList.get(0).get("var_result").equals("1")) {

                dataKantor_arrayList.remove(0);
                adapter = new ListAdapter(KantorActivity.this, dataKantor_arrayList, 2);
                lvDataKantor.setAdapter(adapter);
                Toast.makeText(KantorActivity.this, "koneksi Berhasil gan1", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(KantorActivity.this,"koneksi gagal gan2", Toast.LENGTH_LONG).show();
            }


        }catch (JSONException e) {
            Toast.makeText(KantorActivity.this, "koneksi gagal gan3", Toast.LENGTH_LONG).show();
        }
    }
}
