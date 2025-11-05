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
    public void shouldThrowExceptionWhenDataIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Financing financing = new Financing(100000.0, 2000.0, 20);
        });
    }

}
