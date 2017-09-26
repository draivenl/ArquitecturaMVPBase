package co.com.etn.arquitecturamvpbase.view.activity;

import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.presenter.NewProductPresenter;
import co.com.etn.arquitecturamvpbase.view.BaseActivity;

public class NewProductActivity extends BaseActivity<NewProductPresenter> implements INewProductView, TextWatcher {

    private TextInputEditText name;
    private TextInputEditText description;
    private TextInputEditText quantity;
    private TextInputEditText price;
    private Product product;
    private Button btnCreate;

    NewProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        // No se debe hacer una instancia, se debe setear el presenter
        setPresenter(new NewProductPresenter());
        getPresenter().inject(this, getValidateInternet());
        createProgressDialog();
        loadView();
        setButtonCreateDisabled();



    }

    private void loadView() {
        name = (TextInputEditText) findViewById(R.id.new_product_name);
        description = (TextInputEditText) findViewById(R.id.new_product_description);
        quantity = (TextInputEditText) findViewById(R.id.new_product_quantity);
        price = (TextInputEditText) findViewById(R.id.new_product_price);
        btnCreate = (Button) findViewById(R.id.new_product_create);

        name.addTextChangedListener(this);
        description.addTextChangedListener(this);
        quantity.addTextChangedListener(this);
        price.addTextChangedListener(this);
    }




    @Override
    public void setButtonCreateEnabled() {
        btnCreate.setEnabled(true);
        btnCreate.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));


    }

    @Override
    public void setButtonCreateDisabled() {
        btnCreate.setEnabled(false);
        btnCreate.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDisabled));
    }

    @Override
    public void onSuccessNewProduct() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(NewProductActivity.this, getResources().getString(R.string.new_product_ok), Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        validateEmptyInputs();
    }

    private void validateEmptyInputs() {
        if(!name.getText().toString().isEmpty()
                && !description.getText().toString().isEmpty()
                && !quantity.getText().toString().isEmpty()
                && !price.getText().toString().isEmpty()) {
            setButtonCreateEnabled();

        } else {
            setButtonCreateDisabled();
        }
    }

    public void createNewProduct(View view){
        product = new Product();
        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        product.setQuantity(quantity.getText().toString());
        product.setPrice(price.getText().toString());
        getPresenter().createNewProduct(product);

        
    }

}
