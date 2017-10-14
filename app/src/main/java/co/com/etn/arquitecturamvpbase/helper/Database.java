package co.com.etn.arquitecturamvpbase.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import co.com.etn.arquitecturamvpbase.dao.ProductDao;

/**
 * Created by Lisandro Gómez on 10/7/17.
 */

public class Database {
    private final Context context;
    private DatabaseHelper dbHelper;

    //DAO´S

    public static ProductDao productDao;


    public Database(Context context) {
        this.context = context;
    }

    public Database open(){
        try {
            dbHelper = new DatabaseHelper(context);
            SQLiteDatabase sdb = dbHelper.getWritableDatabase();
            productDao = new ProductDao(sdb);
            return this;
        }catch (SQLException ex){
            Log.e("SQL OPEN ", ex.getMessage());
            throw ex;
        }
    }

    public void close(){
        dbHelper.close();
    }
}
