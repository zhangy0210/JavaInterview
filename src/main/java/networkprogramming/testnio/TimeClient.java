package networkprogramming.testnio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class TimeClient {

    public void bind(String ip, int port) {
        try (
                //打开一个SocketChannel
                SocketChannel connectChannel = SocketChannel.open();
        ) {
            //设置成非阻塞
            connectChannel.configureBlocking(false);
            //连接指定服务器
            connectChannel.connect(new InetSocketAddress(InetAddress.getByName(ip), port));
            //创建Selector多路复用器
            Selector selector = Selector.open();
            //注册connect事件到多路复用器上
            connectChannel.register(selector, SelectionKey.OP_CONNECT);
            //轮询多路复用器，查看是否有准备就绪的I/O事件
            while (selector.select() > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    //取出key之后从Selector中移除掉
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        //如果是通知connect就绪
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.configureBlocking(false);
                        //判断connect是否完成
                        if (socketChannel.finishConnect()) {
                            commonWrite(socketChannel);
                            //注册read事件到多路复用器上
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                    } else if (key.isReadable()) {
                        //新建一个ByteBuffer用于读取
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(8192);
                        int size = socketChannel.read(buffer);
                        if (size == -1) {
                            //客户端关闭了连接
                            socketChannel.close();
                            return;
                        }
                        buffer.flip();
                        byte[] bts = new byte[buffer.remaining()];
                        buffer.get(bts);
                        System.out.println(new String(bts));
                        commonWrite(socketChannel);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void commonWrite(SocketChannel socketChannel) throws IOException {
        System.out.print("请输入要发送的数据：");
        String input = new Scanner(System.in).nextLine();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        buffer.put(input.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    public static void main(String[] args) {
        new TimeClient().bind("127.0.0.1", 8989);
    }
}
