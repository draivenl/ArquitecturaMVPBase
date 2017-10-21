package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.Context;

import co.com.etn.arquitecturamvpbase.model.User;
import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by Lisandro Gómez on 10/17/17.
 */

public interface ILoginView extends IBaseView {
    void loginSuccess(User user);

}
