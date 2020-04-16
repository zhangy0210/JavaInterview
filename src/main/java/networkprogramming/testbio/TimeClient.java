package networkprogramming.testbio;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TimeClient {

    public void connect(String ip, int port) {
        try (
                Socket socket = new Socket(ip, port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            while (true) {
                System.out.print("请输入要发送的数据：");
                String input = new Scanner(System.in).nextLine();
                writer.write(input);
                writer.newLine();
                writer.flush();
                String response = reader.readLine();
                System.out.println("接收到服务器的数据：" + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TimeClient().connect("127.0.0.1", 8989);
    }
}