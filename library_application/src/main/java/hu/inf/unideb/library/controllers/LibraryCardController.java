package hu.inf.unideb.library.controllers;

import hu.inf.unideb.library.models.BorrowerDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import hu.inf.unideb.library.models.Borrower;
import hu.inf.unideb.library.view.Main;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class LibraryCardController {

    @FXML
    private RadioButton rb1;

    @FXML
    private RadioButton rb2;

    @FXML
    private ComboBox<Integer> libraryCardId;

    @FXML
    private TextField borrowerName;

    @FXML
    private TextField borrowerHomeAddress;

    @FXML
    private ComboBox discount;

    @FXML
    private ComboBox periodOfLibraryCard;

    @FXML
    private Button button;

    @FXML
    private void buyLibraryCardScreen() {
        rb2.setSelected(false);
        libraryCardId.setDisable(true);
        libraryCardId.getSelectionModel().clearSelection();
        borrowerName.setText("");
        borrowerName.setEditable(true);
        borrowerHomeAddress.setText("");
        borrowerHomeAddress.setEditable(true);
        discount.getSelectionModel().clearSelection();
        periodOfLibraryCard.getSelectionModel().clearSelection();
        button.setText("Bérlet váltás");
    }

    @FXML
    private void renewTheLibraryCardScreen() {
        rb1.setSelected(false);
        libraryCardId.setDisable(false);
        libraryCardId.getSelectionModel().clearSelection();
        borrowerName.setText("");
        borrowerName.setEditable(false);
        borrowerHomeAddress.setText("");
        borrowerHomeAddress.setEditable(false);
        discount.getSelectionModel().clearSelection();
        periodOfLibraryCard.getSelectionModel().clearSelection();
        button.setText("Bérlet hosszabbítás");
        libraryCardId.setItems(FXCollections.observableArrayList(new BorrowerDAO().getLibraryCardIds()));
    }

    @FXML
    private void loadBorrowerBySelectedLibraryCardId() {
        if(!libraryCardId.getSelectionModel().isEmpty()) {
            Borrower borrower = new BorrowerDAO().getBorrowerByLibraryCardId(libraryCardId.getSelectionModel().getSelectedItem());
            borrowerName.setText(borrower.getBorrowerName());
            borrowerHomeAddress.setText(borrower.getBorrowerHomeAddress());
        }
    }

    @FXML
    private void buyOrRenewLibraryCard() {
        try {
            if(button.getText().equals("Bérlet váltás")) {
                if(borrowerName.getText() == null || borrowerHomeAddress.getText() == null ||
                        periodOfLibraryCard.getSelectionModel().isEmpty()) {
                    throw new NullPointerException();
                }
                else if(borrowerName.getText().matches("[0-9]+")) {
                    throw new IllegalArgumentException();
                }
            }
            else if(button.getText().equals("Bérlet hosszabbítás")) {
                if(libraryCardId.getSelectionModel().isEmpty() || borrowerName.getText() == null ||
                        borrowerHomeAddress.getText() == null || periodOfLibraryCard.getSelectionModel().isEmpty()) {
                    throw new NullPointerException();
                }
            }
            int amount = calculateTheAmountToBePaid(
                    new BorrowerDAO().getBorrowerByLibraryCardId(libraryCardId.getSelectionModel().getSelectedItem()),
                    periodOfLibraryCard.getSelectionModel().getSelectedItem().toString(),
                    discount.getSelectionModel().getSelectedItem().toString(),
                    button.getText());
            ButtonType payButton = new ButtonType("Fizetés");
            ButtonType cancelButton = new ButtonType("Mégse");
            Optional<ButtonType> result = payAlert(amount,payButton,cancelButton).showAndWait();
            if(result.get() == payButton) {
                if (button.getText().equals("Bérlet váltás")) {
                    new BorrowerDAO().addNewBorrower(
                            new Borrower(
                                    generateLibraryCardId(),
                                    borrowerName.getText(),
                                    borrowerHomeAddress.getText(),
                                    calculateExpirationDateOfLibraryCard(
                                            LocalDate.now(),
                                            periodOfLibraryCard.getSelectionModel().getSelectedItem().toString()
                                    ),
                                    amount
                            )
                    );
                }
                else if (button.getText().equals("Bérlet hosszabbítás")) {
                    LocalDate expirationDateOfLibraryCard = new BorrowerDAO()
                            .getBorrowerByLibraryCardId(libraryCardId.getSelectionModel().getSelectedItem())
                            .getExpirationDateOfLibraryCard();
                    Borrower borrower = new BorrowerDAO().getBorrowerByIndex(libraryCardId.getSelectionModel().getSelectedIndex());
                    new BorrowerDAO().renewBorrowerLibraryCard(
                            borrower, calculateExpirationDateOfLibraryCard(
                                    expirationDateOfLibraryCard,
                                    periodOfLibraryCard.getSelectionModel().getSelectedItem().toString()
                            ),
                            amount
                    );
                }
                clearFields();
            }
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nincs elég adat megadva");
            alert.setHeaderText(null);
            alert.setContentText("Nincs az összes mező kitöltve.");
            alert.show();
        }
        catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nem megengedett karakter használata");
            alert.setHeaderText(null);
            alert.setContentText("A név nem megengedett karaktereket tartalmaz.");
            alert.show();
        }
    }

    private Alert payAlert(int amount, ButtonType payButton, ButtonType cancelButton) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bérlet váltás / hosszabbítás megerősítése");
        alert.setHeaderText(null);
        alert.setContentText("A fizetendő összeg: " + amount + " Ft");
        alert.getButtonTypes().setAll(payButton,cancelButton);
        return alert;
    }

    private int generateLibraryCardId() {
        Random rand = new Random();
        int num = rand.nextInt(899999) + 100000;
        List<Integer> leaseIds = new BorrowerDAO().getLibraryCardIds();
        while (leaseIds.contains(num)) {
            num = rand.nextInt(899999) + 100000;
        }
        return num;
    }

    public LocalDate calculateExpirationDateOfLibraryCard(LocalDate expirationDateOfLibraryCard, String period) {
        int plusMonths = 0;
        switch (period) {
            case "1 hónap": plusMonths = 1; break;
            case "6 hónap": plusMonths = 6; break;
            case "12 hónap": plusMonths = 12; break;
        }
        return expirationDateOfLibraryCard.isAfter(LocalDate.now()) ?
                expirationDateOfLibraryCard.plusMonths(plusMonths) : LocalDate.now().plusMonths(plusMonths);
    }

    public int calculateTheAmountToBePaid(Borrower borrower, String period, String discount, String payType) {
        double amount = 0, discountPercentage = 0;
        switch (period) {
            case "1 hónap": amount = 5000; break;
            case "6 hónap": amount = 25000; break;
            case "12 hónap": amount = 45000; break;
        }
        switch (discount) {
            case "Diák": discountPercentage = 0.5; break;
            case "Nyugdíjas": discountPercentage = 0.3; break;
        }
        if(payType.equals("Bérlet hosszabbítás")) {
            if(borrower.getBorrowerReliability() >= 20) {
                discountPercentage += 0.15;
            }
        }
        return (int) (amount - (amount * discountPercentage));
    }

    private void clearFields() {
        if(!libraryCardId.getSelectionModel().isEmpty()) {
            libraryCardId.getSelectionModel().clearSelection();
        }
        borrowerName.clear();
        borrowerHomeAddress.clear();
        discount.getSelectionModel().clearSelection();
        periodOfLibraryCard.getSelectionModel().clearSelection();
    }

    @FXML
    private void back() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/library.fxml"));
        Stage stage = new Main().getStage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

}
