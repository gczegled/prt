package hu.inf.unideb.library.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BorrowerDAO {

    private EntityManager entityManager = new EntityManagement().getEntityManager();
    private static ObservableList<Borrower> borrowers =  FXCollections.observableArrayList();

    public void loadBorrowers() {
        borrowers.clear();
        TypedQuery<Borrower> query = entityManager.createQuery("SELECT b FROM Borrower b", Borrower.class);
        borrowers.addAll(query.getResultList());
    }

    public void addNewBorrower(Borrower borrower) {
        entityManager.getTransaction().begin();
        entityManager.persist(borrower);
        entityManager.getTransaction().commit();
        loadBorrowers();
    }

    public void renewBorrowerLibraryCard(Borrower borrower, LocalDate expirationDateOfLibraryCard, int amount) {
        entityManager.getTransaction().begin();
        borrower.setExpirationDateOfLibraryCard(expirationDateOfLibraryCard);
        borrower.setMoneySpent(borrower.getMoneySpent() + amount);
        entityManager.getTransaction().commit();

    }

    public void changeBorrowerMoneySpent(Borrower borrower, int amount) {
        entityManager.getTransaction().begin();
        borrower.setMoneySpent(borrower.getMoneySpent() + amount);
        entityManager.getTransaction().commit();
    }

    public void changeBorrowerReliability(Borrower borrower, int reliability) {
        entityManager.getTransaction().begin();
        borrower.setBorrowerReliability(borrower.getBorrowerReliability() + reliability);
        entityManager.getTransaction().commit();
    }

    public ObservableList<Integer> getLibraryCardIds() {
        return borrowers
                .stream()
                .map(Borrower::getLibraryCardId)
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
    }

    public Borrower getBorrowerByLibraryCardId(int libraryCardId) {
        return borrowers
                .stream()
                .filter(borrowr -> borrowr.getLibraryCardId() == libraryCardId)
                .findFirst()
                .get();
    }

    public Borrower getBorrowerByIndex(int index) {
        return borrowers.get(index);
    }

    public static ObservableList<Borrower> getBorrowers() {
        return borrowers;
    }

    public static void setBorrowers(ObservableList<Borrower> borrowers) {
        BorrowerDAO.borrowers = borrowers;
    }
}
