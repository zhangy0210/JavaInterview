package networkprogramming.testnio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class TimeServer {

    public void bind(int port) {
        try (
                //打开一个ServerSocketChannel
                ServerSocketChannel acceptChannel = ServerSocketChannel.open();
        ) {
            //设置成非阻塞，才能配合Selector使用
            acceptChannel.configureBlocking(false);
            //绑定指定端口
            acceptChannel.bind(new InetSocketAddress(port));
            //创建Selector多路复用器
            Selector selector = Selector.open();
            //注册accept事件到多路复用器上
            acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
            //轮询多路复用器，查看是否有准备就绪的I/O事件
            while (selector.select() > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    //取出key之后从Selector中移除掉
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        //如果是通知accept就绪
                        System.out.println("客户端连接成功");
                        ServerSocketChannel tempServerChannel = (ServerSocketChannel) key.channel();
                        //取出客户端连接的Channel
                        SocketChannel socketChannel = tempServerChannel.accept();
                        //设置成非阻塞
                        socketChannel.configureBlocking(false);
                        //注册read事件到多路复用器上
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        //如果是通知read就绪
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        //新建一个ByteBuffer用于读取
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
                        String str = new String(bts);
                        System.out.println("接收到客户端数据：" + str);
                        buffer.clear();
                        //判断客户端输入的字符串是否为Get Date
                        if (str.equalsIgnoreCase("Get Date")) {
                            //输出服务器时间字符串
                            buffer.put(new Date().toString().getBytes());
                        } else {
                            //提示客户端请求有误
                            buffer.put("Bad Request".getBytes());
                        }
                        buffer.flip();
                        socketChannel.write(buffer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TimeServer().bind(8989);
    }
}
