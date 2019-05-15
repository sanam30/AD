package loh.calvin.imanagead;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DescriptionPage extends AppCompatActivity {

    private String productid = null;
    private String product_type = null;
    private String des, current_user_id;
    private EditText description;
    private Button save;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth, mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference productref, userref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        productref = FirebaseDatabase.getInstance().getReference().child("Product");
        userref = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        progressDialog = new ProgressDialog(this);


        productid = getIntent().getExtras().getString("product_id");
        product_type = getIntent().getExtras().getString("product_type");
        UIsettings();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInfo();
            }
        });

    }

    private void validateInfo(){
        des = description.getText().toString();
        if(des.isEmpty()){
            Toast.makeText(DescriptionPage.this,"Please fill in the description",Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setMessage("Working on it...");
            progressDialog.show();
            post();
        }
    }

    private void post(){

        des = description.getText().toString();

        userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashMap = new HashMap();
                hashMap.put("Description", des);

                productref.child(product_type).child(productid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DescriptionPage.this, "Successfull", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(DescriptionPage.this, MainPage.class));
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(DescriptionPage.this, "Failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void UIsettings(){
        description = (EditText)findViewById(R.id.ET_ProductDesctiption);
        save = (Button)findViewById(R.id.next2);
    }
}
