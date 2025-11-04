package tests.entities;

import entities.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTests {

    @Test
    public void depositShouldIncreaseBalanceAndDiscountFeeWhenPositiveAmount() {
        double amount = 200.0;
        double expectedValue = 196.0;
        Account account = new Account(1L, 0.0);

        account.deposit(amount);

        Assertions.assertEquals(expectedValue, account.getBalance());
    }

    @Test
    public void depositShouldDoNothingWhenNegativeAmount() {
        double amount = -200.0;
        double expectedValue = 100.0;
        Account account = new Account(1L, expectedValue);

        account.deposit(amount);

        Assertions.assertEquals(expectedValue, account.getBalance());
    }

}
