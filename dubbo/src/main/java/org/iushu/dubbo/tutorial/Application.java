package org.iushu.dubbo.tutorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.URLBuilder;
import org.apache.dubbo.common.config.Environment;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.common.serialize.fst.FstSerialization;
import org.apache.dubbo.common.serialize.gson.GsonSerialization;
import org.apache.dubbo.common.serialize.hessian2.Hessian2Serialization;
import org.apache.dubbo.common.serialize.java.JavaSerialization;
import org.apache.dubbo.common.serialize.kryo.KryoSerialization;
import org.apache.dubbo.common.serialize.kryo.optimized.KryoSerialization2;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.qos.protocol.QosProtocolWrapper;
import org.apache.dubbo.qos.server.Server;
import org.apache.dubbo.qos.server.handler.QosProcessHandler;
import org.apache.dubbo.registry.ListenerRegistryWrapper;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.client.migration.MigrationInvoker;
import org.apache.dubbo.registry.client.migration.MigrationRuleHandler;
import org.apache.dubbo.registry.client.migration.MigrationRuleListener;
import org.apache.dubbo.registry.integration.DynamicDirectory;
import org.apache.dubbo.registry.integration.InterfaceCompatibleRegistryProtocol;
import org.apache.dubbo.registry.integration.RegistryDirectory;
import org.apache.dubbo.registry.integration.RegistryProtocol;
import org.apache.dubbo.registry.support.FailbackRegistry;
import org.apache.dubbo.registry.zookeeper.ZookeeperRegistry;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Codec2;
import org.apache.dubbo.remoting.buffer.ChannelBuffer;
import org.apache.dubbo.remoting.exchange.ExchangeChannel;
import org.apache.dubbo.remoting.exchange.ExchangeHandler;
import org.apache.dubbo.remoting.exchange.ExchangeServer;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeHandler;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeServer;
import org.apache.dubbo.remoting.exchange.support.header.HeartbeatHandler;
import org.apache.dubbo.remoting.transport.CodecSupport;
import org.apache.dubbo.remoting.transport.DecodeHandler;
import org.apache.dubbo.remoting.transport.MultiMessageHandler;
import org.apache.dubbo.remoting.transport.dispatcher.ChannelHandlers;
import org.apache.dubbo.remoting.transport.dispatcher.all.AllChannelHandler;
import org.apache.dubbo.remoting.transport.netty4.NettyCodecAdapter;
import org.apache.dubbo.remoting.transport.netty4.NettyServer;
import org.apache.dubbo.remoting.transport.netty4.NettyServerHandler;
import org.apache.dubbo.remoting.transport.netty4.NettyTransporter;
import org.apache.dubbo.remoting.zookeeper.ChildListener;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.ClusterInvoker;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.AvailableClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.BroadcastClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.FailbackClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.FailfastClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.FailoverCluster;
import org.apache.dubbo.rpc.cluster.support.FailoverClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.FailsafeClusterInvoker;
import org.apache.dubbo.rpc.cluster.support.wrapper.AbstractCluster;
import org.apache.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker;
import org.apache.dubbo.rpc.listener.ListenerInvokerWrapper;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ServiceRepository;
import org.apache.dubbo.rpc.protocol.AbstractProtocol;
import org.apache.dubbo.rpc.protocol.AsyncToSyncInvoker;
import org.apache.dubbo.rpc.protocol.ProtocolFilterWrapper;
import org.apache.dubbo.rpc.protocol.ProtocolListenerWrapper;
import org.apache.dubbo.rpc.protocol.dubbo.DecodeableRpcInvocation;
import org.apache.dubbo.rpc.protocol.dubbo.DubboCodec;
import org.apache.dubbo.rpc.protocol.dubbo.DubboInvoker;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.apache.dubbo.rpc.proxy.AbstractProxyFactory;
import org.apache.dubbo.rpc.proxy.InvokerInvocationHandler;
import org.apache.dubbo.rpc.proxy.javassist.JavassistProxyFactory;
import org.apache.dubbo.rpc.proxy.wrapper.StubProxyFactoryWrapper;
import org.apache.dubbo.rpc.service.Destroyable;
import org.apache.dubbo.rpc.service.EchoService;
import org.iushu.dubbo.Utils;
import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.provider.CenterItemWarehouse;
import org.iushu.dubbo.provider.ItemWarehouse;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.iushu.dubbo.Utils.sleep;

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

    static void registryProviderCase() {
        ApplicationConfig applicationConfig = new ApplicationConfig("ItemWareHouseAccessService");
        applicationConfig.setQosEnable(false);

        ServiceConfig<ItemWarehouse> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ItemWarehouse.class);
        serviceConfig.setRef(new CenterItemWarehouse());
        serviceConfig.setGroup("ItemWareHouse");
        serviceConfig.setVersion("1.0.0");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig);
        bootstrap.registry(new RegistryConfig("zookeeper://localhost:2181"));
        bootstrap.service(serviceConfig);
        bootstrap.protocol(new ProtocolConfig("dubbo", 20881));
        bootstrap.start();
        bootstrap.await();
    }

    static void registryConsumerCase() {
        ReferenceConfig<ItemWarehouse> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("Distributor"));
        referenceConfig.setGroup("ItemWareHouse");
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://localhost:2181"));
        referenceConfig.setInterface(ItemWarehouse.class);
        referenceConfig.setVersion("1.0.0");
        ItemWarehouse itemWarehouse = referenceConfig.get();
        Item item = itemWarehouse.getItem(new Random().nextInt(999999));
        System.out.println(item);
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
     * @see ChannelHandlers#wrapInternal(ChannelHandler, URL) wrap ChannelHandler for send/receive message
     * @see NettyServerHandler core ChannelHandler(Netty)
     * @see NettyServer#doOpen()
     * @see #nettyInDubbo()
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
     * @see Protocol#export(Invoker)
     * @see Protocol#refer(Class, URL)
     * @see Exporter
     * @see Invoker
     *
     * Providing (Provider side)
     * @see Protocol#export(Invoker)
     * @see AbstractProtocol#serviceKey(URL)
     * @see AbstractProtocol#exporterMap storing Exporter and Invoker
     *
     * Consuming (Provider side)
     * @see DubboProtocol#requestHandler
     * @see ExchangeHandlerAdapter#reply(ExchangeChannel, Object)
     * @see AbstractProtocol#serviceKey(int, String, String, String)
     * @see AbstractProtocol#exporterMap get target Invoker
     * @see Invoker#invoke(Invocation) start invocation chain
     */
    static void exportAndRefer() {

    }

    /**
     * Netty ChannelHandler for send/receive message in Channel
     * @see NettyServer#doOpen()
     * @see NettyServerHandler core ChannelHandler in Netty server
     * @see NettyServerHandler#channelRead(ChannelHandlerContext, Object)
     * @see NettyServerHandler#write(ChannelHandlerContext, Object, ChannelPromise)
     *
     * Dubbo ChannelHandler for decode/encode/send/receive message (Provider-side)
     * @see ChannelHandlers#wrapInternal(ChannelHandler, URL) Decorator mode for Dubbo ChannelHandler
     * @see MultiMessageHandler
     * @see HeartbeatHandler
     * @see AllChannelHandler
     * @see DecodeHandler provider side
     * @see HeaderExchangeHandler
     * @see DubboProtocol#requestHandler ExchangeHandler
     * @see ExchangeHandler#reply(ExchangeChannel, Object)
     */
    static void nettyInDubbo() {

    }

    /**
     * Message decode/encode
     * @see NettyCodecAdapter add deconder/encoder in Netty Channel Pipeline
     * @see NettyCodecAdapter#encoder
     * @see NettyCodecAdapter#decoder
     * @see Codec2 decode/encode mechanism interface
     * @see DubboCodec#decode decode message
     * @see DubboCodec#encode encode message (in superclass)
     * @see CodecSupport#getSerialization determine the serialized way
     * @see Serialization#deserialize(URL, InputStream)
     * @see Serialization#serialize(URL, OutputStream)
     * @see org.apache.dubbo.remoting.exchange.Request
     * @see org.apache.dubbo.remoting.exchange.Response
     * @see DecodeableRpcInvocation Request#data
     *
     * Serialized mechanism
     * @see Serialization
     * @see Hessian2Serialization default
     * @see JavaSerialization
     * @see GsonSerialization
     * @see KryoSerialization
     * @see KryoSerialization2
     * @see FstSerialization
     *
     * ObjectInput/Output
     * @see org.apache.dubbo.common.serialize.ObjectInput
     * @see org.apache.dubbo.common.serialize.ObjectOutput
     */
    static void serializedMechaism() {

    }

    /**
     * ClusterInvoker initializing with LoadBalance
     * @see ProtocolListenerWrapper#refer(Class, URL)
     * @see RegistryProtocol#refer(Class, URL) determine Cluster from url
     * @see MigrationRuleListener#onRefer(RegistryProtocol, ClusterInvoker, URL)
     * @see MigrationRuleHandler#doMigrate(String)
     * @see MigrationInvoker#migrateToServiceDiscoveryInvoker(boolean)
     * @see MigrationInvoker#refreshServiceDiscoveryInvoker()
     * @see MigrationInvoker#refreshInterfaceInvoker()
     * @see InterfaceCompatibleRegistryProtocol#getInvoker(Cluster, Registry, Class, URL)
     * @see RegistryProtocol#doCreateInvoker(DynamicDirectory, Cluster, Registry, Class)
     * @see Cluster#join(Directory)
     * @see FailoverCluster#doJoin(Directory) default strategy failover
     *
     * ClusterInvoker working flow
     * @see InvokerInvocationHandler#invoke(Object, Method, Object[])
     * @see MigrationInvoker#invoke(Invocation)
     * @see MockClusterInvoker#invoke(Invocation)
     * @see AbstractCluster.InterceptorInvokerNode apply interceptor on ClusterInvoker
     * @see AbstractClusterInvoker#initLoadBalance(List, Invocation) initialize LoadBalance strategy
     * @see FailoverClusterInvoker#doInvoke(Invocation, List, LoadBalance) invoke with LoadBalance
     *
     * LoadBalance strategy
     * see also /META-INF/dubbo.internals/org.apache.dubbo.rpc.cluster.LoadBalance
     * @see LoadBalance
     * @see RandomLoadBalance default strategy
     * @see FailoverClusterInvoker#doInvoke(Invocation, List, LoadBalance) invoke with LoadBalance
     * @see AbstractClusterInvoker#doSelect(LoadBalance, Invocation, List, List)
     * @see LoadBalance#select(List, URL, Invocation) select one Invoker for consuming
     *
     * Organizing multiple providers
     *  available providers at zookeeper: /dubbo/org.iushu.dubbo.provider.ItemWarehouse/providers
     * @see MigrationInvoker#refreshInterfaceInvoker()
     * @see RegistryDirectory#subscribe(URL)
     * @see ListenerRegistryWrapper#subscribe(URL, NotifyListener)
     * @see FailbackRegistry#subscribe(URL, NotifyListener)
     * @see ZookeeperRegistry#doSubscribe(URL, NotifyListener)
     * @see ZookeeperClient#addChildListener(String, ChildListener) get provider's urls from registry
     * @see ZookeeperRegistry#notify(URL, NotifyListener, List)
     * @see RegistryDirectory#notify(List)
     *
     * @see AvailableClusterInvoker find available one
     * @see BroadcastClusterInvoker throw if too much fail
     * @see FailsafeClusterInvoker only log if fail
     * @see FailoverClusterInvoker retry with LoadBalance if fail
     * @see FailfastClusterInvoker throw if fail
     * @see FailbackClusterInvoker timer retry if fail
     */
    static void failToleranceAndLoadBalanceMechanism() {

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

    /**
     * There are something preparatory work to do before export, like loading redis cache.
     * see <dubbo:service delay="5000"/> millisecond
     * @see org.apache.dubbo.config.ServiceConfig#delay
     */
    static void delayExportServiceCase() {

    }

    /**
     * limit server connection amount
     * provider see <dubbo:protocol .. accepts="10"/>
     * @see ProtocolConfig#accepts
     * consumer see <dubbo:reference .. connections="10"/>
     * @see ReferenceConfig#connections
     */
    static void limitConnectionAmount() {

    }

    /**
     * always stick connection to one provider in every connection
     * see <dubbo:reference .. stick="true"/>
     * @see ReferenceConfig#sticky
     * method-level see
     * <dubbo:reference ..>
     *   <dubbo:method name="methodName" sticky="true"/>
     * </dubbo:reference>
     * @see ReferenceConfig#methods
     */
    static void stickConnectionCase() {

    }

    /**
     * see <dubbo:protocol .. accesslog="true"/>
     */
    static void accessLogCase() {

    }

    /**
     * see /META-INF/dubbo.internal/org.apache.dubbo.container.Container
     * @see org.apache.dubbo.container.Container
     * @see org.apache.dubbo.container.spring.SpringContainer default
     * @see org.apache.dubbo.container.log4j.Log4jContainer
     * @see org.apache.dubbo.container.logback.LogbackContainer
     *
     * @see org.apache.dubbo.container.Main#main(String[])  container startup method
     * @see org.apache.dubbo.container.spring.SpringContainer#DEFAULT_SPRING_CONFIG default xml path
     */
    static void startupInContainerCase() {

    }

    /**
     * ReferenceConfig is used to store the connection metadata between consumer and registry and provider,
     * such a big config object requires caching up.
     * NOTE: what the ReferenceConfigCache cached is the proxied service interface.
     *
     * @see ReferenceConfigCache
     * @see ReferenceConfigCache#DEFAULT_KEY_GENERATOR generated by interface's name & group & version
     */
    static void referenceConfigCacheCase() {
        ReferenceConfig<ItemWarehouse> referenceConfig = new ReferenceConfig<>();

        // set configurations to ReferenceConfig ..

        ReferenceConfigCache referenceConfigCache = ReferenceConfigCache.getCache();    // create and cache itself
        ItemWarehouse itemWarehouse = referenceConfigCache.get(referenceConfig);        // create and cache proxied ref
        Item item = itemWarehouse.getItem(211334);
        System.out.println(item);

        referenceConfigCache.destroy(referenceConfig);  // destroy and release memory
    }

    /**
     * manage dubbo
     * @see Protocol#export(Invoker)
     * @see QosProtocolWrapper#export(Invoker)
     * @see QosProtocolWrapper#startQosServer(URL) with host and port(22222)
     * @see Server#start()
     * @see QosProcessHandler#decode(ChannelHandlerContext, ByteBuf, List)
     * @see org.apache.dubbo.qos.server.handler.HttpProcessHandler managed by http
     * @see org.apache.dubbo.qos.server.handler.TelnetProcessHandler managed by telnet
     *
     * manage command: help ls online offline ready version publish quit
     * see /META-INF/dubbo.internal/org.apache.dubbo.qos.command.BaseCommand
     * @see org.apache.dubbo.qos.command.CommandContext
     * @see org.apache.dubbo.qos.command.CommandContextFactory
     * @see org.apache.dubbo.qos.command.CommandExecutor
     * @see org.apache.dubbo.qos.command.DefaultCommandExecutor
     */
    static void remoteManageCase() {

    }

    /**
     * @see org.apache.dubbo.monitor.MonitorService
     * @see org.apache.dubbo.monitor.dubbo.DubboMonitor
     * @see org.apache.dubbo.monitor.dubbo.DubboMonitorFactory
     * @see org.apache.dubbo.monitor.support.MonitorServiceDetector
     */
    static void monitorService() {

    }

    public static void main(String[] args) {
//        proxyObject();
//        gettingStartedProviderBootstrap();
//        gettingStartedCase();
//        gettingStartedBootstrapCase();
//        proxyFactoryInvoker();
//        registryProviderCase();
        registryConsumerCase();
    }

}
