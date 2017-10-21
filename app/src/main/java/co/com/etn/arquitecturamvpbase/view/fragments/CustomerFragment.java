package co.com.etn.arquitecturamvpbase.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.presenter.CustomerPresenter;
import co.com.etn.arquitecturamvpbase.presenter.ProductPresenter;
import co.com.etn.arquitecturamvpbase.repository.CustomerRepository;
import co.com.etn.arquitecturamvpbase.view.BaseFragment;
import co.com.etn.arquitecturamvpbase.view.activity.CustomerActivity;
import co.com.etn.arquitecturamvpbase.view.activity.ICustomerView;
import co.com.etn.arquitecturamvpbase.view.adapter.CustomerAdapter;

/**
 * Created by Lisandro Gómez on 10/14/17.
 */

public class CustomerFragment extends BaseFragment<CustomerPresenter> implements ICustomerView{

    private ListView customersListView;
    private CustomerAdapter customerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_customer, container, false);

        setPresenter(new CustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this, getValidateInternet());


        getBaseActivity().createProgressDialog();

        getPresenter().getCustomers();

        initViews(view);

        return view;
    }

    @Override
    public void showCustomersList(final ArrayList<Customer> customers) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callAdapter(customers);

            }
        });
    }

    @Override
    public void showAlertDialog(int resId) {

    }


    private void callAdapter(final ArrayList<Customer> customers) {
        customerAdapter = new CustomerAdapter(getActivity(), R.id.customer_listView, customers);
        customersListView.setAdapter(customerAdapter);
        customersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), (customers.get(position).getPhoneList().get(0)).getNumber(), Toast.LENGTH_LONG).show();

            }
        });
    }
    private void initViews(View view) {
        customersListView = (ListView) view.findViewById(R.id.customer_listView);
    }

}
