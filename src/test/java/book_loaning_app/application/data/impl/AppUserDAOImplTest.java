package book_loaning_app.application.data.impl;

import book_loaning_app.application.model.AppUser;
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
class AppUserDAOImplTest {

    @Autowired
    AppUserDAOImpl appUserDAO;

    AppUser appUser_1;
    AppUser appUser_2;

    @BeforeEach
    void BeforeEach(){
        appUser_1 = new AppUser("userName1", "passWord!", LocalDate.parse("2022-09-14"));
        appUser_2 = new AppUser("userName2", "passWord2!", LocalDate.parse("2022-09-12"));

        appUserDAO.create(appUser_1);
        appUserDAO.create(appUser_2);
    }

    @Test
    void create() {

        //Assert
        assertNotNull(appUser_1);
        assertNotNull(appUser_2);
        assertEquals(1, appUser_1.getAppUserId());
        assertEquals(2, appUser_2.getAppUserId());
    }

    @Test
    void findAll() {
        List<AppUser> appUserList = appUserDAO.findAll();


        //Assert
        assertTrue(appUserList.size() > 0);

        assertEquals(appUserList.get(0), appUser_1);
        assertEquals(appUserList.get(1), appUser_2);
    }

    @Test
    void findById() {
        AppUser expected = appUserDAO.findById(appUser_1.getAppUserId());

        //Assert
        assertEquals(expected, appUser_1);
    }

    @Test
    @Transactional
    void update() {
        //Arrange
        String userName = "userName_3";
        String password = "pass";

        //Act
        appUser_2.setUsername(userName);
        appUser_2.setPassword(password);
        appUserDAO.update(appUser_2);

        AppUser appUser = appUserDAO.findById(appUser_2.getAppUserId());

        //Assert
        assertEquals(userName, appUser.getUsername());
        assertEquals(password, appUser.getPassword());
    }

    @Test
    void delete() {
        int listSize = appUserDAO.findAll().size();

        appUserDAO.delete(appUser_1.getAppUserId());

        int newListSize = appUserDAO.findAll().size();

        //Assert
        assertNotEquals(listSize, newListSize);
        assertEquals(newListSize, listSize-1);
    }
}