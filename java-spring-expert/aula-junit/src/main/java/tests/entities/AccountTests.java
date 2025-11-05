package tests.entities;

import entities.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.factory.AccountFactory;

public class AccountTests {

    @Test
    public void depositShouldIncreaseBalanceAndDiscountFeeWhenPositiveAmount() {
        double amount = 200.0;
        double expectedValue = 196.0;
        Account account = AccountFactory.createEmptyAccount();

        account.deposit(amount);

        Assertions.assertEquals(expectedValue, account.getBalance());
    }

    @Test
    public void depositShouldDoNothingWhenNegativeAmount() {
        double amount = -200.0;
        double expectedValue = 100.0;
        Account account = AccountFactory.createAccount(expectedValue);

        account.deposit(amount);

        Assertions.assertEquals(expectedValue, account.getBalance());
    }

    @Test
    public void fullWithdrawShouldClearBalanceAndReturnFullBalance() {
        double expectedValue = 0.0;
        double initialBalance = 800.0;
        Account account = AccountFactory.createAccount(initialBalance);

        double result = account.fullWithdraw();

        Assertions.assertEquals(expectedValue, account.getBalance());
        Assertions.assertEquals(initialBalance, result);
    }

    @Test
    public void withdrawShouldDecreaseBalanceWhenSufficientBalance() {
        Account account = AccountFactory.createAccount(800.0);

        account.withdraw(500.0);

        Assertions.assertEquals(300.0, account.getBalance());
    }

    @Test
    public void withdrawShouldThrowExceptionWhenInsufficientBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = AccountFactory.createAccount(800.0);
            account.withdraw(801.0);
        });
    }

}
