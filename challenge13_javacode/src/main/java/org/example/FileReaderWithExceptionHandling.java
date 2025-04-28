package org.example;
import java.io.*;
import java.nio.file.*;
import java.util.*;

// Custom Exception
//Custom exception to indicate that a file is corrupted. //

class CorruptedFileException extends Exception {
    public CorruptedFileException(String message) {
        super(message);
    }
}
/*This class provides functionality to read a file and handle exceptions such as file not found and corrupted file.
 */
public class FileReaderWithExceptionHandling {

    public List<String> readFile(String filePath) throws FileNotFoundException, CorruptedFileException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found");
        }

        // if the file is "corrupted" based on file name
        if (filePath.contains("corrupt")) {
            throw new CorruptedFileException("File is corrupted");
        }

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            // You can rethrow as unchecked exception or handle it here
            throw new RuntimeException("An error occurred while reading the file: " + e.getMessage());
        }
        return lines;
    }

    // Main method for manual testing
    public static void main(String[] args) {
        FileReaderWithExceptionHandling fileReader = new FileReaderWithExceptionHandling();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path to read: ");
        String filePath = scanner.nextLine();

        try {
            List<String> content = fileReader.readFile(filePath);
            System.out.println("File Content:");
            content.forEach(System.out::println);
        } catch (FileNotFoundException | CorruptedFileException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
