package loh.calvin.imanagead;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {

    private Button add, signout;
    private RecyclerView typelist;
    private DatabaseReference typeref;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        firebaseAuth = FirebaseAuth.getInstance();
        typeref = FirebaseDatabase.getInstance().getReference().child("Product").child("Type");

        UIsettings();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, AddProductPage.class));
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainPage.this, LoginPage.class));
            }
        });


        typelist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        typelist.setLayoutManager(linearLayoutManager);

        displayuserpost();
    }

    private void displayuserpost() {
        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>
                (
                        Product.class,
                        R.layout.activity_all_type,
                        ProductViewHolder.class,
                        typeref
                ) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {

                final String producttype = getRef(position).getKey();

                viewHolder.setPtype(model.getPtype());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewPageIntent = new Intent(MainPage.this, ProductInformation.class);
                        viewPageIntent.putExtra("producttype", producttype);
                        startActivity(viewPageIntent);
                    }
                });
            }
        };
        typelist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPtype(String ptype) {
            TextView producttype = (TextView) mView.findViewById(R.id.tv_producttype2);
            producttype.setText(ptype);
        }
    }

    private void UIsettings(){
        add = (Button)findViewById(R.id.buttonadd1);
        typelist = (RecyclerView)findViewById(R.id.RV_Type);
        signout = (Button)findViewById(R.id.btn_signout);
    }
}
