package co.com.etn.arquitecturamvpbase.repository;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.model.User;
import co.com.etn.arquitecturamvpbase.service.IServices;

/**
 * Created by Lisandro Gómez on 10/17/17.
 */

public class LoginRepository implements ILoginRepository {
    private IServices services;

    public LoginRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public User login(String user, String password) {
        return services.login(user, password);
    }

    @Override
    public User authLogin(String token) {
        return services.authLogin("bearer:" + token);
    }


}
