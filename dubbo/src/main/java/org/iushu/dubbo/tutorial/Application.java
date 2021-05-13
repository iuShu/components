package org.iushu.dubbo.tutorial;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.URLBuilder;
import org.apache.dubbo.common.config.Environment;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.qos.protocol.QosProtocolWrapper;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.exchange.ExchangeHandler;
import org.apache.dubbo.remoting.exchange.ExchangeServer;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeHandler;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeServer;
import org.apache.dubbo.remoting.transport.netty4.NettyServer;
import org.apache.dubbo.remoting.transport.netty4.NettyServerHandler;
import org.apache.dubbo.remoting.transport.netty4.NettyTransporter;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.listener.ListenerInvokerWrapper;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ServiceRepository;
import org.apache.dubbo.rpc.protocol.AsyncToSyncInvoker;
import org.apache.dubbo.rpc.protocol.ProtocolFilterWrapper;
import org.apache.dubbo.rpc.protocol.ProtocolListenerWrapper;
import org.apache.dubbo.rpc.protocol.dubbo.DubboInvoker;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.apache.dubbo.rpc.proxy.AbstractProxyFactory;
import org.apache.dubbo.rpc.proxy.InvokerInvocationHandler;
import org.apache.dubbo.rpc.proxy.javassist.JavassistProxyFactory;
import org.apache.dubbo.rpc.proxy.wrapper.StubProxyFactoryWrapper;
import org.apache.dubbo.rpc.service.Destroyable;
import org.apache.dubbo.rpc.service.EchoService;
import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.provider.CenterItemWarehouse;
import org.iushu.dubbo.provider.ItemWarehouse;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
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
     * Provider
     * @see #gettingStartedProviderBootstrap() demonstrating case
     *
     * Initialize part
     * @see DubboBootstrap#DubboBootstrap()
     * @see ApplicationModel#getConfigManager()
     * @see ApplicationModel#getEnvironment()
     * @see ConfigManager see also /META-INF/dubbo.internal/org.apache.dubbo.common.context.FrameworkExt
     * @see Environment see also /META-INF/dubbo.internal/org.apache.dubbo.common.context.FrameworkExt
     * @see DubboBootstrap#initialize() loading configs
     * @see DubboBootstrap#exportServices()
     * @see ServiceConfig#export()
     * @see ServiceRepository see also /META-INF/dubbo.internal/org.apache.dubbo.common.context.FrameworkExt
     * @see ServiceConfig#doExportUrlsFor1Protocol(ProtocolConfig, List)
     * @see DubboProtocol#export(Invoker)
     * @see DubboProtocol#openServer(URL)
     * @see DubboProtocol#createServer(URL)
     *
     * TODO How Netty Server handle the client connection.
     * TODO Supplement interface for jobcnx-person
     * @see HeaderExchangeHandler
     */
    static void providerProcessing() {

    }

    /**
     * Consumer
     * @see #gettingStartedProviderBootstrap() demonstrating case
     *
     * Initialize protocol and proxy
     * @see ReferenceConfig#init()
     * @see Invoker invoker chain
     * @see ReferenceConfig#REF_PROTOCOL
     * @see Protocol#refer(Class, URL) protocol wrapper chain
     * @see QosProtocolWrapper#refer(Class, URL)
     * @see ProtocolFilterWrapper#refer(Class, URL) build invoker chain
     *  @see org.apache.dubbo.rpc.Filter ConsumerContextFilter FutureFilter MonitorFilter
     *  @see org.apache.dubbo.rpc.protocol.FilterNode filter chain
     * @see ProtocolListenerWrapper#refer(Class, URL)
     *  @see ListenerInvokerWrapper
     *  @see InvokerListener
     * @see DubboProtocol#refer(Class, URL)
     *  @see AsyncToSyncInvoker
     *  @see DubboInvoker connect to netty server of Provider
     * @see ReferenceConfig#PROXY_FACTORY
     * @see ProxyFactory#getProxy(Invoker, boolean)
     * @see StubProxyFactoryWrapper#getProxy(Invoker, boolean)
     * @see JavassistProxyFactory#getProxy(Invoker, boolean)
     *
     * Invocation processing
     * @see Invocation invoke action
     * @see Invoker invocation processing chain
     * @see InvokerInvocationHandler#invoke the proxy invocation
     * @see org.apache.dubbo.rpc.protocol.FilterNode invoker chain
     * @see org.apache.dubbo.rpc.Filter#invoke(Invoker, Invocation)
     * @see ListenerInvokerWrapper#invoke(Invocation)
     * @see AsyncToSyncInvoker#invoke(Invocation)
     * @see DubboInvoker#doInvoke(Invocation)
     * @see org.apache.dubbo.rpc.Filter.Listener#onResponse(Result, Invoker, Invocation)
     * @see org.apache.dubbo.rpc.Filter.Listener#onError(Throwable, Invoker, Invocation)
     * @see AsyncRpcResult#recreate() throw exception if any or return invoked result
     *
     * @see #filterNodeChain() more details
     */
    static void consumerProcessing() {

    }

    /**
     * A classic recursive invocation chain application case
     * @see ProtocolFilterWrapper build filter chain
     *
     * FilterNode.invoke(invocation)
     * Filter.invoke(next, invocation)
     * Filter.Listener#onResponse/onError
     * Filter.invoke(next, invocation)
     * Filter.Listener#onResponse/onError
     * ... ...
     * Filter.invoke(next, invocation)
     * Filter.Listener#onResponse/onError
     *
     * @see org.apache.dubbo.rpc.protocol.FilterNode also is an Invoker
     * @see org.apache.dubbo.rpc.protocol.FilterNode#invoker
     * @see org.apache.dubbo.rpc.protocol.FilterNode#filter
     * @see org.apache.dubbo.rpc.protocol.FilterNode#next
     * @see org.apache.dubbo.rpc.Filter
     * @see org.apache.dubbo.rpc.Filter.Listener some Filter also is a Listener
     */
    static void filterNodeChain() {

    }

    /**
     * Managing all the configs in dubbo
     * @see ConfigManager#write(Callable) add config with lock
     * @see ConfigManager#read(Callable) get config with lock
     */
    static void configManager() {

    }

    /**
     * The proxy object have implemented three interfaces
     * @see AbstractProxyFactory#getProxy(Invoker, boolean) add interfaces
     * @see EchoService for ping-pong test
     * @see Destroyable destroy the serive eventually
     * @see ItemWarehouse customized service
     */
    static void serviceProxy() {
        new Thread(Application::gettingStartedProviderBootstrap, "provider").start();

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
        for (Class<?> anInterface : itemWarehouse.getClass().getInterfaces())
            System.out.println(anInterface.getName());

        EchoService echoService = (EchoService) itemWarehouse;
        Object pong = echoService.$echo("PING");  // return PING
        System.out.println(pong);
    }

    /**
     * Wrapped component instance
     * @see ExtensionLoader#createExtension(String, boolean)
     * @see ExtensionLoader#cachedWrapperClasses
     * @see StubProxyFactoryWrapper wrapping ProxyFactory
     * @see ProtocolFilterWrapper wrapping Protocol
     * @see QosProtocolWrapper wrapping Protocol
     * @see ProtocolListenerWrapper wrapping Protocol
     *
     * Protocol after wrapped
     *    QosProtocolWrapper
     *     ProtocolFilterWrapper
     *      ProtocolListenerWrapped
     *       InjvmProtocol
     *
     * ProxyFactory after wrapped
     *    StubProxyFactoryWrapper
     *     JavassistProxyFactory
     *
     * Exporter after wrapped
     *    ListenerExporterWrapper
     *     InjvmExporter
     *
     * @see #adaptiveExtensionMechanism() more details
     */
    static void componentExtensionWrappers() {

    }

    /**
     * create the extension components' proxies
     * @see ExtensionLoader#createAdaptiveExtension()
     * @see ExtensionLoader#createAdaptiveExtensionClass()
     * @see org.apache.dubbo.common.compiler.Compiler#compile(String, ClassLoader)
     *
     * @see GeneratedAdaptiveProtocolClass actual Protocol in dubbo
     * @see GeneratedAdaptiveProtocolClass#export(Invoker) real entrance
     * @see GeneratedAdaptiveProtocolClass#refer(Class, URL) real entrance
     *
     * @see GeneratedAdaptiveProxyFactoryClass actual ProxyFactory in dubbo
     * @see GeneratedAdaptiveProxyFactoryClass#getInvoker(Object, Class, URL) real entrance
     * @see GeneratedAdaptiveProxyFactoryClass#getProxy(Invoker) real entrance
     * @see GeneratedAdaptiveProxyFactoryClass#getProxy(Invoker, boolean) real entrance
     */
    static void adaptiveExtensionMechanism() {

    }

    public static void main(String[] args) {
//        proxyObject();
        gettingStartedProviderBootstrap();
//        gettingStartedCase();
//        gettingStartedBootstrapCase();
//        proxyFactoryInvoker();

    }

}
