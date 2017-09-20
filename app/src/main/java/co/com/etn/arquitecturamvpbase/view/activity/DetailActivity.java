package co.com.etn.arquitecturamvpbase.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.model.Product;

/**
 * Created by draiven on 9/16/17.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView nameValue;
    private TextView descriptionValue;
    private TextView quantityValue;
    private TextView priceValue;
    private Product product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);
        loadView();
        product = (Product) getIntent().getSerializableExtra(Constants.ITEM_PRODUCT);
        setDataItem();
    }

    private void setDataItem() {
        nameValue.setText(product.getName());
        descriptionValue.setText(product.getDescription());
        quantityValue.setText(product.getQuantity());
        priceValue.setText(product.getPrice());
    }

    private void loadView() {
        nameValue = (TextView) findViewById(R.id.product_detail_name_value);
        descriptionValue = (TextView) findViewById(R.id.product_detail_description_value);
        quantityValue = (TextView) findViewById(R.id.product_detail_quantity_value);
        priceValue = (TextView) findViewById(R.id.product_detail_price_value);

    }
}
