package co.com.etn.arquitecturamvpbase.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.presenter.DetailProductPresenter;
import co.com.etn.arquitecturamvpbase.repository.ProductRepository;
import co.com.etn.arquitecturamvpbase.view.BaseActivity;

/**
 * Created by draiven on 9/16/17.
 */

public class DetailActivity extends BaseActivity<DetailProductPresenter> implements IDetailProductView {

    private static final String TAG = "DetailActivity";
    private TextView nameValue;
    private TextView descriptionValue;
    private TextView quantityValue;
    private TextView priceValue;
    private Product product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        setPresenter(new DetailProductPresenter(new ProductRepository()));
        getPresenter().inject(this, getValidateInternet());

        // Esto es encargado por la vista no por el presentador
        createProgressDialog();

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


    @Override
    public void showAlertDialog(int validate_internet) {
        // TODO: Implementar
    }

    @Override
    public void showToast(int success_deleted) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailActivity.this, "Borrado Exitosamente..", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void showAlertDialogError(int error_deleted) {
        // TODO: mostrar
    }

    public void deleteProduct(View view) {
        Log.d(TAG, "Borrar el producto " + product.getId());
        getPresenter().deleteProduct(product.getId());

    }
}
