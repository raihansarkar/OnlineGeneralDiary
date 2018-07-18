package com.example.raihansarkar.onlinegeneraldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LostForm extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText nameEt;
    EditText fatherNameEt;
    EditText motherNameEt;
    EditText subjectEt;
    EditText policeStationEt;
    EditText policeThanaEt;
    EditText policeDistrictEt;
    EditText nidEt;
    EditText mobileEt;
    EditText emailEt;
    EditText perAddressEt;
    EditText cerAddressEt;
    EditText dateEt;
    EditText descriptionEt;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_form);

        Intent j =getIntent();

        mAuth=FirebaseAuth.getInstance();

        nameEt=(EditText)findViewById(R.id.etName);
        fatherNameEt=(EditText)findViewById(R.id.etFatherName);
        motherNameEt=(EditText)findViewById(R.id.etMotherName);
        subjectEt=(EditText)findViewById(R.id.etSubject);
        policeStationEt=(EditText)findViewById(R.id.etPoliceStation);
        policeThanaEt=(EditText)findViewById(R.id.etPoliceThana);
        policeDistrictEt=(EditText)findViewById(R.id.etPoliceDistrict);
        nidEt=(EditText)findViewById(R.id.etNid);
        mobileEt=(EditText)findViewById(R.id.etMobile);
        emailEt=(EditText)findViewById(R.id.etEmail);
        perAddressEt=(EditText)findViewById(R.id.etPerAddress);
        cerAddressEt=(EditText)findViewById(R.id.etCerAddress);
        dateEt=(EditText)findViewById(R.id.etDate);
        descriptionEt=(EditText)findViewById(R.id.etDescription);
        submitBtn=(Button)findViewById(R.id.btnSubmit);



        dateEt.setText(getDateTime());
        emailEt.setText(mAuth.getCurrentUser().getEmail());
        if (mAuth.getCurrentUser().getPhoneNumber()!=null){
            mobileEt.setText(mAuth.getCurrentUser().getPhoneNumber());
        }
        mobileEt.setText(mAuth.getCurrentUser().getPhoneNumber());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name=nameEt.getText().toString();
                String father=fatherNameEt.getText().toString();
                String mother=motherNameEt.getText().toString();
                String subject=subjectEt.getText().toString();
                String station=policeStationEt.getText().toString();
                String thana=policeThanaEt.getText().toString();
                String district=policeDistrictEt.getText().toString();
                String nid=nidEt.getText().toString();
                String mobile=mobileEt.getText().toString();
                String email=emailEt.getText().toString();
                String perAddress=perAddressEt.getText().toString();
                String cerAddress=cerAddressEt.getText().toString();
                String date=dateEt.getText().toString();
                String description=descriptionEt.getText().toString();


                Intent k=new Intent(LostForm.this, GdCopy.class);

                k.putExtra("Name", name);
                k.putExtra("Father", father);
                k.putExtra("Mother", mother);
                k.putExtra("Subject", subject);
                k.putExtra("Station", station);
                k.putExtra("Thana", thana);
                k.putExtra("District", district);
                k.putExtra("Nid", nid);
                k.putExtra("Mobile", mobile);
                k.putExtra("Email", email);
                k.putExtra("PerAddress", perAddress);
                k.putExtra("CerAddress", cerAddress);
                k.putExtra("Date", date);
                k.putExtra("Description", description);

                startActivity(k);

                nameEt.setText("");
                fatherNameEt.setText("");
                motherNameEt.setText("");
                subjectEt.setText("");
                policeStationEt.setText("");
                policeThanaEt.setText("");
                policeDistrictEt.setText("");
                nidEt.setText("");
                mobileEt.setText("");
                emailEt.setText("");
                perAddressEt.setText("");
                cerAddressEt.setText("");
                dateEt.setText("");
                descriptionEt.setText("");

            }
        });

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
