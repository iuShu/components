package org.iushu.dubbo;

import org.apache.dubbo.config.*;
import org.iushu.dubbo.bean.Item;
import org.iushu.dubbo.provider.CenterItemWarehouse;
import org.iushu.dubbo.provider.ItemWarehouse;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
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
        protocolConfig.setPort(2088);
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
        referenceConfig.setUrl("dubbo://192.168.61.137:2088/org.iushu.dubbo.provider.ItemWarehouse");
        referenceConfig.setInterface(ItemWarehouse.class);
        referenceConfig.setVersion("1.0.0");

        ItemWarehouse itemWarehouse = referenceConfig.get();
        Item item = itemWarehouse.getItem(new Random().nextInt(1000000));
        System.out.println(item);
    }

    static void gettingStartedCase() {
        new Thread(Application::gettingStartedProvider, "provider").start();
        new Thread(Application::gettingStartedConsumer, "consumer").start();
        new Thread(Application::gettingStartedConsumer, "consumer").start();
    }

    public static void main(String[] args) {
        gettingStartedCase();
    }

}
