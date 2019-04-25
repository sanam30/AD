package loh.calvin.imanagead;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class ProductInformation extends AppCompatActivity {

    private String producttype = null;
    private Button back;
    RecyclerView productInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);

        producttype = getIntent().getExtras().getString("producttype");

        UISettings();

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

        displayuserpost();
    }

    private void displayuserpost(){
        
    }

    private void UISettings(){
        back = (Button)findViewById(R.id.button_back);
        productInfo = (RecyclerView)findViewById(R.id.RV_product);
    }

}
