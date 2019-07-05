package com.example.daysatu.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.daysatu.R;
import com.example.daysatu.RestProcess;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class AboutActivity extends AppCompatActivity {

    //Declare RestAPI
    private RestProcess rest_bebas;

    //Local variable
    private WebView webView;

    //eclare Array List
    ArrayList<HashMap<String, String>> dataAbout_arrayList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        rest_bebas = new RestProcess();
        webView = (WebView) findViewById(R.id.wvAbout);
        getAbout(null);

    }


    private void getAbout(final View view) {

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
                    displayAbout(view, resp_content);
                } catch (Throwable t) {
                    Toast.makeText(AboutActivity.this, "koneksi GAGAL BRO 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AboutActivity.this, "koneksi gagal bro 2", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void displayAbout(final View view, String resp_content) {
        try {
            dataAbout_arrayList = rest_bebas.getJsonData(resp_content);

            if (dataAbout_arrayList.get(0).get("var_result").equals("1")) {

                String strHtml = dataAbout_arrayList.get(1).get("about_apps");
                webView.loadData(strHtml, "text/html", null);

//                dataAbout_arrayList.remove(0);
//                adapter = new ListAdapter(EmployeeActivity.this, dataAbout_arrayList, 1);
//                webView.setAdapter(adapter);
                Toast.makeText(AboutActivity.this, "koneksi Berhasil gan1", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(AboutActivity.this,"koneksi gagal gan2", Toast.LENGTH_LONG).show();
            }


        }catch (JSONException e) {
            Toast.makeText(AboutActivity.this, "koneksi gagal gan3", Toast.LENGTH_LONG).show();
        }
    }
}
