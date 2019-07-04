package com.example.daysatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.nio.channels.AsynchronousChannel;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class LoginAcivity extends AppCompatActivity {

    String var_username, var_password;

    private RestProcess rest_bebas;

    ArrayList<HashMap<String, String>> arrayLogin = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);

        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAcivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

        getSupportActionBar().hide();

        rest_bebas = new RestProcess();


        final EditText edtUsername = (EditText) findViewById(R.id.username);
        final EditText edtPassword = (EditText) findViewById(R.id.password);
        Button btnLogin = (Button) findViewById(R.id.login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                var_username = edtUsername.getText().toString();
                var_password = edtPassword.getText().toString();

                if (var_username.length() == 0) {
                    Toast.makeText(LoginAcivity.this, getString(R.string.username_string), Toast.LENGTH_SHORT).show();

                } else if (var_username.length() < 10) {
                    Toast.makeText(LoginAcivity.this, "username tidak boleh kurang dari 10 karakter", Toast.LENGTH_SHORT).show();
                } else if (var_password.length() == 0) {
                    Toast.makeText(LoginAcivity.this, "Isi Password gan", Toast.LENGTH_SHORT).show();
                } else if (var_password.length() < 6) {
                    Toast.makeText(LoginAcivity.this, "password tidak boleh kurang dari 6 karakter", Toast.LENGTH_SHORT).show();
                } else {


//                    Intent intent = new Intent(LoginAcivity.this, Main2Activity.class);
//                    startActivity(intent);
//                    finish();
                    loginProcess(v);
                }
            }
        });
    }




    private void loginProcess(final View view) {

        HashMap<String, String> data_api = new HashMap<String, String>();
        data_api = rest_bebas.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String base_url;

        base_url = data_api.get("str_ws_addr") + "/api/training/auth/format/json";
        params.put("var_cell_phone", var_username);
        params.put("var_password", var_password);


        client.setBasicAuth(data_api.get("str_ws_user"), data_api.get("str_ws_pass"));
        client.post(base_url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp_content = null;

                try {
                    resp_content = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    displayLogin(view, resp_content);
                } catch (Throwable t) {
                    Toast.makeText(LoginAcivity.this, "koneksi GAGAL BRO 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LoginAcivity.this, "Koneksi gagal om 2", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void displayLogin(View view, String resp_content) {
        try {
            arrayLogin = rest_bebas.getJsonData(resp_content);

            if (arrayLogin.get(0).get("var_result").equals("1")) {

                Intent main_intent = new Intent( LoginAcivity.this, Main2Activity.class);
                startActivity(main_intent);
                finish();

            }else if(arrayLogin.get(0).get("var_result").equals("0")) {
                Toast.makeText(LoginAcivity.this, "Koneksi gagal bro 3", Toast.LENGTH_SHORT).show();
            }


        }catch (JSONException e) {
            Toast.makeText(LoginAcivity.this, "koneksi gagal om 4", Toast.LENGTH_SHORT).show();
        }
    }
}