package toppan.example.toppan.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class WorkToppan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date; // Дата  информации  ( 01 -число каждого месяца )
    //  Sn -Прнтенра
    private String Sn, TotalCount, RepairStartDate, RepairEndDate, Price, TSC, Prim, ip;

    public WorkToppan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSn() {
        return Sn;
    }

    public void setSn(String sn) {
        Sn = sn;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getRepairStartDate() {
        return RepairStartDate;
    }

    public void setRepairStartDate(String repairStartDate) {
        RepairStartDate = repairStartDate;
    }

    public String getRepairEndDate() {
        return RepairEndDate;
    }

    public void setRepairEndDate(String repairEndDate) {
        RepairEndDate = repairEndDate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTSC() {
        return TSC;
    }

    public void setTSC(String TSC) {
        this.TSC = TSC;
    }

    public String getPrim() {
        return Prim;
    }

    public void setPrim(String prim) {
        Prim = prim;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public WorkToppan(Date date, String sn, String totalCount, String repairStartDate, String repairEndDate, String price, String TSC, String prim, String ip) {
        this.date = date;
        Sn = sn;
        TotalCount = totalCount;
        RepairStartDate = repairStartDate;
        RepairEndDate = repairEndDate;
        Price = price;
        this.TSC = TSC;
        Prim = prim;
        this.ip = ip;
    }
}
