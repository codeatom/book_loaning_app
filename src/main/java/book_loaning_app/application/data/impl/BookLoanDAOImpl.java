package book_loaning_app.application.data.impl;

import book_loaning_app.application.data.BookLoanDAO;
import book_loaning_app.application.model.BookLoan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Transactional
@Repository
public class BookLoanDAOImpl implements BookLoanDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookLoan create(BookLoan bookLoan) {
        entityManager.persist(bookLoan);
        return bookLoan;
    }

    @Override
    @Transactional(readOnly = true)
    public BookLoan findById(int id) {

        return entityManager.find(BookLoan.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookLoan> findAll() {
        TypedQuery<BookLoan> query = entityManager.createQuery("SELECT bl FROM BookLoan bl", BookLoan.class);
        return query.getResultList();
    }

    @Override
    public BookLoan update(BookLoan bookLoan) {
        entityManager.merge(bookLoan);
        return bookLoan;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(findById(id));
    }
}
