package loh.calvin.imanagead;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductInformation extends AppCompatActivity {

    private String producttype = null;
    private String productcurrency = null;
    private String forref;
    private Button back, add;
    RecyclerView productInfo;
    private DatabaseReference typeref;
    private FirebaseAuth firebaseAuth;
    private TextView inf;
    private EditText search;
    private Button searchbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        producttype = getIntent().getExtras().getString("producttype");
        forref = producttype.toString();

        typeref = FirebaseDatabase.getInstance().getReference().child("Product").child(forref);

        UISettings();


        inf.setText(producttype);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductInformation.this, MainPage.class));
            }
        });

        productInfo.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        productInfo.setLayoutManager(linearLayoutManager);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addproduct  = new Intent(ProductInformation.this, AddProductPage2.class);
                addproduct.putExtra("producttype", producttype);
                startActivity(addproduct);
            }
        });

        displayproduct();

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayproduct();
            }
        });
    }

    private void displayproduct() {
        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>
                (
                        Product.class,
                        R.layout.adapter_product_information,
                        ProductViewHolder.class,
                        typeref
                ) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, final Product model, int position) {

                final String productID = getRef(position).getKey();

                if (search.getText().toString().isEmpty()) {

                    viewHolder.setPtype(model.getPtype());
                    viewHolder.setPdate(model.getPdate());
                    viewHolder.setPimage(getApplicationContext(), model.getPimage());
                    viewHolder.setPname(model.getPname());
                    viewHolder.setPquantity(model.getPquantity());
                    viewHolder.setPprice(model.getPprice());
                    viewHolder.setPcurrency(model.getPcurrency());

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent viewPageIntent = new Intent(ProductInformation.this, SingleProductPage.class);
                            viewPageIntent.putExtra("productID", productID);
                            viewPageIntent.putExtra("producttype", producttype);
                            viewPageIntent.putExtra("productcurrency", model.getPcurrency());
                            startActivity(viewPageIntent);
                        }
                    });
                }
                else if(!search.getText().toString().isEmpty()){
                    String x = search.getText().toString();
                    if(model.pname.contains(x)){
                        viewHolder.setPtype(model.getPtype());
                        viewHolder.setPdate(model.getPdate());
                        viewHolder.setPimage(getApplicationContext(), model.getPimage());
                        viewHolder.setPname(model.getPname());
                        viewHolder.setPquantity(model.getPquantity());
                        viewHolder.setPprice(model.getPprice());
                        viewHolder.setPcurrency(model.getPcurrency());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent viewPageIntent = new Intent(ProductInformation.this, SingleProductPage.class);
                                viewPageIntent.putExtra("productID", productID);
                                viewPageIntent.putExtra("producttype", producttype);
                                viewPageIntent.putExtra("productcurrency", model.getPcurrency());
                                startActivity(viewPageIntent);
                            }
                        });
                    }
                    else if(!model.pname.contains(x)){
                        viewHolder.setPtype2(model.getPtype());
                        viewHolder.setPdate2(model.getPdate());
                        viewHolder.setPimage2(getApplicationContext(), model.getPimage());
                        viewHolder.setPname2(model.getPname());
                        viewHolder.setPquantity2(model.getPquantity());
                        viewHolder.setPprice2(model.getPprice());
                        viewHolder.setPcurrency2(model.getPcurrency());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent viewPageIntent = new Intent(ProductInformation.this, SingleProductPage.class);
                                viewPageIntent.putExtra("productID", productID);
                                viewPageIntent.putExtra("producttype", producttype);
                                viewPageIntent.putExtra("productcurrency", model.getPcurrency());
                                startActivity(viewPageIntent);
                            }
                        });
                    }
                }
            }
        };
        productInfo.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPtype(String ptype) {
            TextView producttype = (TextView) mView.findViewById(R.id.tv_ptype);
            producttype.setText("Product type = " + ptype);
        }
        public void setPname(String pname) {
            TextView productname = (TextView) mView.findViewById(R.id.tv_pname);
            productname.setText("Product Name = " + pname);
        }
        public void setPimage(Context ctx, String pimage) {
            ImageView productimage = (ImageView) mView.findViewById(R.id.iv_pimage);
            Picasso.get().load(pimage).into(productimage);
        }
        public void setPdate(String pdate) {
            TextView postdate = (TextView) mView.findViewById(R.id.tv_postdate);
            postdate.setText("Last updated on " + pdate);
        }
        public void setPquantity(String pquantity) {
            TextView productquantity = (TextView) mView.findViewById(R.id.tv_pquantity);
            productquantity.setText("Quantity left " + pquantity);
        }
        public void setPprice(String pprice) {
            TextView productprice = (TextView) mView.findViewById(R.id.tv_pprice);
            productprice.setText("$ " + pprice);
        }
        public void setPcurrency(String pcurrency){
            TextView productcurrency = (TextView) mView.findViewById(R.id.Pcurrency);
            productcurrency.setText(pcurrency);
        }
        public void setPtype2(String ptype) {
            TextView producttype = (TextView) mView.findViewById(R.id.tv_ptype);
            producttype.setVisibility(View.GONE);
        }
        public void setPname2(String pname) {
            TextView productname = (TextView) mView.findViewById(R.id.tv_pname);
            productname.setVisibility(View.GONE);
        }
        public void setPimage2(Context ctx, String pimage) {
            ImageView productimage = (ImageView) mView.findViewById(R.id.iv_pimage);
            productimage.setVisibility(View.GONE);
        }
        public void setPdate2(String pdate) {
            TextView postdate = (TextView) mView.findViewById(R.id.tv_postdate);
            postdate.setVisibility(View.GONE);
        }
        public void setPquantity2(String pquantity) {
            TextView productquantity = (TextView) mView.findViewById(R.id.tv_pquantity);
            productquantity.setVisibility(View.GONE);
        }
        public void setPprice2(String pprice) {
            TextView productprice = (TextView) mView.findViewById(R.id.tv_pprice);
            productprice.setVisibility(View.GONE);
        }
        public void setPcurrency2(String pcurrency){
            TextView productcurrency = (TextView) mView.findViewById(R.id.Pcurrency);
            productcurrency.setVisibility(View.GONE);
        }
    }

    private void UISettings(){
        back = (Button)findViewById(R.id.button_back);
        productInfo = (RecyclerView)findViewById(R.id.RV_product);
        inf = (TextView)findViewById(R.id.tv_inf);
        add = (Button)findViewById(R.id.add_product2);
        search = (EditText) findViewById(R.id.et_searchproduct);
        searchbutton = (Button) findViewById(R.id.btn_searchname);

    }

}
