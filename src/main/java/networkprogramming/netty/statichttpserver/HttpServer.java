package networkprogramming.netty.statichttpserver;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import networkprogramming.netty.httpserver.EchoServerHandler;

public class HttpServer {
    public void bind(int port) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {

                        protected void initChannel(Channel channel) {
                            //添加内置HTTP编解码器
                            channel.pipeline().addLast(new HttpServerCodec());
                            //将报文头和报文体合并成一个对象，并设置支持最大的报文体为 8MB
                            channel.pipeline().addLast(new HttpObjectAggregator(1024 * 1024 * 8));

                            //添加自定义HTTP服务处理器
                            channel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture f = bootstrap.bind(port).sync();
            System.out.println("服务器启动成功");
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        //实验楼Web服务功能映射端口为8080
        new HttpServer().bind(8080);
    }
}