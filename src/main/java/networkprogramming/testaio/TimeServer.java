package networkprogramming.testaio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class TimeServer {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void bind(int port) {
        try (
                //打开一个AsynchronousServerSocketChannel
                AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        ) {
            //绑定指定端口
            serverSocketChannel.bind(new InetSocketAddress(port));
            //监听accept事件
            doAccept(serverSocketChannel);
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

    private void doAccept(AsynchronousServerSocketChannel serverSocketChannel) {
        //调用accept方法，通过实现一个CompletionHandler接口以回调的方式来进行编码，该方法是非阻塞的
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
                //当accept完成时，aio框架会自动调用此方法，接着再调用一次doAccept方法，因为一次accept的调用只会触发一次
                doAccept(serverSocketChannel);
                //监听read事件
                doRead(socketChannel);
                System.out.println("客户端连接");
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
                countDownLatch.countDown();
            }
        });
    }

    private void doRead(AsynchronousSocketChannel socketChannel) {
        //新建一个ByteBuffer用于读取
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        //调用read方法，也是通过实现一个CompletionHandler接口以回调的方式来进行编码，该方法是非阻塞的
        socketChannel.read(buffer, socketChannel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
            @Override
            public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
                //当read完成时，aio框架会自动调用此方法，接着再调用一次doRead方法，原理同上
                doRead(socketChannel);
                try {
                    if (result == -1) {
                        //客户端关闭了连接
                        socketChannel.close();
                    } else {
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

    public static void main(String[] args) {
        new TimeServer().bind(8989);
    }
}