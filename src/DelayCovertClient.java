import java.io.*;
import java.net.*;

public class DelayCovertClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 65432;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Connected to server: " + HOST + ":" + PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            long previousTime = System.currentTimeMillis();
            StringBuilder binaryString = new StringBuilder();
            StringBuilder decodedMessage = new StringBuilder();

            while (reader.readLine() != null) {
                long currentTime = System.currentTimeMillis();
                long delay = currentTime - previousTime;
                previousTime = currentTime;

                binaryString.append(delay >= 300 ? '1' : '0');

                if (binaryString.length() == 8) {
                    decodedMessage.append((char) Integer.parseInt(binaryString.toString(), 2));
                    binaryString.setLength(0);
                }
            }

            System.out.println("Decoded message: " + decodedMessage);
        }
    }
}
