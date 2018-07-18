package com.example.raihansarkar.onlinegeneraldiary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdatePage extends AppCompatActivity {

    private ListView listView;
    private List<Item> listItem;
    private BaseAdapter adapter;
    private Button refreshBtn;
    FirebaseFirestore db;
    String TAG = "firebaseChack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page);

        listView = (ListView) findViewById(R.id.listview);

        db = FirebaseFirestore.getInstance();

        listItem = new ArrayList<Item>();

        adapter = new BaseAdapter() {
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

            @Override
            public int getCount() {
                return listItem.size();
            }

            @Override
            public Object getItem(int position) {
                return listItem.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.child_layout, null);
                }
                TextView itemNumber = (TextView) convertView.findViewById(R.id.tvUserGDNumberId);
                TextView itemForm = (TextView) convertView.findViewById(R.id.tvGdFormId);

                itemNumber.setText(listItem.get(position).getGd_number());
                itemForm.setText(listItem.get(position).getGd_form());
                return convertView;
            }
        };

        listView.setAdapter(adapter);
        refreshDataView();
        adapter.notifyDataSetChanged();

        final DatabaseHelper helper = new DatabaseHelper(getBaseContext());

        Log.d("updatePage", "sucess");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                Log.d("updatePage", "onItemClick");

                final AlertDialog.Builder bld = new AlertDialog.Builder(UpdatePage.this);
                final View updateView = getLayoutInflater().inflate(R.layout.update_view, null);

                final EditText gdNumber = (EditText) updateView.findViewById(R.id.updateGdNumber);
                Button btnUpdate = (Button) updateView.findViewById(R.id.updateBtn);
                Button btnDelete = (Button) updateView.findViewById(R.id.deleteBtn);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String sEmail = listItem.get(i).getGd_email();



                        //firebase cloud store update part-------------------
                        String sGdNumber = gdNumber.getText().toString();

                        DocumentReference gdRef = db.collection("users").document(listItem.get(i).getIdString());
                        gdRef.update("gd_number", sGdNumber)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(UpdatePage.this, "Updated Successfully",
                                                Toast.LENGTH_SHORT).show();
                                        refreshDataView();

                                    }
                                });



                        //send email part----------------
                        Intent emailIntent = null, chooser = null;
                        emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        String[] to = {sEmail};
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "GD Number");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your GD Successfully Granted, Your GD Number is: " + sGdNumber);
                        emailIntent.setType("massage/rfc822");
                        chooser = Intent.createChooser(emailIntent, "Send Email");
                        startActivity(chooser);

                    }
                });
                //delete button-----------
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("users").document(listItem.get(i).getIdString())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(UpdatePage.this, "Delete Successfully",
                                                Toast.LENGTH_SHORT).show();
                                        refreshDataView();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UpdatePage.this, "Can't delete this file",
                                                Toast.LENGTH_SHORT).show();
                                        refreshDataView();

                                    }
                                });
                    }
                });


                bld.setView(updateView);
                AlertDialog alert = bld.create();
                alert.show();
            }
        });


        //pull to refresh ----------
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDataView(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

    }


    public void refreshDataView() {
        listItem.removeAll(listItem);

        Intent intent = getIntent();
        final String user = intent.getStringExtra("Username");
        Toast.makeText(UpdatePage.this, "Police Station Name: " + user, Toast.LENGTH_LONG).show();
        Log.d("SelectedStation", "After Intent: " + user);

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String idString=document.getId();
                                String form = "" + document.getString("gd_form");
                                String mobile = "" + document.getString("mobile_number");
                                String policeStation = "" + document.getString("police_station");
                                String email = "" + document.getString("email");
                                String date = "" + document.getString("date");
                                String gdNumber = "" + document.getString("gd_number");

                                Item item = new Item(idString, form, mobile, policeStation, email, date, gdNumber);
                                if (item.getGd_Station().equals(user)) {
                                    addData(item);
                                }

                            }

                            Log.d(TAG, "" + listItem.size());
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.");
                        }
                    }
                });


    }

    public void addData(Item item) {
        listItem.add(item);
    }



}
