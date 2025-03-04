package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import mol.AverageStar;
import mol.Category;
import mol.Countnumbersold;
import mol.FeedBack;
import mol.Order;
import mol.Product;
import mol.Sold;

public class DAO extends DBContext {

    public List<Product> getAllProducts() {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");
                Product product = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }
        return productList;
    }

    public Product getProductsByID(int id) {
        Product product = null; // Declare the product outside the loop
        String sql = "SELECT * FROM products WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) { // Use try-with-resources
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Check if there is a result
                    String name = rs.getString("model");
                    double price = rs.getDouble("price");
                    String image = rs.getString("image_url");
                    int storage = rs.getInt("storage");
                    String color = rs.getString("color");
                    String quantity = rs.getString("stock");
                    String description = rs.getString("description");
                    int sellId = rs.getInt("sellId");
                    product = new Product(id, name, price, image, storage, color, quantity, description, sellId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging
        }

        return product; // Return null if no product found
    }

    public Product getProductsByName(String name) {
        Product product = null; // Declare the product outside the loop
        String sql = "SELECT * FROM products WHERE model=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) { // Use try-with-resources
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Check if there is a result
                    int id = rs.getInt("id");
                    double price = rs.getDouble("price");
                    String image = rs.getString("image_url");
                    int storage = rs.getInt("storage");
                    String color = rs.getString("color");
                    String quantity = rs.getString("stock");
                    String description = rs.getString("description");
                    int sellId = rs.getInt("sellId");
                    product = new Product(id, name, price, image, storage, color, quantity, description, sellId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging
        }

        return product; // Return null if no product found
    }

    public List<Category> getAllCategorys() {
        PreparedStatement ps;
        ResultSet rs;
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM category";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                String cname = rs.getString("cname");

                Category category = new Category(cid, cname);
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public List<Product> getNewProducts() {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> newProducts = new ArrayList<>();
        String sql = "SELECT products.*,newproducts.release_date FROM newproducts\n"
                + "join products on products.id=newproducts.product_id";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");
                Product newProduct = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
                newProducts.add(newProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }
        return newProducts;
    }

    public List<Product> getSaleProducts() {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> saleProducts = new ArrayList<>();
        String sql = "select products.*,saleProducts.discount,saleProducts.start_date,"
                + "saleProducts.end_date from saleProducts\n"
                + "join products on products.id=saleProducts.product_id";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");
                Product saleProduct = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
                saleProducts.add(saleProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }
        return saleProducts;
    }

    public List<Product> getBestSellerProducts() {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> bestSellerProducts = new ArrayList<>();
        String sql = "SELECT products.*,bestsellers.quantity_sold,"
                + "bestsellers.total_revenue FROM bestsellers\n"
                + "join products on products.id=bestsellers.product_id";

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");
                Product bestSellerProduct = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
                bestSellerProducts.add(bestSellerProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }
        return bestSellerProducts;
    }

    // DAO.java
    public Product addProduct(int id, String name, double price, String image, int storage,
            String color, String quantity, String description, int sellerId) {
        String sql = "INSERT INTO products  "
                + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setString(4, image);
            ps.setInt(5, storage);
            ps.setString(6, color);
            ps.setString(7, quantity);
            ps.setString(8, description);
            ps.setInt(9, sellerId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return new Product(id, name, price, image, storage, color, quantity, description, sellerId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return null;
    }

    public Product MyProduct(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product myProduct = null;  // Single product instead of a list
        String sql = "SELECT * FROM products WHERE id= ? ";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);  // Set the parameter for the ID
            rs = ps.executeQuery();

            if (rs.next()) {  // Expect only one result
                double price = rs.getDouble("price");
                String modelName = rs.getString("model");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");

                myProduct = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);  // Assign the product
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();  // Log closing errors
            }
        }
        return myProduct;  // Return a single product
    }

    public List<Product> getProductbySellId(int sellId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> PsellIds = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE sellId = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, sellId);  // Set the parameter for the ID
            rs = ps.executeQuery();

            while (rs.next()) {  // Loop through all results
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                String modelName = rs.getString("model");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");

                Product PsellId = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);
                PsellIds.add(PsellId);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                // It's a good idea to close the connection as well if you're done with it
                // if (connection != null) {
                //     connection.close();
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return PsellIds;
    }

    public List<Product> PagingProducts(int index) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> pagings = new ArrayList<>();
        String query = "select*from products\n"
                + "order by id\n"
                + "offset ? rows fetch next 8 rows only;";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, (index - 1) * 8);  // Set the parameter for the ID
            rs = ps.executeQuery();

            while (rs.next()) {  // Loop through all results
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                String modelName = rs.getString("model");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");

                Product paging = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);
                pagings.add(paging);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                // It's a good idea to close the connection as well if you're done with it
                // if (connection != null) {
                //     connection.close();
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pagings;
    }

    public Product updateProduct(int id, String name, double price, String image, int storage,
            String color, String quantity, String description, int sellerId) {
        String sql = "UPDATE products SET "
                + "model = ?, "
                + "price = ?, "
                + "image_url = ?, "
                + "storage = ?, "
                + "color = ?, "
                + "stock = ?, "
                + "description = ?, "
                + "sellId = ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set parameters correctly
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, image);
            ps.setInt(4, storage);
            ps.setString(5, color);
            ps.setString(6, quantity); // Assuming quantity is numeric (int)
            ps.setString(7, description);
            ps.setInt(8, sellerId);
            ps.setInt(9, id); // id is set last for the WHERE clause

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                // Return the updated product if successful
                return new Product(id, name, price, image, storage, color, quantity, description, sellerId);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return null;
    }

    public void deleteProducts(int id) {
        PreparedStatement ps;
        String sql = "DELETE FROM products WHERE id=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id); // Set the id parameter
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Product> searchByName(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> searchs = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE model LIKE ?";  // Corrected SQL query

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");  // Set the parameter for the model name
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                String modelName = rs.getString("model");  // Correctly retrieve model_name from the database
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");

                Product search = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);
                searchs.add(search);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();  // Log closing errors
            }
        }
        return searchs;
    }

    public List<Product> getRelateProduct(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> searchs = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE model LIKE ?";  // Corrected SQL query

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");  // Set the parameter for the model name
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                String modelName = rs.getString("model");  // Correctly retrieve model_name from the database
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");

                Product search = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);
                searchs.add(search);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();  // Log closing errors
            }
        }
        return searchs;
    }

    public List<Product> filterProducts(String model, double minPrice, double maxPrice, String color, int storage) {
        List<Product> filters = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM Products "
                + "WHERE [model] LIKE ? "
                + "AND Price BETWEEN ? AND ? "
                + "AND Color = ? "
                + "AND [storage] = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + model + "%");
            ps.setDouble(2, minPrice); // Giá thấp nhất
            ps.setDouble(3, maxPrice); // Giá cao nhất
            ps.setString(4, color);
            ps.setInt(5, storage);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    double price = rs.getDouble("price");
                    String modelName = rs.getString("model");
                    String image = rs.getString("image_url");
                    int storageCapacity = rs.getInt("storage");
                    String productColor = rs.getString("color");
                    String quantity = rs.getString("stock");
                    String description = rs.getString("description");
                    int sellId = rs.getInt("sellId");

                    Product product = new Product(id, modelName, price, image, storageCapacity, productColor, quantity, description, sellId);
                    filters.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filters;
    }

    public List<Sold> getSoldProductsBySellId(int sellId) {
        List<Sold> soldProducts = new ArrayList<>();
        String query = "SELECT sp.id AS sold_id, sp.product_id, sp.name, sp.price, sp.amount, sp.sold_time, sp.sellID, "
                + "p.image_url, p.description, o.user_id "
                + "FROM projectPrj301.dbo.sold_products sp "
                + "JOIN projectPrj301.dbo.products p ON p.id = sp.product_id "
                + "JOIN projectPrj301.dbo.orders o ON sp.sold_time = o.order_date "
                + "WHERE sp.sellID = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sellId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sold sold = new Sold(
                            rs.getInt("sold_id"),
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("amount"),
                            rs.getInt("sellId"),
                            rs.getString("sold_time"),
                            rs.getString("image_url"),
                            rs.getString("description"),
                            rs.getInt("user_id")
                    );
                    soldProducts.add(sold);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc dùng Logger để ghi log
        }
        return soldProducts;
    }

    public List<Sold> getSoldProductsByOrder(String dtime) {
        List<Sold> soldProductsx = new ArrayList<>();
        String query = "SELECT \n"
                + "    orders.order_date,orders.user_id, \n"
                + "    sold_products.id AS sold_products_id,\n"
                + "    sold_products.product_id, sold_products.name, sold_products.sellID, sold_products.price, sold_products.amount,\n"
                + "    p.image_url, p.description \n"
                + "FROM \n"
                + "    projectPrj301.dbo.sold_products AS sold_products\n"
                + "JOIN projectPrj301.dbo.products p ON p.id = sold_products.product_id\n"
                + "JOIN projectPrj301.dbo.orders AS orders ON CONVERT(VARCHAR(19), sold_products.sold_time, 120) = CONVERT(VARCHAR(19), orders.order_date, 120)\n"
                + "WHERE \n"
                + "    CONVERT(VARCHAR(19), orders.order_date, 120) LIKE ?;";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + dtime + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int soldProductId = rs.getInt("sold_products_id");
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                int sellId = rs.getInt("sellID");
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");
                String image = rs.getString("image_url");
                String description = rs.getString("description");
                int buyId = rs.getInt("user_id");

                // Tạo đối tượng Sold và thêm vào danh sách
                Sold sold = new Sold(soldProductId, productId, name, price, amount, sellId, dtime, image, description, buyId);
                soldProductsx.add(sold);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soldProductsx;
    }

    public List<Sold> getSoldProductsByUserId(int id) {
        List<Sold> soldProductsx = new ArrayList<>();
        String query = "SELECT \n"
                + "    orders.order_date, orders.user_id,\n"
                + "    sold_products.id AS sold_products_id,\n"
                + "    sold_products.product_id, sold_products.name, sold_products.sellID, sold_products.price, sold_products.amount,\n"
                + "    p.image_url, p.description \n"
                + "FROM \n"
                + "    projectPrj301.dbo.sold_products AS sold_products\n"
                + "JOIN projectPrj301.dbo.products p ON p.id = sold_products.product_id\n"
                + "JOIN projectPrj301.dbo.orders AS orders ON CONVERT(VARCHAR(19), sold_products.sold_time, 120) = CONVERT(VARCHAR(19), orders.order_date, 120)\n"
                + "WHERE \n"
                + "   orders.user_id=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int soldProductId = rs.getInt("sold_products_id");
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                int sellId = rs.getInt("sellID");
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");
                String dtime = rs.getString("order_date");
                String image = rs.getString("image_url");
                String description = rs.getString("description");
                int buyId = rs.getInt("user_id");

                // Tạo đối tượng Sold và thêm vào danh sách
                Sold sold = new Sold(soldProductId, productId, name, price, amount, sellId, dtime, image, description, buyId);
                soldProductsx.add(sold);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soldProductsx;
    }

    public List<Sold> getSoldProductsBySellIds(int id) {
        List<Sold> soldProductsx = new ArrayList<>();
        String query = "SELECT \n"
                + "    orders.order_date, orders.user_id,\n"
                + "    sold_products.id AS sold_products_id,\n"
                + "    sold_products.product_id, sold_products.name, sold_products.sellID, sold_products.price, sold_products.amount,\n"
                + "    p.image_url, p.description \n"
                + "FROM \n"
                + "    projectPrj301.dbo.sold_products AS sold_products\n"
                + "JOIN projectPrj301.dbo.products p ON p.id = sold_products.product_id\n"
                + "JOIN projectPrj301.dbo.orders AS orders ON CONVERT(VARCHAR(19), sold_products.sold_time, 120) = CONVERT(VARCHAR(19), orders.order_date, 120)\n"
                + "WHERE \n"
                + "   sold_products.sellId=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int soldProductId = rs.getInt("sold_products_id");
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                int sellId = rs.getInt("sellID");
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");
                String dtime = rs.getString("order_date");
                String image = rs.getString("image_url");
                String description = rs.getString("description");
                int buyId = rs.getInt("user_id");

                // Tạo đối tượng Sold và thêm vào danh sách
                Sold sold = new Sold(soldProductId, productId, name, price, amount, sellId, dtime, image, description, buyId);
                soldProductsx.add(sold);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soldProductsx;
    }

    public void addSoldProduct(int productId, String name, double price, int amount, int sellId) {
        String query = "INSERT INTO sold_products (product_id, name, price, amount, sellId) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, amount);
            ps.setInt(5, sellId);

            ps.executeUpdate();  // Thực hiện câu lệnh INSERT
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFeedBack(int soldProductId, String userName, String feedbackContent, int rating) {
        String query = "INSERT INTO dbo.Feedback (sold_product_id, user_name, feedback_content, rating) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, soldProductId);
            ps.setString(2, userName);
            ps.setString(3, feedbackContent);
            ps.setInt(4, rating);

            int rowsAffected = ps.executeUpdate();  // Kiểm tra số dòng bị ảnh hưởng
            if (rowsAffected > 0) {
                System.out.println("Feedback inserted successfully");
            } else {
                System.out.println("No feedback inserted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FeedBack> getFeedbackBySoldId(int Soldpid) {
        List<FeedBack> Feedbacks = new ArrayList<>();
        String query = "SELECT * FROM Feedback WHERE sold_product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, Soldpid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                int id = rs.getInt("feedback_id");
                int pid = rs.getInt("sold_product_id");
                String name = rs.getString("user_name");
                String contend = rs.getString("feedback_content");
                int rate = rs.getInt("rating");
                String time = rs.getString("feedback_date");
                FeedBack feed = new FeedBack(id, pid, name, contend, rate, time);
                Feedbacks.add(feed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Feedbacks;
    }

    public List<FeedBack> getFeedbackByProductId(int productId) {
        String query = "SELECT Feedback.*, sold_products.product_id FROM sold_products "
                + "JOIN Feedback ON sold_products.id = Feedback.sold_product_id "
                + "WHERE Feedback.sold_product_id = ?";
        List<FeedBack> feedbackList = new ArrayList<>(); // Danh sách để lưu phản hồi

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("feedback_id");
                int pid = rs.getInt("sold_product_id");
                String name = rs.getString("user_name");
                String contend = rs.getString("feedback_content");
                int rate = rs.getInt("rating");
                String time = rs.getString("feedback_date");

                FeedBack feed = new FeedBack(id, pid, name, contend, rate, time);
                feedbackList.add(feed); // Thêm phản hồi vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList; // Trả về danh sách phản hồi
    }

    public List<Product> SortProductbyPriceASC(int pageIndex) {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY price ASC OFFSET ? row fetch next 8 rows only";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (pageIndex - 1) * 8); // Tính vị trí bắt đầu của sản phẩm dựa trên trang hiện tại
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");
                Product product = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để kiểm tra
        }
        return productList;
    }

    public List<Product> SortProductbyPriceDESC(int pageIndex) {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY price DESC OFFSET ? row fetch next 8 rows only";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (pageIndex - 1) * 8); // Tính vị trí bắt đầu của sản phẩm dựa trên trang hiện tại
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                String image = rs.getString("image_url");
                int storage = rs.getInt("storage");
                String color = rs.getString("color");
                String quantity = rs.getString("stock");
                String descrip = rs.getString("description");
                int sellId = rs.getInt("sellId");
                Product product = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để kiểm tra
        }
        return productList;
    }

    public List<Product> SortProductbyBestSellerDESC(int pageIndex) {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.id AS product_id,\n"
                + "    COUNT(sp.product_id) AS product_count,\n"
                + "    p.model, \n"
                + "    p.[price],\n"
                + "    CAST(p.image_url AS NVARCHAR(MAX)) AS image_url -- Chuyển đổi kiểu dữ liệu tạm thời\n"
                + "FROM [projectPrj301].[dbo].[products] p\n"
                + "LEFT JOIN [projectPrj301].[dbo].[sold_products] sp\n"
                + "    ON p.id = sp.product_id\n"
                + "GROUP BY p.id, p.price, p.model, CAST(p.image_url AS NVARCHAR(MAX))\n"
                + "ORDER BY product_count DESC\n"
                + " OFFSET ? row fetch next 8 rows only";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (pageIndex - 1) * 8); // Tính vị trí bắt đầu của sản phẩm dựa trên trang hiện tại
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                double totalAmount = rs.getDouble("product_count"); // Lấy tổng số lượng bán cho sản phẩm
                int amount = 0;
                int sellid = 0;
                String dtime = "";
                String image = rs.getString("image_url");  // Nếu bạn có bảng riêng cho thông tin sản phẩm
                int storage = 0;
                String color = "";
                String quantity = "";
                String des = "";
                int buyid = 0;

                Product product = new Product(id, name, price, image, storage, color, quantity, des, sellid);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }
        return productList;
    }

    public List<Product> SortProductbyAverageFeedbackDESC(int pageIndex) {
        PreparedStatement ps;
        ResultSet rs;
        List<Product> productList = new ArrayList<>();
        String sql = " SELECT \n"
                + "    p.id AS product_id,\n"
                + "    COUNT(fb.sold_product_id) AS product_count,\n"
                + "    p.model, \n"
                + "    p.price,\n"
                + "    SUM(fb.rating) AS rate, \n"
                + "    ROUND(CAST(SUM(fb.rating) AS FLOAT) / NULLIF(COUNT(fb.sold_product_id), 0), 1) AS Average,  -- Làm tròn và giữ 1 chữ số sau dấu thập phân\n"
                + "    CAST(p.image_url AS NVARCHAR(MAX)) AS image_url -- Chuyển đổi kiểu dữ liệu tạm thời\n"
                + "FROM [projectPrj301].[dbo].[products] p\n"
                + "LEFT JOIN [projectPrj301].[dbo].Feedback fb\n"
                + "    ON p.id = fb.sold_product_id\n"
                + "GROUP BY p.id, p.model, p.price, CAST(p.image_url AS NVARCHAR(MAX))\n"
                + "ORDER BY Average DESC, product_count DESC  OFFSET ? row fetch next 8 rows only;";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (pageIndex - 1) * 8); // Tính vị trí bắt đầu của sản phẩm dựa trên trang hiện tại
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("model");
                double price = rs.getDouble("price");
                double totalAmount = rs.getDouble("product_count"); // Lấy tổng số lượng bán cho sản phẩm
                int amount = 0;
                int sellid = 0;
                String dtime = "";
                String image = rs.getString("image_url");  // Nếu bạn có bảng riêng cho thông tin sản phẩm
                int storage = 0;
                String color = "";
                String quantity = "";
                String des = "";
                int buyid = 0;

                Product product = new Product(id, name, price, image, storage, color, quantity, des, sellid);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
        }
        return productList;
    }

    public Countnumbersold getCountNumberOfsold(int pid) {
        PreparedStatement ps;
        ResultSet rs;
        Countnumbersold count = null;

        // Modify the SQL to filter by product ID
        String sql = "SELECT \n"
                + "    p.id AS product_id,\n"
                + "    COUNT(sp.product_id) AS product_count\n"
                + "FROM [projectPrj301].[dbo].[products] p\n"
                + "LEFT JOIN [projectPrj301].[dbo].[sold_products] sp\n"
                + "    ON p.id = sp.product_id\n"
                + "WHERE p.id = ?\n" // Add WHERE clause to filter by product ID
                + "GROUP BY p.id";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);  // Set the product ID for filtering
            rs = ps.executeQuery();

            if (rs.next()) {
                int counts = rs.getInt("product_count");
                count = new Countnumbersold(pid, counts);  // Create Countnumbersold object
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log or print the exception for debugging
        }

        return count;  // Return the result
    }

    public AverageStar getAverageFeedback(int pid) {
        PreparedStatement ps;
        ResultSet rs;
        AverageStar avg = null;

        // Modify the SQL to filter by product ID and calculate average correctly
        String sql = "SELECT \n"
                + "    p.id AS product_id,\n"
                + "    COUNT(fb.sold_product_id) AS product_count,\n"
                + "    SUM(fb.rating) AS total_rating, \n"
                + "    ROUND(CAST(SUM(fb.rating) AS FLOAT) / NULLIF(COUNT(fb.sold_product_id), 0), 1) AS Average  -- Làm tròn và giữ 1 chữ số sau dấu thập phân\n"
                + "FROM [projectPrj301].[dbo].[products] p\n"
                + "LEFT JOIN [projectPrj301].[dbo].Feedback fb\n"
                + "    ON p.id = fb.sold_product_id\n"
                + "WHERE p.id = ?\n" // Add WHERE clause to filter by product ID
                + "GROUP BY p.id";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, pid);  // Set the product ID for filtering
            rs = ps.executeQuery();

            if (rs.next()) {
                int counts = rs.getInt("product_count");
                double avgs = rs.getDouble("Average"); // Get the average rating as double
                avg = new AverageStar(pid, counts, avgs);  // Create AverageStar object
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log or print the exception for debugging
        }

        return avg;  // Return the result
    }

