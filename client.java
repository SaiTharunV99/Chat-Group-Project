import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter server IP address: ");
        String serverAddress = scanner.nextLine();

        Socket socket = new Socket(serverAddress, 1234);
        System.out.println("Connected to server!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(new ReceiveMessages(in)).start();

        while (true) {
            String message = scanner.nextLine();
            out.println(message);
        }
    }

    private static class ReceiveMessages implements Runnable {
        private BufferedReader in;

        public ReceiveMessages(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        }
    }
}
