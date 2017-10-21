package co.com.etn.arquitecturamvpbase.repository;

import co.com.etn.arquitecturamvpbase.model.User;
import co.com.etn.arquitecturamvpbase.service.IServices;

/**
 * Created by Lisandro Gómez on 10/17/17.
 */

interface ILoginRepository {
    public User login(String user, String password);
    public User authLogin(String token);
}
