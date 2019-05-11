package loh.calvin.imanagead;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    private Button add, signout, searchbutton;
    private RecyclerView typelist;
    private DatabaseReference typeref;
    private FirebaseAuth firebaseAuth;
    private EditText search;

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

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayuserpost();
            }
        });

    }

    private void displayuserpost() {
            FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>
                    (
                            Product.class,
                            R.layout.adapter_all_type,
                            ProductViewHolder.class,
                            typeref
                    )
            {
                @Override
                protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {

                    final String producttype = getRef(position).getKey();

                    if (search.getText().toString().isEmpty()) {

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
                    else if(!search.getText().toString().isEmpty()){

                        String x = search.getText().toString();

                        if(model.getPtype().contains(x)) {

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
                        else if (!model.getPtype().contains(x)){
                            viewHolder.setPtype2(model.getPtype());
                        }
                    }
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
            producttype.setVisibility(View.VISIBLE);
        }
        public void setPtype2(String ptype2){
            TextView producttype2 = (TextView) mView.findViewById(R.id.tv_producttype2);
            producttype2.setVisibility(View.GONE);
        }
    }

    private void UIsettings(){
        add = (Button)findViewById(R.id.buttonadd1);
        typelist = (RecyclerView)findViewById(R.id.RV_Type);
        signout = (Button)findViewById(R.id.btn_signout);
        search = (EditText)findViewById(R.id.search_main);
        searchbutton = (Button)findViewById(R.id.searchtypebutton);
    }
}
