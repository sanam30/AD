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

public class AddProductPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText name, type, price, quantity;
    private Button addImage, next;
    private ImageView productImage;
    private Spinner spinner;
    private static final int Gallery_Pick = 1;
    private Uri ImageUri;
    ProgressDialog progressDialog;

    private String productname, producttype, productprice, productquantity, currency;
    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;

    private StorageReference productimageref;
    private DatabaseReference productref, userref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_page);
        UIsettings();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        productimageref = FirebaseStorage.getInstance().getReference().child("Product Image");
        productref = FirebaseDatabase.getInstance().getReference().child("Product");
        userref = FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddProductPage.this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInfo();
            }
        });
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
        producttype = type.getText().toString();
        productprice = price.getText().toString();
        productquantity = quantity.getText().toString();

        if(TextUtils.isEmpty(productname)){
            Toast.makeText(AddProductPage.this, "Please fill in the pname", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(producttype)){
            Toast.makeText(AddProductPage.this, "Please fill in the ptype", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productprice)){
            Toast.makeText(AddProductPage.this, "Please fill in the product price", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productquantity)){
            Toast.makeText(AddProductPage.this, "Please fill in the product quantity", Toast.LENGTH_SHORT).show();
        }

        else if(ImageUri != null){
            progressDialog.setMessage("Working on it...");
            progressDialog.show();
            storingImageToFirestore();
        }
        else{
            progressDialog.setMessage("Working on it...");
            progressDialog.show();
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
                    Toast.makeText(AddProductPage.this, "Image uploaded successfully",Toast.LENGTH_SHORT).show();

                    savingPost();
                }
                else{
                    String message = task.getException().getMessage();
                    Toast.makeText(AddProductPage.this,"Error" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currency = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void savingPost(){

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        final String producttype = type.getText().toString();
        final String postRandomName = productname + " " + saveCurrentDate + " " + saveCurrentTime;

        userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashMap = new HashMap();
                hashMap.put("pname", productname);
                hashMap.put("ptype", producttype);
                hashMap.put("pquantity", productquantity);
                hashMap.put("pprice", productprice);
                hashMap.put("pimage", downloadUrl);
                hashMap.put("pdate", saveCurrentDate);
                hashMap.put("pcurrency", currency);

                productref.child(producttype).child(postRandomName).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            productref.child("Type").child(producttype).child("ptype").setValue(producttype);
                            Toast.makeText(AddProductPage.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent descriptionpageintent = new Intent(AddProductPage.this, DescriptionPage.class);
                            descriptionpageintent.putExtra("product_type", producttype);
                            descriptionpageintent.putExtra("product_id", postRandomName);
                            startActivity(descriptionpageintent);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(AddProductPage.this, "Error", Toast.LENGTH_SHORT).show();
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

        final String producttype = type.getText().toString();
        final String postRandomName = productname + " " + saveCurrentDate + " " + saveCurrentTime;

        userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("pname", productname);
                    hashMap.put("ptype", producttype);
                    hashMap.put("pquantity", productquantity);
                    hashMap.put("pprice", productprice);
                    hashMap.put("pdate", saveCurrentDate);
                    hashMap.put("pcurrency", currency);

                    productref.child(producttype).child(postRandomName).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {

                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                productref.child("Type").child(producttype).child("ptype").setValue(producttype);
                                Toast.makeText(AddProductPage.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent descriptionpageintent = new Intent(AddProductPage.this, DescriptionPage.class);
                                descriptionpageintent.putExtra("product_type", producttype);
                                descriptionpageintent.putExtra("product_id", postRandomName);
                                startActivity(descriptionpageintent);
                                progressDialog.dismiss();
                            }
                            else{
                                Toast.makeText(AddProductPage.this, "Error", Toast.LENGTH_SHORT).show();
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
        name = (EditText)findViewById(R.id.addName);
        type = (EditText)findViewById(R.id.addType);
        price = (EditText)findViewById(R.id.addPrice);
        quantity = (EditText)findViewById(R.id.addQuantity);

        next = (Button) findViewById(R.id.buttontodes);
        addImage = (Button)findViewById(R.id.addimagebutton);

        productImage = (ImageView)findViewById(R.id.addimage1);
        productImage.setVisibility(View.INVISIBLE);

        spinner = (Spinner)findViewById(R.id.spinnerCurrency);

    }


}
