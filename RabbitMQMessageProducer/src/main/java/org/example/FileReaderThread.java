package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderThread extends Thread {
    private final String filePath;

    public FileReaderThread(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try {
            Path path = Paths.get(filePath);
            while (true) {
                // Read all lines from the file
                Files.lines(path)
                        .map(Double::parseDouble) // convert each line to a double
                        .forEach(number -> {
                            System.out.println(Thread.currentThread().getId() + " Value " + number);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}