package co.com.etn.arquitecturamvpbase.view.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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


    private EditText productDetailEditName;
    private EditText productDetailEditDescription;
    private EditText productDetailEditPrice;
    private EditText productDetailEditQuantity;

    private FloatingActionButton fabUpdateProduct;
    private FloatingActionButton fabEditProduct;

    private Drawable backgroundOri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        setPresenter(new DetailProductPresenter(new ProductRepository()));
        getPresenter().inject(this, getValidateInternet());

        // Esto es encargado por la vista no por el presentador
        createProgressDialog();

        loadView();

        boolean editable = (boolean) getIntent().getSerializableExtra(Constants.ITEM_EDIT);

        setEditable(editable);

        product = (Product) getIntent().getSerializableExtra(Constants.ITEM_PRODUCT);
        setDataItem();
    }

    private void setDataItem() {
//        nameValue.setText(product.getName());
//        descriptionValue.setText(product.getDescription());
//        quantityValue.setText(product.getQuantity());
//        priceValue.setText(product.getPrice());

        productDetailEditName.setText(product.getName());
        productDetailEditDescription.setText(product.getDescription());
        productDetailEditPrice.setText(product.getQuantity());
        productDetailEditQuantity.setText(product.getPrice());
    }

    private void loadView() {
//        nameValue = (TextView) findViewById(R.id.product_detail_name_value);
//        descriptionValue = (TextView) findViewById(R.id.product_detail_description_value);
//        quantityValue = (TextView) findViewById(R.id.product_detail_quantity_value);
//        priceValue = (TextView) findViewById(R.id.product_detail_price_value);

        productDetailEditName = (EditText) findViewById(R.id.product_detail_edit_name);
        productDetailEditDescription = (EditText) findViewById(R.id.product_detail_edit_description);
        productDetailEditPrice = (EditText) findViewById(R.id.product_detail_edit_price);
        productDetailEditQuantity = (EditText) findViewById(R.id.product_detail_edit_quantity);

        fabUpdateProduct = (FloatingActionButton) findViewById(R.id.fabUpdateProduct);
        fabEditProduct = (FloatingActionButton) findViewById(R.id.fabEditProduct);

        backgroundOri =  productDetailEditName.getBackground();

    }


    @Override
    public void showAlertDialog(int validate_internet) {
        // TODO: Implementar
    }

    @Override
    public void showToast(int id) {
        showToast(getResources().getString(id));

    }

    @Override
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();
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
    public void updateProduct(View view) {
        Log.d(TAG, "Actualizar el producto " + product.getId());
        product.setName(productDetailEditName.getText().toString());
        product.setDescription(productDetailEditDescription.getText().toString());
        product.setQuantity(productDetailEditQuantity.getText().toString());
        product.setPrice(productDetailEditPrice.getText().toString());

        getPresenter().updateProduct(product.getId(), product);

    }

    public void editProduct(View view) {
        setEditable(true);

    }

    public void setEditable(boolean editable) {
        if (editable) {
            productDetailEditName.setFocusable(true);
            productDetailEditName.setFocusableInTouchMode(true);
            productDetailEditName.setBackground(backgroundOri);

            productDetailEditDescription.setFocusable(true);
            productDetailEditDescription.setFocusableInTouchMode(true);
            productDetailEditDescription.setBackground(backgroundOri);

            productDetailEditQuantity.setFocusable(true);
            productDetailEditQuantity.setFocusableInTouchMode(true);
            productDetailEditQuantity.setBackground(backgroundOri);

            productDetailEditPrice.setFocusable(true);
            productDetailEditPrice.setFocusableInTouchMode(true);
            productDetailEditPrice.setBackground(backgroundOri);

            fabUpdateProduct.setVisibility(View.VISIBLE);
            fabEditProduct.setVisibility(View.GONE);



            showToast("Editable");
        } else {
            showToast("NOOO Editable");
            productDetailEditName.setFocusable(false);
            productDetailEditName.setBackgroundColor(Color.TRANSPARENT);

            productDetailEditDescription.setFocusable(false);
            productDetailEditDescription.setBackgroundColor(Color.TRANSPARENT);

            productDetailEditQuantity.setFocusable(false);
            productDetailEditQuantity.setBackgroundColor(Color.TRANSPARENT);

            productDetailEditPrice.setFocusable(false);
            productDetailEditPrice.setBackgroundColor(Color.TRANSPARENT);

            fabUpdateProduct.setVisibility(View.GONE);



        };
    }
}
