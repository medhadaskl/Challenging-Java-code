package org.example;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// test case for File Reader With Exception Handling
class FileReaderWithExceptionHandlingTest {

    @Test
    void testReadValidFile() throws Exception {
        // Arrange
        FileReaderWithExceptionHandling reader = new FileReaderWithExceptionHandling();
        String fileName = "test_valid.txt";

        // Create a small sample file
        Files.write(Paths.get(fileName), List.of("Reading", "file"));

        // Act
        List<String> lines = reader.readFile(fileName);

        // Assert
        assertEquals(2, lines.size());
        assertEquals("Reading", lines.get(0));
        assertEquals("file", lines.get(1));

        // Clean
        Files.deleteIfExists(Paths.get(fileName));
    }

    @Test
    void testFileNotFound() {
        // Arrange
        FileReaderWithExceptionHandling reader = new FileReaderWithExceptionHandling();
        String nonExistentFile = "file_does_not_exist.txt";

        // Assert
        assertThrows(FileNotFoundException.class, () -> {
            reader.readFile(nonExistentFile);
        });
    }

    @Test
    void testCorruptedFile() throws Exception {
        // Arrange
        FileReaderWithExceptionHandling reader = new FileReaderWithExceptionHandling();
        String corruptFileName = "corrupt_test.txt";

        // Create a fake corrupt file
        Files.write(Paths.get(corruptFileName), List.of("Some data"));

        // Assert
        assertThrows(CorruptedFileException.class, () -> {
            reader.readFile(corruptFileName);
        });

        // Clean
        Files.deleteIfExists(Paths.get(corruptFileName));
    }
}
