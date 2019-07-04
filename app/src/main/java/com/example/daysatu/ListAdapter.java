package com.example.daysatu;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ListAdapter  extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private int fragment_position = 0;
    private static LayoutInflater inflater = null;
    private String PACKAGE_NAME;

    public ListAdapter(Activity a, ArrayList<HashMap<String, String>> d, int fragment_pos) {
        data = d;
        activity = a;
        fragment_position = fragment_pos;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PACKAGE_NAME = activity.getLocalClassName();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        switch (fragment_position) {

            case 1:
                if (convertView == null)
                    vi = inflater.inflate(R.layout.lv_employee, null);

                TextView tvEmployeeName = (TextView) vi.findViewById(R.id.tvEmployeeName);
                TextView tvEmployeeNumber = (TextView) vi.findViewById(R.id.tvEmployeeNumber);
                TextView tvEmployeeAdress = (TextView) vi.findViewById(R.id.tvEmployeeAdress);
                TextView tvEmployeeGender = (TextView) vi.findViewById(R.id.tvEmployeeGender);
                ImageView image = vi.findViewById(R.id.imgEmployee);

                HashMap<String, String> empList = new HashMap<String, String>();

                        empList = data.get(position);

                tvEmployeeName.setText(empList.get("employee_name"));
                tvEmployeeNumber.setText(empList.get("nomor_induk_pegawai"));
                tvEmployeeAdress.setText(empList.get("address"));
                tvEmployeeGender.setText(empList.get("gender"));
                Picasso.get().load(empList.get("base_url")).into(image);

                break;


            case 2:
                if (convertView == null)
                    vi = inflater.inflate(R.layout.lv_kantor, null);

                TextView tvKantorName = (TextView) vi.findViewById(R.id.tvKantorName);
                TextView tvKantorAdress = (TextView) vi.findViewById(R.id.tvKantorAdress);
                TextView tvCellphone = (TextView) vi.findViewById(R.id.tvCellphone);
                ImageView imageKantor = vi.findViewById(R.id.imgKantor);

                HashMap<String, String> empListKantor = new HashMap<String, String>();

                empListKantor = data.get(position);

                tvKantorName.setText(empListKantor.get("office_name"));
                tvKantorAdress.setText(empListKantor.get("office_address"));
                tvCellphone.setText(empListKantor.get("cell_phone"));
                Picasso.get().load(empListKantor.get("base_url")).into(imageKantor);

                break;

            default:

                break;
        }


        return vi;
    }
}

