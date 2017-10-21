package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.model.PhoneList;
import co.com.etn.arquitecturamvpbase.presenter.CustomerPresenter;
import co.com.etn.arquitecturamvpbase.repository.CustomerRepository;
import co.com.etn.arquitecturamvpbase.view.BaseActivity;
import co.com.etn.arquitecturamvpbase.view.adapter.CustomerAdapter;

public class CustomerActivity extends BaseActivity<CustomerPresenter> implements ICustomerView {

    private ListView customersListView;
    private CustomerAdapter customerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        setPresenter(new CustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());

        createProgressDialog();

        getPresenter().getCustomers();

        initViews();
    }

    private void initViews() {
        customersListView = (ListView) findViewById(R.id.customer_listView);
    }

    @Override
    public void showCustomersList(final ArrayList<Customer> customers) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(customers);

            }
        });

    }

    private void callAdapter(final ArrayList<Customer> customers) {
        customerAdapter = new CustomerAdapter(this, R.id.customer_listView, customers);
        customersListView.setAdapter(customerAdapter);
        customersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(CustomerActivity.this, (customers.get(position).getPhoneList().get(0)).getNumber(), Toast.LENGTH_LONG).show();
                Customer customer = customers.get(position);
                ArrayList<PhoneList> phoneList = customer.getPhoneList();
                for (PhoneList phone : phoneList) {
                    Double[] coordinates = phone.getLocation().getCoordinates();
                }
                Intent intent = new Intent(CustomerActivity.this, CustomerLocationsActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void showAlertDialog(int resId) {
        getShowAlertDialog().showAlertDialog(R.string.alert, resId, true, R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().getCustomers();
            }
        }, R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
    }

    public void launchNewCustomerActivity(View view) {
        Intent intent = new Intent(this, NewCustomerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getCustomers();
    }
}
