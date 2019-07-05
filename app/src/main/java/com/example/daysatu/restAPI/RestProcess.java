package com.example.daysatu.restAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RestProcess {

    public HashMap apiSetting() {
        HashMap<String, String> apiData = new HashMap<String, String>();
        apiData.put("str_ws_addr","http://caiman.multiintisarana.id:2095/ws_training");
        apiData.put("str_ws_user", "apitraining");
        apiData.put("str_ws_pass","password");
        return apiData;
    }

    public ArrayList<HashMap<String, String>> getJsonData(String resp_content) throws JSONException {
        ArrayList<HashMap<String, String>> arrayReturn = new ArrayList<HashMap<String, String>>();
        JSONObject obj_json = null;

        int i;
        String var_result,var_result_flag, var_message;
        HashMap<String, String> map_gen;

        try {
            obj_json = new JSONObject(resp_content);
            var_result_flag = obj_json.get("var_result").toString();
            var_message = obj_json.get("var_message").toString();
            var_result = obj_json.get("result").toString();
            map_gen = new HashMap<String, String>();
            map_gen.put("var_result",var_result_flag);
            map_gen.put("var_message",var_message);
            arrayReturn.add(map_gen);
            if (var_result_flag.equals("1")) {
                JSONArray result_array = new JSONArray(var_result);

                for (i = 0; i < result_array.length(); i++) {
                    JSONObject obj = result_array.getJSONObject(i);

                    map_gen = new HashMap<String, String>();

                    for(int j=0;j<obj.length();j++){
                        String json_key = obj.names().getString(j);
                        String json_value = obj.getString(json_key);
                        map_gen.put(json_key,json_value);
                    }

                    arrayReturn.add(map_gen);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayReturn;
    }
}


