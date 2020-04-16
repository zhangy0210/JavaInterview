package networkprogramming.testaio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class TimeClient {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void connect(String ip, int port) {
        try (
                //打开一个AsynchronousSocketChannel
                AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        ) {
            //绑定指定端口
            socketChannel.connect(new InetSocketAddress(InetAddress.getByName(ip), port), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    //当connect完成
                    doRead(socketChannel);
                    //客户端输入处理
                    doWrite(socketChannel);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    try {
                        //连接失败
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                //因为上面的方法都是非阻塞的，这里需要阻塞住主线程不然程序就直接退出了
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doRead(AsynchronousSocketChannel socketChannel) {
        //新建一个ByteBuffer用于读取
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        //调用read方法，也是通过实现一个CompletionHandler接口以回调的方式来进行编码，该方法是非阻塞的
        socketChannel.read(buffer, socketChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
            @Override
            public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
                //当read就绪时，aio框架会自动调用此方法，接着再调用一次doRead方法，原理同上
                doRead(socketChannel);
                try {
                    if (result == -1) {
                        //服务器关闭了连接
                        socketChannel.close();
                    } else {
                        buffer.flip();
                        byte[] bts = new byte[buffer.remaining()];
                        buffer.get(bts);
                        System.out.println("接收到服务器的数据：" + new String(bts));
                        doWrite(socketChannel);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, AsynchronousSocketChannel socketChannel) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doWrite(AsynchronousSocketChannel socketChannel) {
        System.out.print("请输入要发送的数据：");
        String input = new Scanner(System.in).nextLine();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        buffer.put(input.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    public static void main(String[] args) {
        new TimeClient().connect("127.0.0.1", 8989);
    }
}