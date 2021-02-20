package org.iushu.ioc.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author iuShu
 * @since 2/20/21
 */
public class Packet {

    private String pid;
    private String name;
    private String sender;
    private String from;
    private long sendDate;
    private String receiver;
    private String to;
    private long receiveDate;

    @Autowired
    @Qualifier("ivy")
    private Deliver carrier;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getSendDate() {
        return sendDate;
    }

    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(long receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Deliver getCarrier() {
        return carrier;
    }

    public void setCarrier(Deliver carrier) {
        this.carrier = carrier;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", sender='" + sender + '\'' +
                ", from='" + from + '\'' +
                ", sendDate=" + sendDate +
                ", receiver='" + receiver + '\'' +
                ", to='" + to + '\'' +
                ", receiveDate=" + receiveDate +
                ", carrier=" + carrier +
                '}';
    }
}
