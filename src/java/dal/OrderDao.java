package dal;

import mol.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mol.Product;

public class OrderDao extends DBContext {

    public Order createOrder(int userId, String name, String email, String phone, String address, double totalMoney, String payment) {
        String sql = "INSERT INTO [projectPrj301].[dbo].[orders] (user_id,total_money,  name, email, phone, address,  payment) VALUES (?, ?,?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDouble(2, totalMoney); 
           ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, payment);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        return new Order(orderId, userId, name, email, phone, address, totalMoney, new java.util.Date(), payment);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, user_id, name, email, phone, address, total_money, order_date, payment FROM orders";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                double totalMoney = rs.getDouble("total_money");
                Timestamp orderDate = rs.getTimestamp("order_date"); // Lấy giá trị kiểu Timestamp
                String payment = rs.getString("payment");

                // Tạo đối tượng Order và thêm vào danh sách
                orders.add(new Order(id, userId, name, email, phone, address, totalMoney, orderDate, payment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getProfileByName(String name) {
        String sql = "SELECT * FROM orders WHERE name = ?";
        List<Order> orders = new ArrayList<>();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setName(rs.getString("name"));
                    order.setEmail(rs.getString("email"));
                    order.setPhone(rs.getString("phone"));
                    order.setAddress(rs.getString("address"));
                    order.setTotalMoney(rs.getDouble("total_money"));
                    order.setDate(rs.getDate("order_date"));
                    order.setPayment(rs.getString("payment"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getOrderByUid(int uid) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setName(rs.getString("name"));
                    order.setEmail(rs.getString("email"));
                    order.setPhone(rs.getString("phone"));
                    order.setAddress(rs.getString("address"));
                    order.setTotalMoney(rs.getDouble("total_money"));
                    order.setDate(rs.getTimestamp("order_date"));
                    order.setPayment(rs.getString("payment"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrderByUidx(int uid) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");  // Sửa lỗi ở đây
                    double money = rs.getDouble("total_money");
                    Date date = rs.getDate("order_date");
                    String pay = rs.getString("payment");
                    return new Order(id, uid, name, email, phone, address, money, date, pay);

                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Order> getMoneyOfOrder() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT total_money FROM orders";

        try (PreparedStatement ps = connection.prepareStatement(sql)) { // Use try-with-resources
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Check if there is a result
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging
        }

        return orders; // Return null if no product found
    }

    public Order newOrder() {
        String sql = "SELECT TOP 1 * FROM orders ORDER BY id DESC;";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt("id");
                int uid = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");  // Sửa lỗi ở đây
                double money = rs.getDouble("total_money");
                Date date = rs.getDate("order_date");
                String pay = rs.getString("payment");

                // Trả về đối tượng Order với các giá trị đã lấy
                return new Order(id, uid, name, email, phone, address, money, date, pay);
            }

        } catch (Exception e) {
            e.printStackTrace();  // In lỗi nếu có
        }
        return null;
    }

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();
//        Order o = orderDao.getOrderByUidx(1061);
//            System.out.println(o);
//        
 //      Order o = orderDao.newOrder();
//        List<Order> o = orderDao.getMoneyOfOrder();
//       System.out.println(o);
       Order order = orderDao.createOrder(29, "Nam", "nam@example.com", "0867381554", "100 Street", 150.0,"cash");
    }

}