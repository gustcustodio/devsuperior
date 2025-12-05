package tests.entities;

import entities.Financing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FinancingTests {

    @Test
    public void shouldCreateObjectWhenDataIsValid() {
        Financing financing = new Financing(100000.0, 2000.0, 80);
        Assertions.assertNotNull(financing);
    }

    @Test
    public void shouldThrowExceptionWhenDataToCreateObjectIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Financing financing = new Financing(100000.0, 2000.0, 20);
        });
    }

    @Test
    public void shouldUpdateTotalAmountWhenDataIsValid() {
        Financing financing = new Financing(100000.0, 2000.0, 100);
        financing.setTotalAmount(120000.0);
        Assertions.assertEquals(120000.0, financing.getTotalAmount());
    }

    @Test
    public void shouldThrowExceptionWhenDataToSetTotalAmountIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Financing financing = new Financing(100000.0, 2000.0, 80);
            financing.setTotalAmount(120000.0);
        });
    }

    @Test
    public void shouldUpdateIncomeWhenDataIsValid() {
        Financing financing = new Financing(100000.0, 2000.0, 80);
        financing.setIncome(3000.0);
        Assertions.assertEquals(3000.0, financing.getIncome());
    }

    @Test
    public void shouldThrowExceptionWhenDataToSetIncomeIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Financing financing = new Financing(100000.0, 2000.0, 80);
            financing.setIncome(1500.0);
        });
    }

    @Test
    public void shouldUpdateMonthsWhenDataIsValid() {
        Financing financing = new Financing(100000.0, 2000.0, 80);
        financing.setMonths(100);
        Assertions.assertEquals(100, financing.getMonths());
    }

    @Test
    public void shouldThrowExceptionWhenDataToSetMonthsIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Financing financing = new Financing(100000.0, 2000.0, 80);
            financing.setMonths(50);
        });
    }

    @Test
    public void shouldCalculateCorrectlyTheValueOfEntry() {
        Financing financing = new Financing(100000.0, 2000.0, 80);
        Assertions.assertEquals(20000.0, financing.entry());
    }

    @Test
    public void shouldCalculateCorrectlyTheValueOfQuota() {
        Financing financing = new Financing(100000.0, 2000.0, 80);
        Assertions.assertEquals(1000.0, financing.quota());
    }

}
