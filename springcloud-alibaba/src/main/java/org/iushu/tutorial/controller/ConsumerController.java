package org.iushu.tutorial.controller;

import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author iuShu
 * @since 8/2/21
 */
@RestController
@Profile("consumer")
public class ConsumerController {

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${nacos.provider.name}")
    private String providerServiceName;

    @RequestMapping("/consumer/get")
    public String getStock() {
        try {
            // using NamingServiceManager might not be a right way
            NamingService namingService = nacosServiceManager.getNamingService(null);
            Instance instance = namingService.selectOneHealthyInstance(providerServiceName);
            String service = "/provider/stock";
            String url = String.format("http://%s%s", instance.toInetAddr(), service);
            String body = restTemplate.getForObject(url, String.class);
            return "consume get " + body;
        } catch (NacosException e) {
            e.printStackTrace();
            return e.getErrMsg();
        }
    }

}
