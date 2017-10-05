package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import co.com.etn.arquitecturamvpbase.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchProductsActivity(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    public void launchCustomersActivity(View view) {
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }
}