//   public Order newOrder() {
//        String sql = "SELECT TOP 1 *\n"
//                + "FROM orders\n"
//                + "ORDER BY id DESC;";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                        int id=rs.getInt("id");
//                        int uid=rs.getInt("user_id");
//                       String name= rs.getString("name");
//                        String email=rs.getString("email");
//                       String phone= rs.getString("phone");
//                        String rs.getString("address");
//                        double money=rs.getDouble("money");
//                       Date date= rs.getDate("order_date");
//                        String pay=rs.getString("payment");
//
//            }
//
//        } catch (Exception e) {
//        }
//        return null;
//    }
    public static void main(String[] args) {
        DAO dao = new DAO();
        AverageStar p = dao.getAverageFeedback(18);
        System.out.println(p);
//    }
//         List<Sold> sold = dao.getSoldProductsByOrder("2024-12-07 14:53:30");
//        List<Product> p = dao.SortProductbyBestSellerDESC(1);
//
//        for (Product product : p) {
//            System.out.println(product);
    }
}
//        dao.addFeedBack(29, "le the nam", "hang dep", 5);
//        List<FeedBack> p = dao.getFeedbackByProductId(20);
//        List<Product> p = dao.addSoldProduct(0, "iPhone 14 Minix", "iPhone 14", 2, 1061);
//        for (Product product : p) {
//            System.out.println(product);
//        }

