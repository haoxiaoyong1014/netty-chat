package cn.haoxiaoyong.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by haoxy on 2019/1/4.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * 通道初始化器
 * 用来加载通道处理器
 */
public class WsServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //用于支持 http协议
        //websocket 基于http协议,需要 http的编码器
        pipeline.addLast(new HttpServerCodec())
                //对大数据流的支持
                .addLast(new ChunkedWriteHandler())
                //添加对HTTP请求和响应的聚合器: 只要Netty进行编码都需要使用
                //对HttpMessage进行聚合,聚合成FullHttpRequest获取FullHttpResponse
                .addLast(new HttpObjectAggregator(1024 * 64))

                //-----------支持 webSocket----------
                //需要指定接收请求的路由,处理握手动作(close,ping pong),ping+pong=心跳
                //必须使用 ws 后缀结尾的 url 才能访问
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                //添加自定义的 handler
                .addLast(new ChatHandler());
    }
}
