/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mol;

/**
 *
 * @author ADMIN
 */
public class Sold {

    private int sid;
    private int pid;
    private String name;
    private double price;
    private int amount;
    private int sellId;
    private String time;
    private String image;
    private String descrip;
    private int buyId;

    public Sold() {
    }

    public Sold(int sid, int pid, String name, double price, int amount, int sellId, String time, String image, String descrip, int buyId) {
        this.sid = sid;
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.sellId = sellId;
        this.time = time;
        this.image = image;
        this.descrip = descrip;
        this.buyId = buyId;

    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    public int getBuyId() {
        return buyId;
    }

    public String getImage() {
        return image;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public int getSid() {
        return sid;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getSellId() {
        return sellId;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSellId(int sellId) {
        this.sellId = sellId;
    }

    @Override
    public String toString() {
        return "Sold{" + "sid=" + sid + ", pid=" + pid + ", buyId=" + buyId + ", name=" + name + ", price=" + price + ", amount=" + amount + ", sellId=" + sellId + ", time=" + time + ", image=" + image + ", descrip=" + descrip + '}';
    }

}
