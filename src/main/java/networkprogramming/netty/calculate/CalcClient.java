package networkprogramming.netty.calculate;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 发送计算公式的客户端
 */
public class CalcClient {

    public void connect(String host, int port) {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap().group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new CalcClientHandler());
                        }
                    });

            //指定服务器IP和端口发起连接
            ChannelFuture f = bootstrap.connect(host, port).sync();
            //阻塞当前线程，直到客户端关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //客户端优雅关闭
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new CalcClient().connect("127.0.0.1", 9999);
    }
}