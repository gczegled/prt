package hu.inf.unideb.library.controllers;

import hu.inf.unideb.library.models.BookDAO;
import hu.inf.unideb.library.models.BorrowerDAO;
import hu.inf.unideb.library.models.EntityManagement;
import hu.inf.unideb.library.models.TransactionDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import hu.inf.unideb.library.view.Main;

public class LibraryController {

    @FXML
    public void initBorrowBookScreen() throws Exception {
        new BookDAO().loadBooks();
        new BorrowerDAO().loadBorrowers();
        screenSwapper("/fxml/borrowbook.fxml");
    }

    @FXML
    public void initReturnBookScreen() throws Exception {
        new TransactionDAO().loadTransactions();
        new BookDAO().loadBooks();
        new BorrowerDAO().loadBorrowers();
        screenSwapper("/fxml/returnbook.fxml");
    }

    @FXML
    public void initLeaseScreen() throws Exception {
        new BorrowerDAO().loadBorrowers();
        screenSwapper("/fxml/librarycard.fxml");
    }

    @FXML
    public void closeApp() throws Exception {
        new EntityManagement().closeEntityManager();
        new Main().getStage().close();
    }

    private void screenSwapper(String fxml) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Main().getStage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
