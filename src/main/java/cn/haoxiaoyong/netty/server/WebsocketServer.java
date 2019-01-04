package cn.haoxiaoyong.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by haoxy on 2019/1/4.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class WebsocketServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebsocketServer.class);

    public static void main(String[] args) {
        //初始化主线程(boss线程)
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        //初始化从线程池(work线程)
        NioEventLoopGroup  subGroup = new NioEventLoopGroup();
        try {
            //创建服务启动器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //指定使用主线程和从线程
            serverBootstrap.group(mainGroup, subGroup)
                    //指定使用NIO通道类型
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(12122))
                    //保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //指定通道初始化器用来加载当Channel收到事件消息后,如何进行业务处理
                    .childHandler(new WsServerInitializer());
            //绑定端口启动服务器,并等待服务启动
            ChannelFuture  future = serverBootstrap.bind().sync();
            if (future.isSuccess()) {
                LOGGER.info("启动 Netty 成功");
            }
            //等待服务器关闭
            //future.channel().closeFuture().sync(); 
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();

        }
    }
}
