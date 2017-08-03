package me.ehlxr.test;

import java.math.BigDecimal;
import java.util.Date;

public class StatisByHourModel {
    private Date time;

    private String creativeid;

    private Short category;

    private Integer imprs;

    private Integer clks;

    private BigDecimal cost;

    private Integer downloads;

    private Integer regists;

    private Integer flag;

    private Date createtime;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCreativeid() {
        return creativeid;
    }

    public void setCreativeid(String creativeid) {
        this.creativeid = creativeid == null ? null : creativeid.trim();
    }

    public Short getCategory() {
        return category;
    }

    public void setCategory(Short category) {
        this.category = category;
    }

    public Integer getImprs() {
        return imprs;
    }

    public void setImprs(Integer imprs) {
        this.imprs = imprs;
    }

    public Integer getClks() {
        return clks;
    }

    public void setClks(Integer clks) {
        this.clks = clks;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Integer getRegists() {
        return regists;
    }

    public void setRegists(Integer regists) {
        this.regists = regists;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}