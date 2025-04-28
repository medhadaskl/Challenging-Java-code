package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrentAccountTest {
    private CurrentAccount account;

    @BeforeEach
    void setUp() {
        account = new CurrentAccount("CA123",1000.0, 500.0, 1.5);
    }

    @Test
    void testDeposit() {
        account.deposit(200.0);
        assertEquals(1200.0, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawWithinBalance() {
        account.withdraw(300.0);
        assertEquals(700.0, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawUsingOverdraft() {
        account.withdraw(1200.0);
        assertEquals(-200.0, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawExceedingOverdraftLimit() {
        account.withdraw(1600.0);
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    void testApplyInterest() {
        account.applyInterest();
        assertEquals(1015.0, account.getBalance(), 0.001);
    }
}
