package org.iushu.formatter.beans;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author iuShu
 * @since 1/28/21
 */
public class Model {

    @NumberFormat(style = Style.CURRENCY)
    private BigDecimal rate;

    @DateTimeFormat(iso = ISO.DATE)
    private Date date;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private String dateDesc;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDesc() {
        return dateDesc;
    }

    public void setDateDesc(String dateDesc) {
        this.dateDesc = dateDesc;
    }

    @Override
    public String toString() {
        return "Model{" +
                "rate=" + rate +
                ", date=" + date +
                ", dateDesc='" + dateDesc + '\'' +
                '}';
    }
}
