package hu.inf.unideb.library.controllers;

import hu.inf.unideb.library.models.*;
import hu.inf.unideb.library.view.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReturnBookController {

    @FXML
    private ComboBox transactionId;

    @FXML
    private TextField bookTitle;

    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookId;

    @FXML
    private TextField borrowerName;

    @FXML
    private TextField borrowerHomeAddress;

    @FXML
    private TextField libraryCardId;

    @FXML
    private TextField dateOfTransaction;

    public void initialize() {
        loadTransactionIds();
    }

    private void loadTransactionIds() {
        transactionId.getItems().clear();
        transactionId.getItems().addAll(new TransactionDAO().getActiveTransactionIds());
    }

    @FXML
    private void loadTransactionInformations() {
        if(!transactionId.getSelectionModel().isEmpty()) {
            TransactionDAO transactionDAO = new TransactionDAO();
            Transaction transaction = transactionDAO.getActiveTransactions().get(transactionId.getSelectionModel().getSelectedIndex());
            bookTitle.setText(transaction.getBookTitle());
            bookAuthor.setText(transaction.getBookAuthor());
            bookId.setText(String.valueOf(transaction.getBookId()));
            borrowerName.setText(transaction.getBorrowerName());
            borrowerHomeAddress.setText(transaction.getBorrowerHomeAddress());
            libraryCardId.setText(String.valueOf(transaction.getLibraryCardId()));
            dateOfTransaction.setText(transaction.getDateOfTransaction().toString());
        }
    }

    @FXML
    private void returnBook() {
        if(!transactionId.getSelectionModel().isEmpty()) {
            TransactionDAO transactionDAO = new TransactionDAO();
            BookDAO bookDAO  = new BookDAO();
            Transaction transaction = transactionDAO.getActiveTransactions().get(transactionId.getSelectionModel().getSelectedIndex());
            Book book = bookDAO.getBookByBookId(transaction.getBookId());
            check(transaction);
            transactionDAO.closeTransaction(transaction);
            bookDAO.changeBookQuantity(book, 1);
            succesReturnAlert();
            clearFields();
            loadTransactionIds();
        }
        else {
            failedReturnBookAlert();
        }
    }

    private void check(Transaction transaction) {
        Borrower borrower = new BorrowerDAO().getBorrowerByLibraryCardId(transaction.getLibraryCardId());
        if(LocalDate.now().isAfter(transaction.getDateOfTransaction().plusMonths(1))) {
            new BorrowerDAO().changeBorrowerReliability(borrower,-5);
            int amount = amountToBePaid(transaction.getDateOfTransaction().plusMonths(1));
            new BorrowerDAO().changeBorrowerMoneySpent(borrower,amount);
            mulctAlert(amount);
        }
        else {
            new BorrowerDAO().changeBorrowerReliability(borrower,2);
        }
    }

    public int amountToBePaid(LocalDate expirationDateOfTransaction) {
        int amount = 0;
        if(LocalDate.now().isAfter(expirationDateOfTransaction.plusYears(1))) {
            amount += 20000;
        }
        else if(LocalDate.now().isAfter(expirationDateOfTransaction.plusMonths(6))) {
            amount += 10000;
        }
        else if(LocalDate.now().isAfter(expirationDateOfTransaction.plusMonths(3))) {
            amount += 7500;
        }
        else if(LocalDate.now().isAfter(expirationDateOfTransaction.plusMonths(1))) {
            amount += 5000;
        }
        else if(LocalDate.now().isAfter(expirationDateOfTransaction)){
            amount += 2500;
        }
        return amount;
    }

    private void mulctAlert(int amount) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lejárt kölcsönzési idő");
        alert.setHeaderText(null);
        alert.setContentText("A kölcsönzési idő túllépve.\nA fizetendő összeg: " + amount + " Ft");
        alert.showAndWait();
    }

    private void failedReturnBookAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Nincs kiválasztva tranzakció!");
        alert.setHeaderText(null);
        alert.setContentText("Nincs kiválaszta tranzakció, így a művelet nem hajtható végre.");
        alert.show();
    }

    private void succesReturnAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sikeres könyv visszaadás");
        alert.setHeaderText(null);
        alert.setContentText("A könyv sikeres vissza lett adva.");
        alert.show();
    }

    private void clearFields() {
        transactionId.getSelectionModel().clearSelection();
        bookTitle.setText(null);
        bookAuthor.setText(null);
        bookId.setText(null);
        borrowerName.setText(null);
        borrowerHomeAddress.setText(null);
        libraryCardId.setText(null);
        dateOfTransaction.setText(null);
    }

    @FXML
    public void back() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/library.fxml"));
        Stage stage = new Main().getStage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
