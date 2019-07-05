package com.example.daysatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daysatu.restAPI.RestProcess;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    String var_fullname, var_cell_phone, var_email, var_password, var_type = "save";

    private RestProcess rest_bebas;

    ArrayList<HashMap<String, String>> arraySubmit = new ArrayList<HashMap<String, String>>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rest_bebas = new RestProcess();


        final EditText edtFullname = (EditText) findViewById(R.id.edt_fullname);
        final EditText edtCellphone = (EditText) findViewById(R.id.edt_cellphone);
        final EditText edtEmail = (EditText) findViewById(R.id.edt_email);
        final EditText edtPassword = (EditText) findViewById(R.id.edt_password);
        Button btnSubmit = (Button) findViewById(R.id.btn_submit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                var_fullname = edtFullname.getText().toString();
                var_cell_phone = edtCellphone.getText().toString();
                var_email = edtEmail.getText().toString();
                var_password = edtPassword.getText().toString();


                if (var_fullname.length() == 0) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.fullname_string), Toast.LENGTH_SHORT).show();
                } else if (var_cell_phone.length() == 0) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.fullname_string), Toast.LENGTH_SHORT).show();
                } else if (var_cell_phone.length() == 0) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.fullname_string), Toast.LENGTH_SHORT).show();
                } else if (var_cell_phone.length() == 0) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.fullname_string), Toast.LENGTH_SHORT).show();
                } else {





//
//            } else if (var_username.length() < 10) {
//                    Toast.makeText(LoginAcivity.this, "username tidak boleh kurang dari 10 karakter", Toast.LENGTH_SHORT).show();
//                } else if (var_password.length() == 0) {
//                    Toast.makeText(LoginAcivity.this, "Isi Password gan", Toast.LENGTH_SHORT).show();
//                } else if (var_password.length() < 6) {
//                    Toast.makeText(LoginAcivity.this, "password tidak boleh kurang dari 6 karakter", Toast.LENGTH_SHORT).show();
//                } else {


//                    Intent intent = new Intent(LoginAcivity.this, Main2Activity.class);
//                    startActivity(intent);
//                    finish();
                    submitProcess(v);
                }
            }
        });
    }


    private void submitProcess(final View view) {

        HashMap<String, String> data_api = new HashMap<String, String>();
        data_api = rest_bebas.apiSetting();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String base_url;

        base_url = data_api.get("str_ws_addr") + "/api/training/register/format/json";
        params.put("var_full_name", var_fullname);
        params.put("var_cell_phone", var_cell_phone);
        params.put("var_email", var_email);
        params.put("var_password", var_password);
        params.put("var_type", var_type);



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
                    displayRegister(view, resp_content);
                } catch (Throwable t) {
                    Toast.makeText(RegisterActivity.this, "koneksi GAGAL BRO 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(RegisterActivity.this, "Koneksi gagal om 2", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void displayRegister(View view, String resp_content) {
        try {
            arraySubmit = rest_bebas.getJsonData(resp_content);

            if (arraySubmit.get(0).get("var_result").equals("1")) {

                Intent main_intent = new Intent( RegisterActivity.this, LoginAcivity.class);
                startActivity(main_intent);
                finish();

            }else if(arraySubmit.get(0).get("var_result").equals("0")) {
                Toast.makeText(RegisterActivity.this, "Koneksi gagal bro 3", Toast.LENGTH_SHORT).show();
            }


        }catch (JSONException e) {
            Toast.makeText(RegisterActivity.this, "koneksi gagal om 4", Toast.LENGTH_SHORT).show();
        }
    }
}
