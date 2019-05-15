package loh.calvin.imanagead;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductPage2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name, price, quantity;
    private String currency;
    private Button addImage, next;
    private ImageView productImage;
    private Spinner spinner;
    private static final int Gallery_Pick = 1;
    private Uri ImageUri;
    ProgressDialog progressDialog;
    String producttype2 = null;

    private String productname, productprice, productquantity;
    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;

    private StorageReference productimageref;
    private DatabaseReference productref, userref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_page2);
        UIsettings();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        productimageref = FirebaseStorage.getInstance().getReference().child("Product Image");
        productref = FirebaseDatabase.getInstance().getReference().child("Product");
        userref = FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog = new ProgressDialog(this);
        producttype2 = getIntent().getExtras().getString("producttype");

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Working on it...");
                progressDialog.show();
                validateInfo();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddProductPage2.this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currency = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void openGallery(){
        Intent opengallery = new Intent();
        opengallery.setAction(Intent.ACTION_GET_CONTENT);
        opengallery.setType("image/*");
        startActivityForResult(opengallery, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode, data);

        if(requestcode==Gallery_Pick && resultcode==RESULT_OK && data!=null){
            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);
            addImage.setVisibility(View.GONE);
            productImage.setVisibility(View.VISIBLE);
        }
    }

    private void validateInfo(){
        productname = name.getText().toString();
        productprice = price.getText().toString();
        productquantity = quantity.getText().toString();

        if(TextUtils.isEmpty(productname)){
            Toast.makeText(AddProductPage2.this, "Please fill in the pname", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productprice)){
            Toast.makeText(AddProductPage2.this, "Please fill in the product price", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productquantity)){
            Toast.makeText(AddProductPage2.this, "Please fill in the product quantity", Toast.LENGTH_SHORT).show();
        }

        else if(ImageUri != null){
            storingImageToFirestore();
        }
        else{
            savingPostWOImage();
        }
    }

    private void storingImageToFirestore(){
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath = productimageref.child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        filepath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    downloadUrl = task.getResult().getDownloadUrl().toString();
                    Toast.makeText(AddProductPage2.this, "Image uploaded successfully",Toast.LENGTH_SHORT).show();

                    savingPost();
                }
                else{
                    String message = task.getException().getMessage();
                    Toast.makeText(AddProductPage2.this,"Error" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savingPost(){

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        final String postRandomName = productname + " " + saveCurrentDate + " " + saveCurrentTime;

        userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("pname", productname);
                    hashMap.put("ptype", producttype2);
                    hashMap.put("pquantity", productquantity);
                    hashMap.put("pprice", productprice);
                    hashMap.put("pimage", downloadUrl);
                    hashMap.put("pdate", saveCurrentDate);
                    hashMap.put("pcurrency", currency);

                    productref.child(producttype2).child(postRandomName).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                productref.child("Type").child(producttype2).child("ptype").setValue(producttype2);
                                Toast.makeText(AddProductPage2.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent descriptionpageintent = new Intent(AddProductPage2.this, DescriptionPage.class);
                                descriptionpageintent.putExtra("product_type", producttype2);
                                descriptionpageintent.putExtra("product_id", postRandomName);
                                startActivity(descriptionpageintent);
                                progressDialog.dismiss();
                            }
                            else{
                                Toast.makeText(AddProductPage2.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void savingPostWOImage(){

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        final String postRandomName = productname + " " + saveCurrentDate + " " + saveCurrentTime;

        userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("pname", productname);
                    hashMap.put("ptype", producttype2);
                    hashMap.put("pquantity", productquantity);
                    hashMap.put("pprice", productprice);
                    hashMap.put("pdate", saveCurrentDate);
                    hashMap.put("pcurrency", currency);

                    productref.child(producttype2).child(postRandomName).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                productref.child("Type").child(producttype2).child("ptype").setValue(producttype2);
                                Toast.makeText(AddProductPage2.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent descriptionpageintent = new Intent(AddProductPage2.this, DescriptionPage.class);
                                descriptionpageintent.putExtra("product_type", producttype2);
                                descriptionpageintent.putExtra("product_id", postRandomName);
                                startActivity(descriptionpageintent);
                                progressDialog.dismiss();
                            }
                            else{
                                Toast.makeText(AddProductPage2.this, "Error", Toast.LENGTH_SHORT).show();
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
        name = (EditText)findViewById(R.id.addName2);
        price = (EditText)findViewById(R.id.addPrice2);
        quantity = (EditText)findViewById(R.id.addQuantity2);

        next = (Button) findViewById(R.id.buttontodes2);
        addImage = (Button)findViewById(R.id.addimagebutton2);

        productImage = (ImageView)findViewById(R.id.addimage12);
        productImage.setVisibility(View.INVISIBLE);

        spinner = (Spinner)findViewById(R.id.spinnerCurrency2);

    }
}