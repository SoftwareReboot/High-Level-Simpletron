import java.io.*;
import java.util.*;

public class Compiler {

    private static final Map<String, Integer> OPCODES = new HashMap<>();
    static {
        OPCODES.put("READ", 10);
        OPCODES.put("WRITE", 11);
        OPCODES.put("LOADM", 20);
        OPCODES.put("STORE", 21);
        OPCODES.put("ADDM", 30);
        OPCODES.put("SUBM", 31);
        OPCODES.put("DIVM", 32);
        OPCODES.put("MULM", 33);
        OPCODES.put("JMP", 40);
        OPCODES.put("JMPZ", 41);
        OPCODES.put("JMPN", 42);
        OPCODES.put("HALT", 43);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter high-level source file: ");
        String inputFile = sc.nextLine();

        Memory memory = new Memory();
        Map<String, Integer> variables = new LinkedHashMap<>();

        int instructionAddress = 0;
        int variableStart = 90; // Reserve top memory for variables

        try {
            List<String> lines = readFile(inputFile);

            for (String line : lines) {
                line = line.split(";")[0].trim(); // remove comments
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String instr = parts[0].toUpperCase();
                String operand = (parts.length > 1) ? parts[1].toLowerCase() : null;

                if (!OPCODES.containsKey(instr)) {
                    System.out.println("Unknown instruction: " + instr);
                    continue;
                }

                int opcode = OPCODES.get(instr);
                int address = 0;

                if (operand != null && operand.matches("[a-zA-Z]")) {
                    if (!variables.containsKey(operand)) {
                        variables.put(operand, variableStart++);
                    }
                    address = variables.get(operand);
                }

                int machineCode = opcode * 100 + address;
                memory.store(instructionAddress++, machineCode);
            }

            // Write compiled program to file
            //writeMemoryToFile(memory, "compiled.txt");
            //System.out.println("\nCompilation successful! Output: compiled.txt");
            //memory.dump();
            System.out.flush(); 

            System.out.print("\nRun the program now? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                //Loader.loadProgram("compiled.txt", memory);
                Processor processor = new Processor(memory);
                processor.run();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
        }

        sc.close();
    }

    private static List<String> readFile(String filename) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File(filename));
        
        while (fileScanner.hasNextLine()) {
            lines.add(fileScanner.nextLine().trim());
        }
        
        fileScanner.close();
        return lines;
    }

    private static void writeMemoryToFile(Memory memory, String outputFile) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
            for (int i = 0; i < 100; i++) {
                int value = memory.load(i);
                if (value != 0) {
                    pw.printf("%02d %04d%n", i, value);
                }
            }
        }
    }
}
