package org.iushu.resources.beans;

import org.springframework.core.io.Resource;

/**
 * @author iuShu
 * @since 1/25/21
 */
public class Network {

    private String IP;
    private Resource configOfISP;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public Resource getConfigOfISP() {
        return configOfISP;
    }

    public void setConfigOfISP(Resource configOfISP) {
        this.configOfISP = configOfISP;
    }

    @Override
    public String toString() {
        return "Network{" +
                "IP='" + IP + '\'' +
                ", configOfISP=" + configOfISP +
                '}';
    }
}
