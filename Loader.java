import java.io.*;
import java.util.*;

public class Loader {
    public static void loadProgram(String filename, Memory memory) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNext()) {
                int address = fileScanner.nextInt();
                int value = fileScanner.nextInt();
                memory.store(address, value);
            }
            System.out.println("Program successfully loaded into memory.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Program file not found (" + filename + ")");
        }
    }
}
