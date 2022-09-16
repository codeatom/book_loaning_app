package book_loaning_app.application.data;

import book_loaning_app.application.model.Author;

import java.util.Collection;


public interface AuthorDAO {

    Author create (Author author);

    Author findById(int id);

    Collection<Author> findAll();

    Author update (Author author);

    void delete(int id);
}
