package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.model.Location;
import co.com.etn.arquitecturamvpbase.model.PhoneList;
import co.com.etn.arquitecturamvpbase.presenter.NewCustomerPresenter;
import co.com.etn.arquitecturamvpbase.repository.CustomerRepository;
import co.com.etn.arquitecturamvpbase.view.BaseActivity;

public class NewCustomerActivity extends BaseActivity<NewCustomerPresenter> implements INewCustomerView {
    private TextInputEditText name;
    private TextInputEditText surname;
    private TextInputEditText phoneDescription;
    private TextInputEditText phoneNumber;
    private TextInputEditText coordinateX;
    private TextInputEditText coordinateY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);


        setPresenter(new NewCustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());
        createProgressDialog();
        loadView();
        setButtonCreateDisabled();


    }

    private void setButtonCreateDisabled() {

    }

    private void loadView() {
        name = (TextInputEditText) findViewById(R.id.new_customer_name);
        surname = (TextInputEditText) findViewById(R.id.new_customer_surname);
        phoneDescription = (TextInputEditText) findViewById(R.id.new_customer_phonedescription);
        phoneNumber = (TextInputEditText) findViewById(R.id.new_customer_phonenumber);
        coordinateX = (TextInputEditText) findViewById(R.id.new_customer_coordx);
        coordinateY = (TextInputEditText) findViewById(R.id.new_customer_coordy);
    }

    public void addCustomer(View view) {
        getPresenter().addCustomer(getCustomer());

    }

    public Customer getCustomer(){
        Customer customer = new Customer();

        customer.setName(name.getText().toString());
        customer.setSurName(surname.getText().toString());

        ArrayList<PhoneList> phoneLists = new ArrayList<PhoneList>();

        PhoneList phoneList = new PhoneList();
        phoneList.setDescription(phoneDescription.getText().toString());
        phoneList.setNumber(phoneNumber.getText().toString());
        Location location = new Location();
        location.setType("Point");

        Double coordinate[] = {
                Double.valueOf(coordinateX.getText().toString()),
                Double.valueOf(coordinateY.getText().toString())
        };
        location.setCoordinates(coordinate);

        phoneList.setLocation(location);

        phoneLists.add(phoneList);
        customer.setPhoneList(phoneLists);

        return customer;
    }


    @Override
    public void showAlertDialog(int resId) {
        getShowAlertDialog().showAlertDialog(R.string.alert, resId, true, R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().addCustomer(getCustomer());
            }
        }, R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
    }

    @Override
    public void showAlertDialogError(int resId) {
        getShowAlertDialog().showAlertDialog(R.string.error, resId, true, R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().addCustomer(getCustomer());
            }
        }, R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
    }
}
