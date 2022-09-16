package book_loaning_app.application.data.impl;

import book_loaning_app.application.data.AppUserDAO;
import book_loaning_app.application.data.BookDAO;
import book_loaning_app.application.model.AppUser;
import book_loaning_app.application.model.Book;
import book_loaning_app.application.model.BookLoan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookLoanDAOImplTest {

    @Autowired
    BookLoanDAOImpl bookLoanDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    AppUserDAO appUserDAO;

    BookLoan bookLoan1;
    BookLoan bookLoan2;
    Book book1;
    Book book2;
    AppUser appUser1;
    AppUser appUser2;


    @BeforeEach
    void beforeEach(){
        book1 = new Book("1469", "Javascript", 11);
        book2 = new Book("1234", "Java development", 8);
        bookDAO.create(book1);
        bookDAO.create(book2);

        appUser1 = new AppUser("userName1", "passWord!", LocalDate.parse("2022-09-14"));
        appUser2 = new AppUser("userName2", "passWord2!", LocalDate.parse("2022-09-12"));
        appUserDAO.create(appUser1);
        appUserDAO.create(appUser2);

        bookLoan1 = new BookLoan(LocalDate.now(), LocalDate.MAX, appUser1, book1);
        bookLoan2 = new BookLoan(LocalDate.now(), LocalDate.MAX, appUser2, book2);
        bookLoanDAO.create(bookLoan1);
        bookLoanDAO.create(bookLoan2);

        bookLoan1.setBorrower(appUser1);
        bookLoanDAO.update(bookLoan1);

        bookLoan2.setBorrower(appUser2);
        bookLoanDAO.update(bookLoan2);
    }

    @Test
    void create() {
        assertNotNull(bookLoan1);
        assertNotNull(bookLoan2);
        assertEquals(1, bookLoan1.getLoanId());
        assertEquals(2, bookLoan2.getLoanId());
    }

    @Test
    void findAll() {
        Collection<BookLoan> bookLoansList = bookLoanDAO.findAll();

        //Assert
        assertTrue(bookLoansList.size() > 0);
        assertEquals(bookLoansList.size(), 2);
        assertEquals(bookLoansList.iterator().next().getBorrower(), bookLoan1.getBorrower());
        assertEquals(bookLoansList.iterator().next().getBook(), bookLoan1.getBook());
    }

    @Test
    void findById() {
        BookLoan expected = bookLoanDAO.findById(bookLoan1.getLoanId());

        //Assert
        assertEquals(expected.getBorrower(), bookLoan1.getBorrower());
        assertEquals(expected.getBook(), bookLoan1.getBook());
    }

    @Test
    @Transactional
    void update() {
        boolean returned = true;
        LocalDate dueDate = LocalDate.parse("2022-12-12");

        bookLoan2.setReturned(returned);
        bookLoan2.setDueDate(dueDate);
        bookLoanDAO.update(bookLoan2);

        BookLoan bookLoan = bookLoanDAO.findById(bookLoan2.getLoanId());

        assertTrue(bookLoan.isReturned());
        assertEquals(dueDate, bookLoan.getDueDate());
    }

    @Test
    void delete() {
        int listSizePreDel = bookLoanDAO.findAll().size();

        bookLoanDAO.delete(bookLoan2.getLoanId());

        int listSizePostDel = bookLoanDAO.findAll().size();

        assertNotEquals(listSizePreDel, listSizePostDel);
        assertEquals(listSizePostDel, listSizePreDel-1);
    }
}