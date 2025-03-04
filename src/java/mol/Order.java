package mol;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    private int id;
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double totalMoney;
    private Date date;
    private String payment;// Change to Date type

    public Order() {
    }

    public Order(int id, int userId, String name, String email, String phone, String address, double totalMoney, Date date, String payment) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.totalMoney = totalMoney;
        this.date = date;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public Date getDate() {
        return date; // Return Date type
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setDate(Date date) {
        this.date = date; // Set Date type
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date != null ? sdf.format(date) : "N/A";
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", userId=" + userId
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", address='" + address + '\''
                + ", totalMoney=" + totalMoney
                + ", date=" + (date != null ? getFormattedDate() : "N/A")
                + ", payment='" + payment + '\''
                + '}';
    }
}

