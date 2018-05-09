package hu.inf.unideb.library.models;

import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "transactionid")
    private int transactionId;
    @Column(name = "booktitle")
    private String bookTitle;
    @Column(name = "bookauthor")
    private String bookAuthor;
    @Column(name = "bookid")
    private int bookId;
    @Column(name = "borrowername")
    private String borrowerName;
    @Column(name = "borrowerhomeaddress")
    private String borrowerHomeAddress;
    @Column(name = "librarycardid")
    private int libraryCardId;
    @Column(name = "dateoftransaction")
    private LocalDate dateOfTransaction;
    @Column(name = "status")
    private String status;
    @Column(name = "returndate")
    private LocalDate returnDate;

    public Transaction() {}

    public Transaction(int transactionId, String bookTitle, String bookAuthor, int bookId, String borrowerName, String borrowerHomeAddress, int libraryCardId, LocalDate dateOfTransaction) {
        this.transactionId = transactionId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.borrowerHomeAddress = borrowerHomeAddress;
        this.libraryCardId = libraryCardId;
        this.dateOfTransaction = dateOfTransaction;
        this.status = "Kölcsönzés alatt";
        this.returnDate = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerHomeAddress() {
        return borrowerHomeAddress;
    }

    public void setBorrowerHomeAddress(String borrowerHomeAddress) {
        this.borrowerHomeAddress = borrowerHomeAddress;
    }

    public int getLibraryCardId() {
        return libraryCardId;
    }

    public void setLibraryCardId(int libraryCardId) {
        this.libraryCardId = libraryCardId;
    }

    public LocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(LocalDate dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
