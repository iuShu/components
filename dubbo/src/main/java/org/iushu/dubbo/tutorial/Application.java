package org.iushu.dubbo.tutorial;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeClient;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeHandler;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeServer;
import org.apache.dubbo.remoting.transport.netty4.NettyServer;
import org.apache.dubbo.remoting.transport.netty4.NettyServerHandler;
import org.apache.dubbo.remoting.transport.netty4.NettyTransporter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.provider.CenterItemWarehouse;
import org.iushu.dubbo.provider.ItemWarehouse;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * No registration center
 *
 * @author iuShu
 * @since 5/10/21
 */
public class Application {

    public static final String NO_REGISTRY_ADDRESS = "N/A";

    static void gettingStartedProvider() {
        ItemWarehouse itemWarehouse = new CenterItemWarehouse();

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("ItemWareHouseAccessService");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(NO_REGISTRY_ADDRESS);

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");    // see /META-INF/dubbo.internal/org.apache.dubbo.rpc.Protocol
        protocolConfig.setPort(20880);
        protocolConfig.setThreadpool("limited");    // see /META-INF/dubbo.internal/org.apache.dubbo.common.threadpool.ThreadPool
        protocolConfig.setThreadname("iushu-dubbo-thread");
        protocolConfig.setThreads(20);

        ServiceConfig<ItemWarehouse> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setGroup("ItemWareHouse");
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setInterface(ItemWarehouse.class);
        serviceConfig.setRef(itemWarehouse);
        serviceConfig.setVersion("1.0.0");

        serviceConfig.export();  // export provider

        // program end after exported
    }

    static void gettingStartedProviderBootstrap() {
        ServiceConfig<ItemWarehouse> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ItemWarehouse.class);
        serviceConfig.setRef(new CenterItemWarehouse());
        serviceConfig.setGroup("ItemWareHouse");
        serviceConfig.setVersion("1.0.0");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application("ItemWareHouseAccessService");
        bootstrap.registry(new RegistryConfig(NO_REGISTRY_ADDRESS));
        bootstrap.service(serviceConfig);
//        bootstrap.protocol(..);  // use 'dubbo' by default
        bootstrap.start();
        bootstrap.await();
    }

    static void gettingStartedConsumer() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("Distributor");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(NO_REGISTRY_ADDRESS);

        ReferenceConfig<ItemWarehouse> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setGroup("ItemWareHouse");
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setUrl("dubbo://192.168.61.137:20880/");
        referenceConfig.setInterface(ItemWarehouse.class);
        referenceConfig.setVersion("1.0.0");

        ItemWarehouse itemWarehouse = referenceConfig.get();
        Item item = itemWarehouse.getItem(new Random().nextInt(1000000));
        System.out.println(item);
    }

    static void gettingStartedCase() {
        new Thread(Application::gettingStartedProvider, "provider").start();
        new Thread(Application::gettingStartedConsumer, "consumer").start();
    }

    static void gettingStartedBootstrapCase() {
        new Thread(Application::gettingStartedProviderBootstrap, "provider").start();
        new Thread(Application::gettingStartedConsumer, "consumer").start();
    }

    /**
     * @see ConfigManager
     * @see ServiceConfig
     * @see RegistryConfig
     *
     * @see NettyTransporter#bind(URL, ChannelHandler)
     * @see NettyTransporter#connect(URL, ChannelHandler)
     * @see NettyServer#doOpen()
     * @see NettyServerHandler
     *
     * Provider
     * @see HeaderExchangeHandler#sent(Channel, Object)
     * @see HeaderExchangeHandler#received(Channel, Object)
     *
     * @see DubboProtocol#requestHandler
     * @see HeaderExchangeServer
     *
     * Consumer
     *
     * @see ReferenceConfig#init()
     * @see DubboProtocol#initClient(URL)
     *
     * ListenerInvokerWrapper -> Filter -> .. -> Filter -> ListenerInvokerWrapper
     * @see org.apache.dubbo.rpc.listener.ListenerInvokerWrapper
     * @see org.apache.dubbo.rpc.protocol.FilterNode#invoke(Invocation)
     * @see org.apache.dubbo.rpc.Filter#invoke(Invoker, Invocation)
     * @see org.apache.dubbo.rpc.protocol.AsyncToSyncInvoker#invoke(Invocation)
     * @see org.apache.dubbo.rpc.protocol.dubbo.DubboInvoker#doInvoke(Invocation)
     * @see org.apache.dubbo.rpc.AsyncRpcResult#get(long, TimeUnit)
     */
    static void components() {

    }

    public static void main(String[] args) {
//        gettingStartedCase();
        gettingStartedBootstrapCase();
    }

}
