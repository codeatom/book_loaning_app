package book_loaning_app.application.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "BOOKLOAN")
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @Column(name = "loandate")
    private LocalDate loanDate;

    @Column(name = "duedate")
    private LocalDate dueDate;

    @Column(name = "returned")
    private boolean returned;

    @ManyToOne
    @JoinTable(name = "bookloan_appuser", joinColumns = @JoinColumn(name = "bookloan_id"), inverseJoinColumns = @JoinColumn(name = "appuser_id"))
    private AppUser borrower;

    @ManyToOne
    @JoinColumn(name="book_id")
//  @JoinTable(name = "book_bookloan", joinColumns = @JoinColumn(name = "bookloan_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Book book;

    public BookLoan() {
    }

    public BookLoan(LocalDate loanDate, LocalDate dueDate, AppUser borrower, Book book) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.borrower = borrower;
        this.book = book;
        this.returned = false;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public AppUser getBorrower() {
        return borrower;
    }

    public void setBorrower(AppUser borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoan bookLoan = (BookLoan) o;
        return returned == bookLoan.returned && Objects.equals(loanId, bookLoan.loanId) && Objects.equals(loanDate, bookLoan.loanDate) && Objects.equals(dueDate, bookLoan.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanDate, dueDate, returned);
    }
}
