package com.example.doannhom7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.helper.DBHelper;

public class Profile_MyAccount2 extends AppCompatActivity {
    ImageButton btnBackMyAccount2;
    Button  btnSaveProfile;

    EditText edtEditFullName, edtEditUsername, edtEditEmail, edtEditDob;

    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_my_account2);
        linkViews();

        setData();

        addEvents();
    }
    private void linkViews() {
        btnBackMyAccount2=findViewById(R.id.btnBackMyAccount2);
        btnSaveProfile=findViewById(R.id.btnSaveProfile);
        edtEditFullName=findViewById(R.id.edtEditFullName);
        edtEditUsername=findViewById(R.id.edtEditUsername);
        edtEditEmail=findViewById(R.id.edtEditEmail);
        edtEditDob=findViewById(R.id.edtEditDob);

    }

    public void setData(){

        Intent receiveIntent = getIntent();
        DB = new DBHelper(this);

        Cursor res = DB.getdata(receiveIntent.getStringExtra("keyUsername"));
        if(res.getCount()==0){
            Toast.makeText(this, "No Entry Exists", Toast.LENGTH_SHORT).show();
        }
        while(res.moveToNext()){
            String pfUsername = res.getString(0);
            String pfFullName = res.getString(1);
            String pfEmail = res.getString(2);
            String pfDob = res.getString(4);

            edtEditFullName.setText(pfFullName);
            edtEditUsername.setText(pfUsername);
            edtEditEmail.setText(pfEmail);
            edtEditDob.setText(pfDob);

        }
    }

    private void addEvents() {
        btnBackMyAccount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Profile_MyAccount2.this,Profile_MyAccount1.class);
                String user = getIntent().getStringExtra("keyUsername");
                intent.putExtra("keyUsername", user);
                startActivity(intent);
            }
        });
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = getIntent().getStringExtra("keyUsername");
//                intent.putExtra("keyUsername", user);

                String fullName = edtEditFullName.getText().toString();
                String username = edtEditUsername.getText().toString();
                String email = edtEditEmail.getText().toString();
                String dob = edtEditDob.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(user, fullName, username, email, dob);
                if(checkupdatedata==true)
                    Toast.makeText(Profile_MyAccount2.this, "Entry Updated, Please Login again", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Profile_MyAccount2.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(Profile_MyAccount2.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}