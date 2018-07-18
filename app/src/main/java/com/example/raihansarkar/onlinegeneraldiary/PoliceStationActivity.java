package com.example.raihansarkar.onlinegeneraldiary;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PoliceStationActivity extends AppCompatActivity {
    private ListView stationListView;
    private String[] stationNames;
    private String[] policeStationNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station);
        stationListView = (ListView) findViewById(R.id.stationListView);

        stationNames = getResources().getStringArray(R.array.stationName);
        policeStationNumbers = getResources().getStringArray(R.array.policeStationNumber);

        final PoliceListAdapter adapter = new PoliceListAdapter(this, getStation());
        stationListView.setAdapter(adapter);

        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String value = stationNames[position];


                final AlertDialog.Builder bld = new AlertDialog.Builder(PoliceStationActivity.this);
                final View pstDioView = getLayoutInflater().inflate(R.layout.dialog_layout_police_profile, null);
                final TextView textPstDio = (TextView) pstDioView.findViewById(R.id.pstDioText);
                Button btnCall = (Button) pstDioView.findViewById(R.id.callBtn);

                textPstDio.setText(value);
                btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = policeStationNumbers[position];
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+number));
                        if (ActivityCompat.checkSelfPermission(PoliceStationActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);
                    }
                });

                bld.setView(pstDioView);
                AlertDialog alert=bld.create();
                alert.show();
            }
        });



    }

    private ArrayList<PoliceStation> getStation(){
        ArrayList<PoliceStation> list=new ArrayList<PoliceStation>();
        PoliceStation policeStation;
        for (int i=0; i<stationNames.length; i++){
            policeStation=new PoliceStation(stationNames[i], policeStationNumbers[i]);
            list.add(policeStation);
        }
        return list;
    }

}
