package com.example.raihansarkar.onlinegeneraldiary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GdStatus extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ListView myList;
    private List<Item> listItem;
    private BaseAdapter adapter;
    FirebaseFirestore db;
    String TAG="firebaseChack";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_status);

        mAuth=FirebaseAuth.getInstance();

        myList=(ListView) findViewById(R.id.gdListView);

        db = FirebaseFirestore.getInstance();

        listItem=new ArrayList<Item>();
        adapter=new BaseAdapter() {
            LayoutInflater inflater= (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

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

                if (convertView==null){
                    convertView=inflater.inflate(R.layout.child_layout,null);
                }
                TextView itemNumber=(TextView) convertView.findViewById(R.id.tvUserGDNumberId);
                TextView itemForm=(TextView) convertView.findViewById(R.id.tvGdFormId);

                itemNumber.setText(listItem.get(position).getGd_number());
                itemForm.setText(listItem.get(position).getGd_form());
                return convertView;
            }
        };

        myList.setAdapter(adapter);
        refreshDataView();

        adapter.notifyDataSetChanged();

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

    public void refreshDataView(){
        listItem.removeAll(listItem);

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String form = "" + document.getString("gd_form");
                                String mobile = "" + document.getString("mobile_number");
                                String policeStation = "" + document.getString("police_station");
                                String email = "" + document.getString("email");
                                String date = "" + document.getString("date");
                                String gdNumber = "" + document.getString("gd_number");

                                Item item = new Item(form, mobile, policeStation, email, date, gdNumber);
                                if (mAuth.getCurrentUser().getEmail().equals(item.getGd_email())){
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

    public void addData(Item item)
    {
        listItem.add(item);
    }

}
