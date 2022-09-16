package book_loaning_app.application.data.impl;

import book_loaning_app.application.model.Details;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DetailsDAOImplTest {

    @Autowired
    DetailsDAOImpl detailsDAO;

    Details details1;
    Details details2;

    @BeforeEach
    void beforeEach(){
        details1 = new Details("mail", "name", LocalDate.parse("2000-01-11"));
        details2 = new Details("mail2", "name2", LocalDate.parse("2000-02-02"));

        detailsDAO.create(details1);
        detailsDAO.create(details2);
    }

    @Test
    void create() {
        assertNotNull(details1);
        assertNotNull(details2);
        assertEquals(1, details1.getDetailsId());
        assertEquals(2, details2.getDetailsId());
    }

    @Test
    void findAll() {
        List<Details> detailsList = detailsDAO.findAll();

        assertTrue(detailsList.size() > 0);
        assertEquals(detailsList.get(0), details1);
        assertEquals(detailsList.get(1), details2);
    }

    @Test
    void findById() {
        Details expected = detailsDAO.findById(details1.getDetailsId());

        //Assert
        assertEquals(expected, details1);
    }

    @Test
    @Transactional
    void update() {
        String eMail = "mails";
        String naMe = "naMe";

        details2.setEmail(eMail);
        details2.setName(naMe);
        detailsDAO.update(details2);

        Details details = detailsDAO.findById(details2.getDetailsId());

        assertEquals(eMail, details.getEmail());
        assertEquals(naMe, details.getName());
    }

    @Test
    void delete() {
        int listSizePreDel = detailsDAO.findAll().size();

        detailsDAO.delete(details2.getDetailsId());

        int listSizePostDel = detailsDAO.findAll().size();

        assertNotEquals(listSizePreDel, listSizePostDel);
        assertEquals(listSizePostDel, listSizePreDel-1);
    }
}