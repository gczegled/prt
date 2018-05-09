import hu.inf.unideb.library.controllers.LibraryCardController;
import hu.inf.unideb.library.models.Borrower;
import org.junit.*;

import java.time.LocalDate;

public class LibraryCardControllerTest {
    private final LibraryCardController libraryCardController = new LibraryCardController();
    private Borrower borrower = new Borrower(123456, "Test Borrower", "Debrecen", LocalDate.now().plusMonths(12), 45000);

    /**
     * Időtartam árak:
     * 1 hónap: 5000 Ft, 6 hónap: 25000 Ft, 12 hónap: 45000 Ft
     * Kedvezmények:
     * Diák : 50%, Nyugdíjas : 30% , ezen felül 15% ha a megbízhatóság 20-as.
     */
    @Test
    public void calculateTheAmountToBePaidTest() {
        int amount, exceptedAmount;
        /*Nincs kedvezmény*/
        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"12 hónap", "Nincs", "Bérlet váltás");
        exceptedAmount = 45000;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"6 hónap", "Nincs", "Bérlet váltás");
        exceptedAmount = 25000;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"1 hónap", "Nincs", "Bérlet váltás");
        exceptedAmount = 5000;
        Assert.assertEquals(exceptedAmount, amount);

        /*Diák kedvezmény*/
        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"12 hónap", "Diák", "Bérlet váltás");
        exceptedAmount = 22500;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"6 hónap", "Diák", "Bérlet váltás");
        exceptedAmount = 12500;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"1 hónap", "Diák", "Bérlet váltás");
        exceptedAmount = 2500;
        Assert.assertEquals(exceptedAmount, amount);

        /*Nyugdíjas kedvezmény*/
        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"12 hónap", "Nyugdíjas", "Bérlet váltás");
        exceptedAmount = 31500;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"6 hónap", "Nyugdíjas", "Bérlet váltás");
        exceptedAmount = 17500;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"1 hónap", "Nyugdíjas", "Bérlet váltás");
        exceptedAmount = 3500;
        Assert.assertEquals(exceptedAmount, amount);

        /*Megbízhatósági kedvezmény*/
        borrower.setBorrowerReliability(20);
        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"12 hónap", "Nincs", "Bérlet hosszabbítás");
        exceptedAmount = 38250;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"6 hónap", "Nincs", "Bérlet hosszabbítás");
        exceptedAmount = 21250;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"1 hónap", "Nincs", "Bérlet hosszabbítás");
        exceptedAmount = 4250;
        Assert.assertEquals(exceptedAmount, amount);

        /*Diák + megbízhatósági kedvezmény*/
        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"12 hónap", "Diák", "Bérlet hosszabbítás");
        exceptedAmount = 15750;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"6 hónap", "Diák", "Bérlet hosszabbítás");
        exceptedAmount = 8750;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"1 hónap", "Diák", "Bérlet hosszabbítás");
        exceptedAmount = 1750;
        Assert.assertEquals(exceptedAmount, amount);

        /*Nyugdíjas + megbízhatósági kedvezmény*/
        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"12 hónap", "Nyugdíjas", "Bérlet hosszabbítás");
        exceptedAmount = 24750;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"6 hónap", "Nyugdíjas", "Bérlet hosszabbítás");
        exceptedAmount = 13750;
        Assert.assertEquals(exceptedAmount, amount);

        amount = libraryCardController.calculateTheAmountToBePaid(borrower,"1 hónap", "Nyugdíjas", "Bérlet hosszabbítás");
        exceptedAmount = 2750;
        Assert.assertEquals(exceptedAmount, amount);
    }

    @Test
    public void calculateExpirationDateOfLibraryCardTest() {
        LocalDate localDate, expectedLocalDate;

        localDate = libraryCardController.calculateExpirationDateOfLibraryCard(LocalDate.now().plusMonths(1), "1 hónap");
        expectedLocalDate = LocalDate.now().plusMonths(2);
        Assert.assertEquals(expectedLocalDate, localDate);

        localDate = libraryCardController.calculateExpirationDateOfLibraryCard(LocalDate.now().plusMonths(1), "6 hónap");
        expectedLocalDate = LocalDate.now().plusMonths(7);
        Assert.assertEquals(expectedLocalDate, localDate);

        localDate = libraryCardController.calculateExpirationDateOfLibraryCard(LocalDate.now().plusMonths(1), "12 hónap");
        expectedLocalDate = LocalDate.now().plusMonths(13);
        Assert.assertEquals(expectedLocalDate, localDate);

        localDate = libraryCardController.calculateExpirationDateOfLibraryCard(LocalDate.now().minusMonths(1), "1 hónap");
        expectedLocalDate = LocalDate.now().plusMonths(1);
        Assert.assertEquals(expectedLocalDate, localDate);

        localDate = libraryCardController.calculateExpirationDateOfLibraryCard(LocalDate.now().minusMonths(1), "6 hónap");
        expectedLocalDate = LocalDate.now().plusMonths(6);
        Assert.assertEquals(expectedLocalDate, localDate);

        localDate = libraryCardController.calculateExpirationDateOfLibraryCard(LocalDate.now().minusMonths(1), "12 hónap");
        expectedLocalDate = LocalDate.now().plusMonths(12);
        Assert.assertEquals(expectedLocalDate, localDate);
    }
}
