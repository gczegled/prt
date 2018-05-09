package hu.inf.unideb.library.models;

import javax.persistence.*;
import java.time.LocalDate;



@Entity
@Table(name = "Borrower")
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "libraryCardId")
    private int libraryCardId;
    @Column(name = "borrowerName")
    private String borrowerName;
    @Column(name = "borrowerHomeAddress")
    private String borrowerHomeAddress;
    @Column(name = "borrowerReliability")
    private int borrowerReliability;
    @Column(name = "expirationDateOfLibraryCard")
    private LocalDate expirationDateOfLibraryCard;
    @Column(name = "moneySpent")
    private int moneySpent;

    public Borrower() {}

    public Borrower(int libraryCardId, String borrowerName, String borrowerHomeAddress, LocalDate expirationDateOfLibraryCard, int moneySpent) {
        this.libraryCardId = libraryCardId;
        this.borrowerName = borrowerName;
        this.borrowerHomeAddress = borrowerHomeAddress;
        this.borrowerReliability = 10;
        this.expirationDateOfLibraryCard = expirationDateOfLibraryCard;
        this.moneySpent = moneySpent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLibraryCardId() {
        return libraryCardId;
    }

    public void setLibraryCardId(int libraryCardId) {
        this.libraryCardId = libraryCardId;
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

    public int getBorrowerReliability() {
        return borrowerReliability;
    }

    public void setBorrowerReliability(int borrowerReliability) {
        this.borrowerReliability = borrowerReliability;
    }

    public LocalDate getExpirationDateOfLibraryCard() {
        return expirationDateOfLibraryCard;
    }

    public void setExpirationDateOfLibraryCard(LocalDate expirationDateOfLibraryCard) {
        this.expirationDateOfLibraryCard = expirationDateOfLibraryCard;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(int moneySpent) {
        this.moneySpent = moneySpent;
    }
}


