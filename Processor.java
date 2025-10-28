import java.util.Scanner;

public class Processor {
    private Memory memory;
    private int accumulator;
    private int instructionCounter;
    private boolean running;

    public Processor(Memory memory) {
        this.memory = memory;
        this.accumulator = 0;
        this.instructionCounter = 0;
        this.running = true;
    }

    public void run() {
        Scanner in = new Scanner(System.in);

        System.out.println("\n--- Executing Simpletron Program ---\n");

        while (running) {
            int instruction = memory.load(instructionCounter);
            int opcode = instruction / 100;
            int operand = instruction % 100;

            switch (opcode) {
                case 10: // READ
                    System.out.print("Enter value for address " + operand + ": ");
                    memory.store(operand, in.nextInt());
                    break;

                case 11: // WRITE
                    System.out.println("Output: " + memory.load(operand));
                    break;

                case 20: // LOAD
                    accumulator = memory.load(operand);
                    break;

                case 21: // STORE
                    memory.store(operand, accumulator);
                    break;

                case 30: // ADD
                    accumulator += memory.load(operand);
                    break;

                case 31: // SUB
                    accumulator -= memory.load(operand);
                    break;

                case 32: // DIV
                    int divisor = memory.load(operand);
                    if (divisor != 0) accumulator /= divisor;
                    else System.out.println("Error: Division by zero");
                    break;

                case 33: // MUL
                    accumulator *= memory.load(operand);
                    break;

                case 40: // JMP
                    instructionCounter = operand - 1;
                    break;

                case 41: // JMPZ
                    if (accumulator == 0) instructionCounter = operand - 1;
                    break;

                case 42: // JMPN
                    if (accumulator < 0) instructionCounter = operand - 1;
                    break;

                case 43: // HALT
                    running = false;
                    System.out.println("\nProgram halted.");
                    break;

                default:
                    System.out.println("Unknown opcode: " + opcode);
                    running = false;
                    break;
            }

            instructionCounter++;
        }

        System.out.println("\nFinal accumulator value: " + accumulator);
        System.out.println();
        in.close();
    }
}
