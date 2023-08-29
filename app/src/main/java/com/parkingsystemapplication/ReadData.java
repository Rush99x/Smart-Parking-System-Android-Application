package com.parkingsystemapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parkingsystemapplication.databinding.ActivityReadDataBinding;

public class ReadData extends AppCompatActivity {

    ActivityReadDataBinding binding;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.readdataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.etusername.getText().toString();
                if (!username.isEmpty()){

                    readData(username);
                }else{

                    Toast.makeText(ReadData.this,"Please Enter Parking Name",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void readData(String username) {

        reference = FirebaseDatabase.getInstance().getReference("Parkings");
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        Toast.makeText(ReadData.this,"Successfully Read",Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String Slot1 = String.valueOf(dataSnapshot.child("Slot1").getValue());
                        String Slot2 = String.valueOf(dataSnapshot.child("Slot2").getValue());
                        binding.Slot1.setText(Slot1);
                        binding.Slot2.setText(Slot2);



                    }else {

                        Toast.makeText(ReadData.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();

                    }


                }else {

                    Toast.makeText(ReadData.this,"Failed to read",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}