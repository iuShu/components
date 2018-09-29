package com.wqt.jndi.tomcat.custom;

import javax.naming.Reference;
import javax.naming.StringRefAddr;

/**
 * Created by iuShu on 18-9-27
 */
public class CustomResource {

    private String param = "default";
    private String url = "http://res.default.cn";
    private int amounts = 1;
    private boolean cachable;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public boolean isCachable() {
        return cachable;
    }

    public void setCachable(boolean cachable) {
        this.cachable = cachable;
    }

    @Override
    public String toString() {
        return "CustomResource{" +
                "param='" + param + '\'' +
                ", url='" + url + '\'' +
                ", amounts=" + amounts +
                ", cachable=" + cachable +
                '}';
    }

    @Deprecated
    public Reference getReference() {
        Reference ref = new Reference(this.getClass().getName(), CustomResourceFactory.class.getName(), null);
        ref.add(new StringRefAddr("param", getParam()));
        ref.add(new StringRefAddr("url", getUrl()));
        ref.add(new StringRefAddr("amounts", String.valueOf(getAmounts())));
        ref.add(new StringRefAddr("cachable", String.valueOf(isCachable())));
        return ref;
    }
}
