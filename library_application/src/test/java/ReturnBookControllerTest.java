import hu.inf.unideb.library.controllers.ReturnBookController;
import org.junit.*;

import java.time.LocalDate;

public class ReturnBookControllerTest {

    private final ReturnBookController returnBookController = new ReturnBookController();

    /**
     * Fizetendő bírság:
     * 1 nap - 1 hónap : 2500 Ft
     * 1 hónap - 3 hónap : 5000 Ft
     * 3 hónap - 6 hónap: 7500 Ft
     * 6 hónap - 1 év : 10000 Ft
     * 1 év + : 20000 Ft
     * */
    @Test
    public void amountToBePaidTest() {
        int amount, exceptedAmount;

        amount = returnBookController.amountToBePaid(LocalDate.now());
        exceptedAmount = 0;
        Assert.assertEquals(exceptedAmount, amount);

        amount = returnBookController.amountToBePaid(LocalDate.now().minusDays(1));
        exceptedAmount = 2500;
        Assert.assertEquals(exceptedAmount, amount);

        amount = returnBookController.amountToBePaid(LocalDate.now().minusMonths(2));
        exceptedAmount = 5000;
        Assert.assertEquals(exceptedAmount, amount);

        amount = returnBookController.amountToBePaid(LocalDate.now().minusMonths(4));
        exceptedAmount = 7500;
        Assert.assertEquals(exceptedAmount, amount);

        amount = returnBookController.amountToBePaid(LocalDate.now().minusMonths(7));
        exceptedAmount = 10000;
        Assert.assertEquals(exceptedAmount, amount);

        amount = returnBookController.amountToBePaid(LocalDate.now().minusYears(2));
        exceptedAmount = 20000;
        Assert.assertEquals(exceptedAmount, amount);
    }
}
