package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//test case to check ATM APP for sucessfull Withdrawal and Insufficient balance.
public class ATMAppTest {
    @Test
    void testSuccessfulWithdrawal() throws InsufficientFundsException {
        ATM atm = new ATM(1000);
        atm.withdraw(500);
        assertEquals(500, atm.getBalance());
    }

    @Test
    void testInsufficientFunds() {
        ATM atm = new ATM(1000);
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            atm.withdraw(1500);
        });
        String expectedMessage = "Withdrawal failed: Insufficient funds";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
