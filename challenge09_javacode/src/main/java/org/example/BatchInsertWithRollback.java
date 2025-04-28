package org.example;
import java.io.*;
import java.sql.*;

/*
This class demonstrates batch insertion of student records from a CSV file into a PostgreSQL database.
 It includes transaction management with rollback in case of errors.
 */

public class BatchInsertWithRollback {

    // Database connection parameters
    // URL for connecting to the PostgreSQL database.

    static final String JDBC_URL = "jdbc:postgresql://localhost:5432/studentsdb";
    static final String JDBC_USERNAME = "postgres";
    static final String JDBC_PASSWORD = "1234";

    // Main method
    public static void main(String[] args) {
        String csvFilePath = "C:\\Users\\KL000049\\Desktop\\students.csv";
        //connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            connection.setAutoCommit(false);

            // Call the batch insert method
            BatchInsertWithRollback batchInsert = new BatchInsertWithRollback();
            batchInsert.processBatchInsert(connection, csvFilePath);

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to perform batch insert
    public void processBatchInsert(Connection connection, String csvFilePath) throws SQLException {
        String sql = "INSERT INTO students (id, name, age, grade, subject) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line = br.readLine();
            int count = 0;

            // Reading each line from the CSV file and adding to batch
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                    String grade = data[3];
                    String subject = data[4];
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, name);
                    preparedStatement.setInt(3, age);
                    preparedStatement.setString(4, grade);
                    preparedStatement.setString(5, subject);

                    // Add to batch
                    preparedStatement.addBatch();

                    // Execute batch every 100 records
                    if (++count % 100 == 0) {
                        preparedStatement.executeBatch();
                    }
                }
            }
            // Execute any remaining records
            preparedStatement.executeBatch();
            // if no errors found Commit the transaction
            connection.commit();
            System.out.println("Batch insert completed successfully.");

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            //SQL exceptions and perform rollback
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.out.println("Error during rollback: " + rollbackException.getMessage());
                rollbackException.printStackTrace();
            }
            System.out.println("Error with database operation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


