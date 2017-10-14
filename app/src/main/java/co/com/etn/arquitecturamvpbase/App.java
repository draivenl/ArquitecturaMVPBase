package co.com.etn.arquitecturamvpbase;

import android.app.Application;
import android.content.IntentFilter;

import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.receivers.NetworkStateReceiver;
import co.com.etn.arquitecturamvpbase.synchronizer.Synchronizer;

/**
 * Created by Lisandro Gómez on 10/7/17.
 */

public class App extends Application {
    public static Database db;

    private final NetworkStateReceiver NETWORK_STATE_RECEIVER = new NetworkStateReceiver();


    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase();
        registerNetworkReceiver();
    }

    @Override
    public void onTerminate() {
        db.close();
        super.onTerminate();
    }
    private void initDataBase() {
        db = new Database(this);
        db.open();
    }
    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(NETWORK_STATE_RECEIVER, filter);
    }
    public void onNetworkStateChanged(boolean isConnected) {
        Synchronizer.getInstance().executeSyncLocalToServer(isConnected);

    }
}
