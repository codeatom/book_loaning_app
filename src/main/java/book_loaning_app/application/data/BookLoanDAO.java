package book_loaning_app.application.data;

import book_loaning_app.application.model.BookLoan;

import java.util.List;

public interface BookLoanDAO {
    BookLoan findById(int id);
    List<BookLoan> findAll();
    BookLoan create(BookLoan bookLoan);
    BookLoan update(BookLoan bookLoan);
    void delete(int id);
}
