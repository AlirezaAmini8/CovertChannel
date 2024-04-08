import java.io.*;
import java.net.*;

public class DelayCovertServer {
    private static final int PORT = 65432;
    private static final long SHORT_DELAY = 100; // Milliseconds for 0
    private static final long LONG_DELAY = 500;  // Milliseconds for 1
    private static final String MESSAGE = "Hello from secret channel";

    private static void sendBit(OutputStream out, char bit) throws IOException, InterruptedException {
        if (bit == '0') {
            Thread.sleep(SHORT_DELAY);
        } else {
            Thread.sleep(LONG_DELAY);
        }
        out.write("Ping\n".getBytes());
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                OutputStream out = clientSocket.getOutputStream();

                for (char character : MESSAGE.toCharArray()) {
                    String binaryString = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0');
                    for (char bit : binaryString.toCharArray()) {
                        sendBit(out, bit);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}
