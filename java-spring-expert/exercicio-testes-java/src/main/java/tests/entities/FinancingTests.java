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

}
