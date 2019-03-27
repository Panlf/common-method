package com.plf.netty.serializable;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class SerializableFactoryMarshalling {

	/**
	 * 创建JBoss  Marshalling解码器MarshallingDecoder
	 * @return
	 */
	public static MarshallingDecoder buildMarshallingDecoder(){
		//首先通过Marshalling工具类的工厂方法获取Marshalling实例对象 参数serial标识创建的是Java序列化工厂对象
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		//创建了MarshallingConfiguration对象，配置版本号为5
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		//序列化版本。只要使用JDK5以上版本，version只能定义5
		configuration.setVersion(5);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		//构建Netty的MarshallingDecoder对象，两个参数分别是provider和单个消息序列化后的最大长度
		MarshallingDecoder decoder = new MarshallingDecoder(provider,1024*1024*1);
		return decoder;
	}
	
	public static MarshallingEncoder buildMarshallingEncoder(){
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		//创建了MarshallingConfiguration对象，配置版本号为5
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		//构建Netty的MarshallingEncoder对象，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		
		return encoder;
	}

}
