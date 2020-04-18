package networkprogramming.netty.httpserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;
import java.util.Map;

public class EchoServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //请求方法
        String method = msg.method().name();
        //请求uri
        String uri = msg.uri();
        //请求http版本
        String version = msg.protocolVersion().text();
        //请求头
        HttpHeaders headers = msg.headers();
        //请求体
        String content = msg.content().toString(Charset.defaultCharset());


        //响应体显示接收到的请求信息
        StringBuilder body = new StringBuilder();
        body.append("<html>");
        body.append("<body>");
        body.append(method + " " + uri + " " + version + "</br>");
        //遍历请求头
        for (Map.Entry<String, String> entry : headers.entries()) {
            body.append(entry.getKey() + ": " + entry.getValue() + "</br>");
        }
        //分割请求头和请求体
        body.append("</br>");
        body.append(content);
        body.append("</body>");
        body.append("</html>");

        //构造一个 200 响应
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        //设置响应头Content-Length
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, body.toString().getBytes().length);
        //设置响应体
        response.content().writeBytes(body.toString().getBytes());
        //写出到客户端
        ctx.writeAndFlush(response);
    }

}