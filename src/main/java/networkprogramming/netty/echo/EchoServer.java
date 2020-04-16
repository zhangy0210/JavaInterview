package networkprogramming.netty.echo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * 返回输入的服务器
 */
public class EchoServer {

    public static void main(String[] args) {
        //处理客户端连接(accept)的线程池
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        //处理IO事件(read、write)的线程池
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //netty服务器启动辅助类
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boosGroup, workerGroup)  //传入之前的两个NioEventLoopGroup实例
                    .channel(NioServerSocketChannel.class)  //使用NioServerSocketChannel作为底层实现
                    .childHandler(new ChannelInitializer() {

                        protected void initChannel(Channel channel) throws Exception {

                            //自定义handle处理数据
                            channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    //当读取到客户端数据时，直接原样写入到客户端
                                    ctx.write(msg);
                                    //刷新缓冲区，将数据立即发送给客户端
                                    ctx.flush();
                                    //或者直接使用 ctx.writeAndFlush(msg);
                                }
                            });
                        }
                    });
            //绑定9999端口并开始接收客户端连接
            ChannelFuture f = bootstrap.bind(9999).sync();
            System.out.println("服务器启动成功");
            //阻塞当前线程，直到服务器关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅关闭服务器
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}