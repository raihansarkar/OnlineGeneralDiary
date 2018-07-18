package com.example.raihansarkar.onlinegeneraldiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GdCopy extends AppCompatActivity {

    TextView gdCopyTv;
    TextView name;
    TextView father;
    TextView mother;
    TextView subject;
    TextView station;
    TextView thana;
    TextView district;
    TextView nid;
    TextView mobile;
    TextView email;
    TextView perAddress;
    TextView cerAddress;
    TextView date;
    TextView description;
    Button sendBtn;
    FirebaseFirestore db;
    String TAG="firebaseChack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_copy);

        Intent l=getIntent();

        sendBtn=findViewById(R.id.btnSend);
        gdCopyTv=(TextView)findViewById(R.id.tvGdCopy);
        gdCopyTv.setMovementMethod(new ScrollingMovementMethod());

        String sName = l.getStringExtra("Name");
        String sFather = l.getStringExtra("Father");
        String sMother = l.getStringExtra("Mother");
        String sSubject = l.getStringExtra("Subject");
        final String sStation = l.getStringExtra("Station");
        String sThana = l.getStringExtra("Thana");
        String sDistrict = l.getStringExtra("District");
        String sNid = l.getStringExtra("Nid");
        final String sMpbile = l.getStringExtra("Mobile");
        final String sEmail = l.getStringExtra("Email");
        String sPerAddress = l.getStringExtra("PerAddress");
        String sCerAddress = l.getStringExtra("CerAddress");
        final String sDate = l.getStringExtra("Date");
        String sDescription = l.getStringExtra("Description");

        final DatabaseHelper helper=new DatabaseHelper(getBaseContext());

        gdCopyTv.setText("Date: "+sDate+"\nTo\n"+sStation+"\n"+sThana+", "+sDistrict+"\n\nSubject: "+sSubject+"\n\nDear Sir,\nI, Mr "+sName+", son of "+sFather+", of "+sMother+", NID: "+sNid+"\nPermanent Address: "+sPerAddress+"\nCurrent Address: "+sCerAddress+"\nDescription: "+sDescription+"\n\nIn the circumstances, I hereby kindly request you to take necessary steps regarding their said matter and entry the said matter as a general Diary with your police Station and oblige me thereby.\n\nYours truly,\n"+sName+"\nCell No. "+sMpbile+"\nEmail: "+sEmail);
        final String gdFormCopy= gdCopyTv.getText().toString();


        //firebase---------------
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GdCopy.this);
                builder.setTitle("Click OK to send your GD!")
                        .setMessage("Are you sure to send your GD?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        helper.userInsertGDForm(gdFormCopy, sMpbile, sStation, sEmail);

                        db = FirebaseFirestore.getInstance();
                        insertData(new Item(gdFormCopy, sMpbile, sStation, sEmail,sDate, "GD Number Is Panding..."));
                        Intent intent=new Intent(GdCopy.this,HomeActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void insertData(Item item){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("gd_form", item.getGd_form());
        user.put("mobile_number", item.getMobile());
        user.put("police_station", item.getGd_Station());
        user.put("email", item.getGd_email());
        user.put("date", item.getGd_date());
        user.put("gd_number", item.getGd_number());



// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(GdCopy.this,"Youe Application send successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(GdCopy.this,"Youe Application do not send! Try again...",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
