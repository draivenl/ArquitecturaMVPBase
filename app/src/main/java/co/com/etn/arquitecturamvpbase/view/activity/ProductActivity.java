package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.Intent;
import android.os.Bundle;
import co.com.etn.arquitecturamvpbase.helper.Constants;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.presenter.ProductPresenter;
import co.com.etn.arquitecturamvpbase.view.BaseActivity;
import co.com.etn.arquitecturamvpbase.view.adapter.ProductAdapter;

/**
 * Created by draiven on 9/16/17.
 */

public class ProductActivity extends BaseActivity<ProductPresenter> implements IProductView{

    private ListView productList;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setPresenter(new ProductPresenter());
        getPresenter().inject(this, getValidateInternet());

        createProgressDialog();

        getPresenter().validateInternetProduct();
        productList = (ListView) findViewById(R.id.product_listView);
    }

    @Override
    public void showProductList(final ArrayList<Product> productArrayList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(productArrayList);

            }
        });



    }

    @Override
    public void showNotConnected() {
        Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
    }

    public void callAdapter(final ArrayList<Product> productArrayList) {
        productAdapter = new ProductAdapter(this, R.id.product_listView, productArrayList);
        productList.setAdapter(productAdapter);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ProductActivity.this, DetailActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT, productArrayList.get(position));
                startActivity(intent);

            }
        });
    }

    public void launchNewProductActivity(View view){
        Intent intent = new Intent(this, NewProductActivity.class );
        startActivity(intent);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().validateInternetProduct();
    }
}
