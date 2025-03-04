/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mol;

/**
 *
 * @author ADMIN
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private String image;
    private int storage;
    private String color;
    private String quantity;
    private String description;
    private int sellId;
    private int amount;

    public Product(int id, String name, double price, String image,int storage, String color, String quantity, String description, int sellId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.storage=storage;
        this.color = color;
        this.quantity = quantity;
        this.description = description;
        this.sellId = sellId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getColor() {
        return color;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public int getSellId() {
        return sellId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSellId(int sellId) {
        this.sellId = sellId;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", image=" + image + ", storage=" + storage + ", color=" + color + ", quantity=" + quantity + ", description=" + description + ", sellId=" + sellId + '}';
    }

  

}
