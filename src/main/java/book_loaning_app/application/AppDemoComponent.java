package book_loaning_app.application;

import book_loaning_app.application.data.AppUserDAO;
import book_loaning_app.application.data.AuthorDAO;
import book_loaning_app.application.data.BookDAO;
import book_loaning_app.application.data.DetailsDAO;
import book_loaning_app.application.data.impl.BookLoanDAOImpl;
import book_loaning_app.application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Profile("!test")
@Component
public class AppDemoComponent implements CommandLineRunner {

    @Autowired
    BookLoanDAOImpl bookLoanDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    AppUserDAO appUserDAO;
    @Autowired
    DetailsDAO detailsDAO;
    @Autowired
    AuthorDAO authorDAO;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Details details1 = new Details("mail", "name", LocalDate.parse("2000-01-11"));
        Details details2 = new Details("mail2", "name2", LocalDate.parse("2000-02-02"));
        detailsDAO.create(details1);
        detailsDAO.create(details2);

        AppUser appUser1 = new AppUser("userName1", "passWord!", LocalDate.parse("2022-09-14"));
        AppUser appUser2 = new AppUser("userName2", "passWord2!", LocalDate.parse("2022-09-12"));
        appUserDAO.create(appUser1);
        appUserDAO.create(appUser2);

        appUser1.setUserDetails(details1);
        appUser2.setUserDetails(details2);

        Book book1 = new Book("14698", "Javascript", 10);
        Book book2 = new Book("12347", "Java development", 21);
        Book book3 = new Book("186989", "Javascript Scripting", 14);
        Book book4 = new Book("129770", "JavaEE development", 14);
        bookDAO.create(book1);
        bookDAO.create(book2);
        bookDAO.create(book3);
        bookDAO.create(book4);

        BookLoan bookLoan1 = new BookLoan(LocalDate.parse("2022-09-15"), LocalDate.parse("2022-09-16"), appUser1, book1);
        BookLoan bookLoan2 = new BookLoan(LocalDate.parse("2022-09-15"), LocalDate.parse("2022-09-14"), appUser2, book2);
        BookLoan bookLoan3 = new BookLoan(LocalDate.parse("2022-09-15"), LocalDate.parse("2022-09-14"), appUser2, book4);
        bookLoanDAO.create(bookLoan1);
        bookLoanDAO.create(bookLoan2);
        bookLoanDAO.create(bookLoan3);

        Author author1 = new Author("authorFirstName", "authorLN");
        Author author2 = new Author("theAuthorFN", "theLastName");
        authorDAO.create(author1);
        authorDAO.create(author2);

        author1.addBook(book1);
        author1.addBook(book2);
        authorDAO.update(author1);

        author2.addBook(book3);
        author2.addBook(book4);
        authorDAO.update(author2);
    }
}
