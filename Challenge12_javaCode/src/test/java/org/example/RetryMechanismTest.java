package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

 // Unit tests for RetryMechanismApp
class RetryMechanismAppTest {
    // Test case where API call succeeds on the first attempt.
    @Test
    void testApiCallSucceedsFirstAttempt() {
        RetryMechanismApp.ApiService apiService = new RetryMechanismApp.ApiService() {
            @Override
            public boolean apiCall() {
                return true;
            }
        };
        RetryMechanismApp.RetryService retryService = new RetryMechanismApp.RetryService(apiService);

        assertDoesNotThrow(() -> retryService.performApiCallWithRetry());
    }

    //Test case where API call fails first then succeeds.
    @Test
    void testApiCallFailsThenSucceeds() {
        RetryMechanismApp.ApiService apiService = new RetryMechanismApp.ApiService() {
            private int attempt = 0;

            @Override
            public boolean apiCall() {
                attempt++;
                return attempt >= 2;
                // Fail first attempt, succeed second
            }
        };
        RetryMechanismApp.RetryService retryService = new RetryMechanismApp.RetryService(apiService);

        assertDoesNotThrow(() -> retryService.performApiCallWithRetry());
    }


     //Test case where API call fails:all attempts.
    @Test
    void testApiCallFailsAllAttempts() {
        RetryMechanismApp.ApiService apiService = new RetryMechanismApp.ApiService() {
            @Override
            public boolean apiCall() {
                return false;
            }
        };
        RetryMechanismApp.RetryService retryService = new RetryMechanismApp.RetryService(apiService);

        Exception exception = assertThrows(RetryMechanismApp.ServiceUnavailableException.class, () -> {
            retryService.performApiCallWithRetry();
        });

        assertEquals("All retry attempts failed.", exception.getMessage());
    }
}
