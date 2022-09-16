package book_loaning_app.application.data.impl;

import book_loaning_app.application.data.AuthorDAO;
import book_loaning_app.application.model.Author;
import book_loaning_app.application.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorDAOImplTest {

    @Autowired
    AuthorDAO authorDAO;
    @Autowired
    BookDAOImpl bookDAO;

    Author author1;
    Author author2;
    Book book1;
    Book book2;
    Book book3;
    Book book4;

    @BeforeEach
    void beforeEach(){
        book1 = new Book("1469hg", "Javascript", 11);
        book2 = new Book("1234j", "Java Core development", 21);
        book3 = new Book("186989", "Javascript Scripting", 14);
        book4 = new Book("129770", "JavaEE development", 14);
        bookDAO.create(book1);
        bookDAO.create(book2);
        bookDAO.create(book3);
        bookDAO.create(book4);

        author1 = new Author("authorFirstName", "authorLN");
        author2 = new Author("theAuthorFN", "theLastName");
        authorDAO.create(author1);
        authorDAO.create(author2);

        author1.addBook(book1);
        author1.addBook(book2);
        authorDAO.update(author1);

        author2.addBook(book3);
        author2.addBook(book4);
        authorDAO.update(author2);
    }

    @Test
    void create() {
        //Assert
        assertNotNull(author1);
        assertNotNull(author2);
        assertEquals(1, author1.getAuthorId());
        assertEquals(2, author2.getAuthorId());
    }

    @Test
    void findById() {
        Author expected_1 = authorDAO.findById(author1.getAuthorId());
        Author expected_2 = authorDAO.findById(author2.getAuthorId());

        //Assert
        assertEquals(expected_1, author1);
        assertEquals(expected_2, author2);
    }

    @Test
    void findAll() {
        Collection<Author> authorCollection = authorDAO.findAll();
        ArrayList<Author> authorList = new ArrayList<>(authorCollection);

        //Assert
        assertTrue(authorCollection.size() > 0);
        assertEquals(2, authorCollection.size());
        assertEquals(authorList.get(0), author1);
        assertEquals(authorList.get(1), author2);
    }

    @Test
    @Transactional
    void update() {
        String name_1 = "A-new_First_Name";
        author1.setFirstName(name_1);
        author1.removeBook(book1);
        authorDAO.update(author1);

        Author a_1 = authorDAO.findById(author1.getAuthorId());

        String name_2 = "A-new_Last_Name";
        author2.setLastName(name_2);
        author2.removeBook(book4);
        authorDAO.update(author2);

        Author a_2 = authorDAO.findById(author2.getAuthorId());

        //Assert
        assertEquals(name_1, a_1.getFirstName());
        assertEquals(1, a_1.getWrittenBooks().size());
        assertEquals(name_2, a_2.getLastName());
        assertEquals(1, a_2.getWrittenBooks().size());
    }

    @Test
    void delete() {
        int listSizeBeforeDelete = authorDAO.findAll().size();

        Author a = authorDAO.findById(author2.getAuthorId());

        authorDAO.delete(author2.getAuthorId());

        int listSizeAfterDelete = authorDAO.findAll().size();

        //Assert
        assertNotEquals(listSizeBeforeDelete, listSizeAfterDelete);
        assertEquals(listSizeBeforeDelete-1, listSizeAfterDelete);
    }
}