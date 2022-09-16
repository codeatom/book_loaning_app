package book_loaning_app.application.data.impl;

import book_loaning_app.application.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookDAOImplTest {

    @Autowired
    BookDAOImpl bookDAO;

    Book book1;
    Book book2;

    @BeforeEach
    void beforeEach(){
        book1 = new Book("1469", "Javascript", 11);
        book2 = new Book("1234", "Java development", 8);

        bookDAO.create(book1);
        bookDAO.create(book2);
    }

    @Test
    void create() {
        assertNotNull(book1);
        assertNotNull(book2);
        assertEquals(1, book1.getBookId());
        assertEquals(2, book2.getBookId());
    }

    @Test
    void findAll() {
        Collection<Book>  bookList = bookDAO.findAll();

        //Assert
        assertTrue(bookList.size() > 0);
        assertEquals(2, bookList.size());
        assertEquals(bookList.iterator().next(), book1);
    }

    @Test
    void findById() {
        Book expected = bookDAO.findById(book1.getBookId());

        //Assert
        assertEquals(expected, book1);
    }

    @Test
    @Transactional
    void update() {
        String isbn = "2233";
        String title = "naMe";

        book2.setIsbn(isbn);
        book2.setTitle(title);
        bookDAO.update(book2);

        Book book = bookDAO.findById(book2.getBookId());

        assertEquals(isbn, book.getIsbn());
        assertEquals(title, book.getTitle());
    }

    @Test
    void delete() {
        int listSizePreDel = bookDAO.findAll().size();

        bookDAO.delete(book2.getBookId());

        int listSizePostDel = bookDAO.findAll().size();

        assertNotEquals(listSizePreDel, listSizePostDel);
        assertEquals(listSizePostDel, listSizePreDel-1);
    }

}