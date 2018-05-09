package hu.inf.unideb.library.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TransactionDAO {
    private EntityManager entityManager = new EntityManagement().getEntityManager();
    private static ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public void loadTransactions() {
        transactions.clear();
        transactions.addAll(entityManager.createQuery("SELECT t FROM Transaction t").getResultList());
    }

    public void addNewTransaction(Transaction transaction) {
        entityManager.getTransaction().begin();
        entityManager.persist(transaction);
        entityManager.getTransaction().commit();
    }

    public void closeTransaction(Transaction transaction) {
        entityManager.getTransaction().begin();
        transaction.setStatus("Visszaadva");
        entityManager.getTransaction().commit();
    }

    public ObservableList<Integer> getTransactionIds() {
        return transactions
                .stream()
                .map(Transaction::getTransactionId)
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
    }

    public ObservableList<Integer> getActiveTransactionIds() {
        return transactions
                .stream()
                .filter(transaction -> transaction.getStatus().equals("Kölcsönzés alatt"))
                .map(Transaction::getTransactionId)
                .collect(Collectors.collectingAndThen(toList(),FXCollections::observableArrayList));
    }

    public Transaction getTransactionByTransactionId(int transactionId) {
        return transactions
                .stream()
                .filter(transaction -> transaction.getTransactionId() == transactionId)
                .findFirst()
                .get();
    }

    public ObservableList<Transaction> getActiveTransactions() {
        return transactions
                .stream()
                .filter(transaction -> transaction.getStatus().equals("Kölcsönzés alatt"))
                .collect(Collectors.collectingAndThen(toList(),FXCollections::observableArrayList));
    }

    public static ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public static void setTransactions(ObservableList<Transaction> transactions) {
        TransactionDAO.transactions = transactions;
    }
}
