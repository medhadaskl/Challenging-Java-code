package org.example;
import java.util.Random;
//application to demonstrate Retry Mechanism with exponential backoff
public class RetryMechanismApp {
    // Custom Exception
    static class ServiceUnavailableException extends Exception {
        public ServiceUnavailableException(String message) {
            super(message);
        }
    }

    // API Service that can randomly succeed or fail.
    static class ApiService {
        private static final Random random = new Random();

        //API call that randomly fails
        public boolean apiCall() {
            return random.nextInt(3) != 0;  //failure 1 in 3 times
        }
    }

    // Retry Service that handles retry logic with backoff
    static class RetryService {
        private ApiService apiService;

        public RetryService(ApiService apiService) {
            this.apiService = apiService;
        }

        public void performApiCallWithRetry() throws ServiceUnavailableException {
            int retryCount = 0;
            int[] backoffTimes = {1, 2, 4};

            while (retryCount < backoffTimes.length) {
                try {
                    //call the API
                    System.out.println("Attempt " + (retryCount + 1) + "...");
                    if (apiService.apiCall()) {
                        System.out.println("API call successful!");
                        return;
                    } else {
                        throw new Exception("API call failed.");
                    }
                } catch (Exception e) {
                    retryCount++;
                    if (retryCount >= backoffTimes.length) {
                        throw new ServiceUnavailableException("All retry attempts failed.");
                    }
                    try {
                        // Wait before retrying
                        System.out.println("Retrying in " + backoffTimes[retryCount - 1] + " seconds...");
                        Thread.sleep(backoffTimes[retryCount - 1] * 1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new ServiceUnavailableException("Service interrupted during backoff.");
                    }
                }
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        ApiService apiService = new ApiService();
        RetryService retryService = new RetryService(apiService);

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Press Enter to start API call with retry mechanism ");

        scanner.nextLine();
        // Wait for user to press Enter

        try {
            retryService.performApiCallWithRetry();
        } catch (ServiceUnavailableException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        scanner.close();
    }

}
