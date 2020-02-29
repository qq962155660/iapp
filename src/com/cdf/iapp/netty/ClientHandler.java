package com.cdf.iapp.netty;
import com.cdf.iapp.MainActivity;

import android.os.Message;
import android.util.Log;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;


public class ClientHandler extends CustomHeartbeatHandler {
    private Client client;
    public ClientHandler(Client client) {
        super("client");
        this.client = client;
    }
 
    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        byte[] data = new byte[byteBuf.readableBytes() - 5];
        byteBuf.skipBytes(5);
        byteBuf.readBytes(data);
        String content = new String(data);
        Log.i("tag收到数据",name + " get content: " + content);
        Message msg = new Message();
        msg.what = 0;
        msg.obj = content;
		MainActivity.mHandler.sendMessage(msg );
    }
 
    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx) {
        super.handleAllIdle(ctx);
        sendPingMsg(ctx);
    }
 
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.doConnect();
    }
}