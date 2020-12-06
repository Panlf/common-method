package com.plf.netty.rpc.provider;

import com.plf.netty.rpc.provider.server.NettyServer;

public class ServerBootstrap {
	public static void main(String[] args) {
		NettyServer.startServer("127.0.0.1", 8080);
	}
}
