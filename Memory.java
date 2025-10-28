public class Memory {
    private static final int SIZE = 100;
    private int[] memory;

    public Memory() {
        memory = new int[SIZE];
    }

    public void store(int address, int value) {
        if (address >= 0 && address < SIZE) {
            memory[address] = value;
        } else {
            System.out.println("Memory store error: invalid address " + address);
        }
    }

    public int load(int address) {
        if (address >= 0 && address < SIZE) {
            return memory[address];
        } else {
            System.out.println("Memory load error: invalid address " + address);
            return 0;
        }
    }

    public void dump() {
        System.out.println("\nMEMORY DUMP:");
        for (int i = 0; i < SIZE; i++) {
            if (memory[i] != 0) {
                System.out.printf("%02d : %04d%n", i, memory[i]);
            }
        }
    }
}
