package book_loaning_app.application.data;

import book_loaning_app.application.model.AppUser;

import java.util.List;

public interface AppUserDAO {
    AppUser findById(int id);
    List<AppUser> findAll();
    AppUser create(AppUser appUser);
    AppUser update(AppUser appUser);
    void delete(int id);
}
