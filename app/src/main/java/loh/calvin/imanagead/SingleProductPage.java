package loh.calvin.imanagead;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SingleProductPage extends AppCompatActivity {

    private TextView productname, productdescription, productprice, productquantity;
    private EditText newprice, newquantity;
    private ImageView productimage;
    private Button edit, save, remove;
    String type = null, id = null, current_user_id, currency = null;
    DatabaseReference productref, userref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_page);

        type = getIntent().getExtras().getString("producttype");
        id = getIntent().getExtras().getString("productID");
        currency = getIntent().getExtras().getString("productcurrency");
        userref = FirebaseDatabase.getInstance().getReference().child("Users");
        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        productref = FirebaseDatabase.getInstance().getReference().child("Product").child(type).child(id);

        UISettings();

        save.setVisibility(View.GONE);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);

                productquantity.setVisibility(View.GONE);
                productprice.setVisibility(View.GONE);

                newprice.setVisibility(View.VISIBLE);
                newquantity.setVisibility(View.VISIBLE);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editproduct();
                    }
                });
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productref.removeValue();
                finish();
                startActivity(new Intent(SingleProductPage.this, MainPage.class));
            }
        });

        displayproduct();
    }

    private void editproduct(){
        userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!TextUtils.isEmpty(newprice.getText().toString()) && !TextUtils.isEmpty(newquantity.getText().toString())) {
                    final String pprice = newprice.getText().toString();
                    final String pquantity = newquantity.getText().toString();

                    HashMap hashMap = new HashMap();

                    hashMap.put("pquantity", pquantity);
                    hashMap.put("pprice", pprice);

                    productref.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            finish();
                        }
                    });

                } else{
                    HashMap hashMap = new HashMap();

                    hashMap.put("pquantity", "0");
                    hashMap.put("pprice", "0");

                    productref.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            finish();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayproduct(){
        productref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String)dataSnapshot.child("pname").getValue();
                String description = (String)dataSnapshot.child("Description").getValue();
                String image = (String)dataSnapshot.child("pimage").getValue();
                String price = (String)dataSnapshot.child("pprice").getValue();
                String quantity = (String)dataSnapshot.child("pquantity").getValue();

                productdescription.setText(description);
                productname.setText(name);
                productprice.setText(currency + "$ " + price);
                productquantity.setText(quantity);
                Picasso.get().load(image).into(productimage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void UISettings(){
        productdescription = (TextView)findViewById(R.id.description_single_page);
        productimage = (ImageView)findViewById(R.id.imageView_single_page);
        productname = (TextView)findViewById(R.id.name_single_page);
        productprice = (TextView)findViewById(R.id.price_single_page);
        productquantity = (TextView)findViewById(R.id.quantity_single_page);
        edit = (Button)findViewById(R.id.edit_single_page);
        save = (Button)findViewById(R.id.save_single_page);
        newprice = (EditText)findViewById(R.id.newprice_single_page);
        newquantity = (EditText)findViewById(R.id.newquantity_single_page);
        remove = (Button) findViewById(R.id.remove_single_page);

        productdescription.setFocusable(false);
        productdescription.setClickable(true);
    }
}
