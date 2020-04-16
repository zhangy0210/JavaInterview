package networkprogramming.netty.calculate;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Scanner;

public class CalcClientHandler extends ChannelInboundHandlerAdapter {

    //连接服务器成功时的回调方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.print("请输入计算公式：");
        String body = new Scanner(System.in).next();
        //使用ByteBuf把消息写入到服务器
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(body.getBytes());
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //拿到服务器传来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        //根据读到的消息长度创建字节数组
        byte[] data = new byte[byteBuf.readableBytes()];
        //把消息读到字节数组中
        byteBuf.readBytes(data);
        //将读到的消息转成字符串
        String body = new String(data, "utf-8");
        System.out.println("接收到计算结果：" + body);
        System.out.print("是否需要继续计算(Y/N)：");
        String flag = new Scanner(System.in).next();
        //判断是否要继续计算
        if ("Y".equalsIgnoreCase(flag)) {
            channelActive(ctx);
        } else {
            System.out.println("客户端关闭中...");
            //关闭客户端
            ctx.close();
        }
    }
}