//        List<Product> Filter = dao.filterProducts("iPhone 14", 799.00, 9000.00, "Blue", 128);
//        for (Product product : Filter) {
//            System.out.println(product);
//        }
//Order o=dao.newOrder();
// System.out.println(o);
//        List<FeedBack> l = dao.getFeedbackByProductId(19);
//        
//        List<Sold> p = dao.getSoldProductsBySellId(1061);
//        System.out.println(p);
//        Product p = dao.getProductsByName("iPhone 15 Pro");
//        System.out.println(p);
//        List<Product> list = dao.getRelateProduct("iphone 14");
//        for (Product product : list) {
//            System.out.println(product);
//              List<Product> list = dao.searchByName("128");
//
//        for (Product product : list) {
//            System.out.println(product);
//        dao.deleteProducts(29); // Delete product with id 29
//Product product = dao.updateProduct(
//                25,
//                "iPhone 14 Pro Max",
//                999.99,
//                "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6487/6487406_sd.jpg",
//                128,
//                "Blue",
//                "31", // Changed quantity to int
//                "Apple - iPhone 14 Pro Max 128GB - Deep Purple (Verizon)",
//                3
//        );
//
//        if (product != null) {
//            System.out.println("Product updated successfully: " + product);
//        } else {
//            System.out.println("Failed to update product.");
//        }
//Product product = dao.addProduct(
//                25,
//                "Iphone 14 Pro Max",
//                999.00,
//                "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6487/6487406_sd.jpg",
//                128,
//                "purple",
//                "30",
//                "Apple - iPhone 14 Pro Max 128GB - Deep Purple (Verizon)",
//                1
//        );
//
//        if (product != null) {
//            System.out.println("Product added successfully: " + product);
//        } else {
//            System.out.println("Failed to add product.");
//        }
//        List<Product> list = dao.getNewProducts();
//        for (Product product : list) {
//            System.out.println(product);
////        } 
//        Product c = dao.getProductsByID(7);
//
//        System.out.println(c);
//List<Product> list = dao.getSaleProducts();
//        for (Product product : list) {
//            
//        dao.deleteProducts("22");
//        }
//        List<Product> list = dao.addProducts(22, "Iphone 16 Pro Max", "2899.9", "https://cdn.tgdd.vn/Products/Images/42/329149/iphone-16-pro-max-tu-nhien-thumb-600x600.jpg", 512, "Black", 20, "The iPhone 16 Pro Max is equipped with the A17 Pro chip and features a large 6.7-inch display with Super Retina XDR technology", "IP16PROMAX-512-BLACK");
//
//        for (Product product : list) {
//            System.out.println(product);
//        }
//        List<Product> list = dao.getAllProducts();
//        for (Product product : list) {
//            System.out.println(product);
//        }
//        List<Product> list1 = dao.getSaleList();
//        for (Product l : list1) {
//            System.out.println(l);
// public List<Product> getSaleList() {
//        PreparedStatement ps;
//        ResultSet rs;
//        List<Product> saleProducts = new ArrayList<>();
//        String sql = "select products.*,saleProducts.discount,saleProducts.start_date,"
//                + "saleProducts.end_date from saleProducts\n"
//                + "join products on products.id=saleProducts.product_id";
//
//        try {
//            ps = connection.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("model_name");
//                double price = rs.getDouble("price");
//                String image = rs.getString("image_url");
//                String storage = rs.getString("storage");
//                String color = rs.getString("color");
//                String quantity = rs.getString("stock_quantity");
//                String descrip = rs.getString("description");
//                String sellId = rs.getString("sellId");
//                Product saleProduct = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
//                saleProducts.add(saleProduct);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();  // Log the error for debugging
//        }
//        return saleProducts;
//    }
//    
//     public List<Product> getBestSellerList() {
//        PreparedStatement ps;
//        ResultSet rs;
//        List<Product> saleProducts = new ArrayList<>();
//        String sql = "select products.*,saleProducts.discount,saleProducts.start_date,"
//                + "saleProducts.end_date from saleProducts\n"
//                + "join products on products.id=saleProducts.product_id";
//
//        try {
//            ps = connection.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("model_name");
//                double price = rs.getDouble("price");
//                String image = rs.getString("image_url");
//                String storage = rs.getString("storage");
//                String color = rs.getString("color");
//                String quantity = rs.getString("stock_quantity");
//                String descrip = rs.getString("description");
//                String sellId = rs.getString("sellId");
//                Product saleProduct = new Product(id, name, price, image, storage, color, quantity, descrip, sellId);
//                saleProducts.add(saleProduct);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();  // Log the error for debugging
//        }
//        return saleProducts;
//    }
//public List<Bestseller> getAllBestsellers() {
//    List<Bestseller> bestsellerList = new ArrayList<>();
//    String sql = "SELECT b.id, p.model, b.quantity_sold, b.total_revenue " +
//                 "FROM bestsellers b JOIN products p ON b.product_id = p.id " +
//                 "ORDER BY b.total_revenue DESC";
//
//    try (PreparedStatement ps = connection.prepareStatement(sql);
//         ResultSet rs = ps.executeQuery()) {
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            String productName = rs.getString("model");
//            int quantitySold = rs.getInt("quantity_sold");
//            double totalRevenue = rs.getDouble("total_revenue");
//
//            Bestseller bestseller = new Bestseller(id, productName, quantitySold, totalRevenue);
//            bestsellerList.add(bestseller);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return bestsellerList;
//}
//    public List<Product> searchByName(String name) {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Product> searchs = new ArrayList<>();
//        String sql = "SELECT * FROM products WHERE model LIKE ?";  // Corrected SQL query
//
//        try {
//            ps = connection.prepareStatement(sql);
//            ps.setString(1, "%" + name + "%");  // Set the parameter for the model name
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                double price = rs.getDouble("price");
//                String modelName = rs.getString("model");  // Correctly retrieve model_name from the database
//                String image = rs.getString("image_url");
//                int storage = rs.getInt("storage");
//                String color = rs.getString("color");
//                String quantity = rs.getString("stock");
//                String descrip = rs.getString("description");
//                int sellId = rs.getInt("sellId");
//
//                Product search = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);
//                searchs.add(search);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();  // Log the error for debugging
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();  // Log closing errors
//            }
//        }
//        return searchs;
//    }
//    public List<Product> searchByColor(String color) {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Product> searchs1 = new ArrayList<>();
//        String sql = "SELECT * FROM products WHERE color LIKE ?";  // Corrected SQL query
//
//        try {
//            ps = connection.prepareStatement(sql);
//            ps.setString(1, "%" + color + "%");  // Set the parameter for the model name
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                double price = rs.getDouble("price");
//                String modelName = rs.getString("model");  // Correctly retrieve model_name from the database
//                String image = rs.getString("image_url");
//                int storage = rs.getInt("storage");
//                String quantity = rs.getString("stock");
//                String descrip = rs.getString("description");
//                int sellId = rs.getInt("sellId");
//
//                Product search = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);
//                searchs1.add(search);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();  // Log the error for debugging
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();  // Log closing errors
//            }
//        }
//        return searchs1;
//    }
//
//    public List<Product> searchByStorage(String Storage) {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Product> searchs2 = new ArrayList<>();
//        String sql = "SELECT * FROM products WHERE storage=?";  // Corrected SQL query
//
//        try {
//            ps = connection.prepareStatement(sql);
//            ps.setString(1, Storage);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                double price = rs.getDouble("price");
//                String modelName = rs.getString("model");
//                String image = rs.getString("image_url");
//                int storage = rs.getInt("storage");
//                String color = rs.getString("color");
//                String quantity = rs.getString("stock");
//                String descrip = rs.getString("description");
//                int sellId = rs.getInt("sellId");
//
//                Product search = new Product(id, modelName, price, image, storage, color, quantity, descrip, sellId);
//                searchs2.add(search);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();  // Log the error for debugging
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();  // Log closing errors
//            }
//        }
//        return searchs2;
//    }
