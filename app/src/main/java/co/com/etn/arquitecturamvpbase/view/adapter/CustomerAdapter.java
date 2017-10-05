package co.com.etn.arquitecturamvpbase.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.view.activity.CustomerActivity;

/**
 * Created by draiven on 10/5/17.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {
    private ArrayList<Customer> customers;
    private Activity context;
    private Customer customer;
    private TextView name;
    private TextView surname;

    public CustomerAdapter(@NonNull CustomerActivity customerActivity, int customer_listView, ArrayList<Customer> customers) {
        super(customerActivity, customer_listView, customers);
        this.context = customerActivity;
        this.customers = customers;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        this.customer = this.customers.get(position);
        loadView(convertView);
        name.setText(customer.getName());
        surname.setText(customer.getSurName());

        return convertView;

    }

    private void loadView(View convertView) {
        name = (TextView) convertView.findViewById(R.id.item_name_customer);
        surname = (TextView) convertView.findViewById(R.id.item_surname_customer);
    }

}
