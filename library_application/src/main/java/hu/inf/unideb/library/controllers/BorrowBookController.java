package hu.inf.unideb.library.controllers;

import hu.inf.unideb.library.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import hu.inf.unideb.library.view.Main;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class BorrowBookController {

    @FXML
    private ComboBox<String> bookTitle;

    @FXML
    private TextField author;

    @FXML
    private TextField bookId;

    @FXML
    private TextField currentNumberOfPieces;

    @FXML
    private ComboBox libraryCardId;

    @FXML
    private TextField borrowerName;

    @FXML
    private TextField borrowerHomeAddress;

    @FXML
    private TextField expirationDateOfLibraryCard;

    @FXML
    private Button borrowBookButton;

    public void initialize() {
        bookTitle.getItems().clear();
        bookTitle.getItems().addAll(new BookDAO().getBookTitles());
        libraryCardId.getItems().clear();
        libraryCardId.getItems().addAll(new BorrowerDAO().getLibraryCardIds());
    }

    @FXML
    private void loadBookInformations() {
        if(!bookTitle.getSelectionModel().isEmpty()) {
            Book book = new BookDAO().getBookByIndex(bookTitle.getSelectionModel().getSelectedIndex());
            author.setText(book.getAuthor());
            bookId.setText(String.valueOf(book.getBookid()));
            currentNumberOfPieces.setText(String.valueOf(book.getCurrentNumberOfPieces()));
            checkCurrentNumberOfBook(book);
        }
    }

    private void checkCurrentNumberOfBook(Book book) {
        if(book.getCurrentNumberOfPieces() == 0) {
            borrowBookButton.setDisable(true);
            currentNumberOfPieces.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A könyv nem kölcsönözhető");
            alert.setHeaderText(null);
            alert.setContentText("Ebből a könyvből nem áll rendelkezésre elegendő példány a kölcsönzéshez.");
            alert.show();
        }
        else if(!expirationDateOfLibraryCard.isDisabled()){
            borrowBookButton.setDisable(false);
            currentNumberOfPieces.setDisable(false);
        }
    }

    @FXML
    private void loadBorrowerInformations() {
        if(!libraryCardId.getSelectionModel().isEmpty()) {
            Borrower borrower = new BorrowerDAO().getBorrowerByIndex(libraryCardId.getSelectionModel().getSelectedIndex());
            borrowerName.setText(borrower.getBorrowerName());
            borrowerHomeAddress.setText(borrower.getBorrowerHomeAddress());
            expirationDateOfLibraryCard.setText(borrower.getExpirationDateOfLibraryCard().toString());
            checkLibraryCardValidity(borrower);
        }
    }

    private void checkLibraryCardValidity(Borrower borrower) {
        if(LocalDate.now().isAfter(borrower.getExpirationDateOfLibraryCard())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lejárt bérlet");
            alert.setHeaderText(null);
            alert.setContentText("A kölcsönzőhöz tartozó bérlet lejárt.");
            alert.show();
            borrowBookButton.setDisable(true);
            expirationDateOfLibraryCard.setDisable(true);
        }
        else if(!currentNumberOfPieces.isDisabled()){
            borrowBookButton.setDisable(false);
            expirationDateOfLibraryCard.setDisable(false);
        }
    }

    @FXML
    private void borrowBook() {
        if(!bookTitle.getSelectionModel().isEmpty() && !libraryCardId.getSelectionModel().isEmpty()) {
            Book book = new BookDAO().getBookByIndex(bookTitle.getSelectionModel().getSelectedIndex());
            Borrower borrower = new BorrowerDAO().getBorrowerByIndex(libraryCardId.getSelectionModel().getSelectedIndex());
            new TransactionDAO().addNewTransaction(
                    new Transaction(
                            generateTransactionId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getBookid(),
                            borrower.getBorrowerName(),
                            borrower.getBorrowerHomeAddress(),
                            borrower.getLibraryCardId(),
                            LocalDate.now()
                    )
            );
            new BookDAO().changeBookQuantity(book, -1);
            borrowAlert();
            clearFields();
        }
        else {
            failedBorrowAlert();
        }
    }

    private int generateTransactionId() {
        Random rand = new Random();
        int num = rand.nextInt(899999) + 100000;
        List<Integer> transactionIds = new TransactionDAO().getTransactionIds();
        while (transactionIds.contains(num)) {
            num = rand.nextInt(899999) + 100000;
        }
        return num;
    }

    private void borrowAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A könyv kölcsönzés sikeres volt!");
        alert.setHeaderText(null);
        alert.setContentText("A könyv kikölcsönözve " + LocalDate.now().plusMonths(1) + "-ig");
        alert.show();
    }

    private void failedBorrowAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("A könyv kölcsönzés nem sikerült!");
        alert.setHeaderText(null);
        alert.setContentText("Nincs az összes mező kitöltve!");
        alert.show();
    }

    private void clearFields() {
        bookTitle.getSelectionModel().clearSelection();
        author.setText(null);
        bookId.setText(null);
        currentNumberOfPieces.setText(null);
        libraryCardId.getSelectionModel().clearSelection();
        borrowerName.setText(null);
        borrowerHomeAddress.setText(null);
        expirationDateOfLibraryCard.setText(null);
    }

    @FXML
    public void back() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/library.fxml"));
        Stage stage = new Main().getStage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
