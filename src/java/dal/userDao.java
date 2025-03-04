package dal;

import mol.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.eclipse.jdt.internal.compiler.ast.Statement;

/**
 * DAO for User operations
 */
public class userDao extends DBContext {

    // Kiểm tra đăng nhập
    public User checkLogin(String username, String password) {
        String query = "SELECT * FROM [User] WHERE UserName = ? AND Password = CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', ?), 2)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password); // Mật khẩu được băm trong SQL
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("userId");
                    int isSell = rs.getInt("isSell");
                    int isAdmin = rs.getInt("isAdmin");
                    String email = rs.getString("Email");
                    String phone = rs.getString("Phone");
                    String address = rs.getString("Address");
                    String birthdate = rs.getString("BirthDate");
                    String gender = rs.getString("Gender");
                    String avatar = rs.getString("Avatar");

                    return new User(id, username, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra sự tồn tại của username
    public User checkUser(String username) {
        String query = "SELECT * FROM [User] WHERE UserName = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("userId");
                    String password = rs.getString("Password");
                    int isSell = rs.getInt("isSell");
                    int isAdmin = rs.getInt("isAdmin");
                    String email = rs.getString("Email");
                    String phone = rs.getString("Phone");
                    String address = rs.getString("Address");
                    String birthdate = rs.getString("BirthDate");
                    String gender = rs.getString("Gender");
                    String avatar = rs.getString("Avatar");

                    return new User(id, username, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User checkUser2(String username, String email, String phone) {
        String query = "SELECT * FROM [User] WHERE UserName = ? AND Email = ? AND Phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, phone);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("userId");
                    String password = rs.getString("Password");
                    int isSell = rs.getInt("isSell");
                    int isAdmin = rs.getInt("isAdmin");
                    String address = rs.getString("Address");
                    String birthdate = rs.getString("BirthDate");
                    String gender = rs.getString("Gender");
                    String avatar = rs.getString("Avatar");

                    return new User(id, username, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đăng ký người dùng mới
    public User signUp(String username, String password, String email, String phone, String address, String gender, String birthdate, String avatar) {
        String query = "INSERT INTO [User] (UserName, Password, isSell, isAdmin,Email,Phone,Address,Gender,BirthDate,Avatar) "
                + "VALUES (?, CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', ?), 2), 1, 0,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, gender);
            ps.setString(7, birthdate);
            ps.setString(8, "https://iconape.com/wp-content/files/im/10836/svg/iconfinder_3_avatar_2754579.svg");
            ps.executeUpdate();

            System.out.println("User registered successfully!");

            // Retrieve the generated ID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1); // Assuming `id` is an integer and is the first column
                    return new User(id, username, password, 0, 0, email, phone, address, gender, birthdate, avatar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if registration fails
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM [User]";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("userId");
                String username = rs.getString("UserName");
                String password = rs.getString("Password");
                int isSell = rs.getInt("isSell");
                int isAdmin = rs.getInt("isAdmin");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String birthdate = rs.getString("BirthDate");
                String gender = rs.getString("Gender");
                String avatar = rs.getString("Avatar");

                users.add(new User(id, username, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByID(int id) {
        User product = null; // Declare the product outside the loop
        String sql = "SELECT * FROM [User] WHERE userId=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) { // Use try-with-resources
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Check if there is a result
                    String name = rs.getString("UserName");
                    String password = rs.getString("Password");
                    int isSell = rs.getInt("isSell");
                    int isAdmin = rs.getInt("isAdmin");
                    String email = rs.getString("Email");
                    String phone = rs.getString("Phone");
                    String address = rs.getString("Address");
                    String birthdate = rs.getString("BirthDate");
                    String gender = rs.getString("Gender");
                    String avatar = rs.getString("Avatar");
                    User user = new User(id, name, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar);
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
        }
        return null;

    }

    public User getUserByName(String name) {
        User product = null; // Declare the product outside the loop
        String sql = "SELECT * FROM [User] WHERE UserName=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) { // Use try-with-resources
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Check if there is a result
                    int id = rs.getInt("userId");
                    String password = rs.getString("Password");
                    int isSell = rs.getInt("isSell");
                    int isAdmin = rs.getInt("isAdmin");
                    String email = rs.getString("Email");
                    String phone = rs.getString("Phone");
                    String address = rs.getString("Address");
                    String birthdate = rs.getString("BirthDate");
                    String gender = rs.getString("Gender");
                    String avatar = rs.getString("Avatar");

                    User user = new User(id, name, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar);
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
        }
        return null;

    }

    public User updateUser(int id, String name, String password, int isSell, int isAdmin, String email, String phone, String address, String gender, String birthdate, String avatar) {
        String sql = "UPDATE [User] SET "
                + "UserName = ?, "
                + "PassWord = ?, "
                + "isSell = ?, "
                + "isAdmin = ?, "
                + "Email = ?, "
                + "Phone = ?, "
                + "Address = ?, "
                + "Gender = ?, "
                + "BirthDate = ?, "
                + "Avatar = ? "
                + "WHERE userId = ?"; // Đã sửa lỗi dư dấu phẩy

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setInt(3, isSell);
            ps.setInt(4, isAdmin);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, address);
            ps.setString(8, gender);
            ps.setString(9, birthdate);
            ps.setString(10, avatar);
            ps.setInt(11, id); // Chuyển vị trí của id xuống cuối cùng để phù hợp với truy vấn

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                // Trả về thông tin user đã được cập nhật nếu thành công
                return new User(id, name, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Nên dùng một framework logging như SLF4J hoặc Log4J thay vì in ra console
        }
        return null; // Trả về null nếu cập nhật không thành công
    }

    public User updateProfile(int id, String name, String password, int isSell, int isAdmin, String email, String phone, String address, String gender, String birthdate, String avatar) {
        String sql = "UPDATE [User] SET "
                + "UserName = ?, "
                + "PassWord = ?, "
                + "isSell = ?, "
                + "isAdmin = ?, "
                + "Email = ?, "
                + "Phone = ?, "
                + "Address = ?, "
                + "Gender = ?, "
                + "BirthDate = ?, "
                + "Avatar = ? "
                + "WHERE userId = ?"; // Đã sửa lỗi dư dấu phẩy

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setInt(3, isSell);
            ps.setInt(4, isAdmin);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, address);
            ps.setString(8, gender);
            ps.setString(9, birthdate);
            ps.setString(10, avatar);
            ps.setInt(11, id); // Chuyển vị trí của id xuống cuối cùng để phù hợp với truy vấn

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                // Trả về thông tin user đã được cập nhật nếu thành công
                return new User(id, name, password, isSell, isAdmin, email, phone, address, gender, birthdate, avatar);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Nên dùng một framework logging như SLF4J hoặc Log4J thay vì in ra console
        }
        return null; // Trả về null nếu cập nhật không thành công
    }

    public void deleteUsers(int id) {
        PreparedStatement ps;
        String sql = "DELETE FROM [User] WHERE userId=?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id); // Set the id parameter
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public void AddUser(String username, String password, int isSell, int isAdmin) {
//        String query = "INSERT INTO [User] (UserName, Password, isSell, isAdmin) "
//                + "VALUES (?, CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', ?), 2), ?, ?)";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ps.setInt(3, isSell);
//            ps.setInt(4, isAdmin);
//            ps.executeUpdate();
//            System.out.println("User add successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public User AddUser(String username, String password, int isSell, int isAdmin, String email, String phone, String address, String gender, String birthdate, String avatar) {
        String query = "INSERT INTO [User] (UserName, Password, isSell, isAdmin,Email,Phone,Address,Gender,BirthDate,Avatar) "
                + "VALUES (?, CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', ?), 2), 1, 0,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, isSell);
            ps.setInt(4, isAdmin);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, gender);
            ps.setString(7, birthdate);
            ps.setString(7, "https://iconape.com/wp-content/files/im/10836/svg/iconfinder_3_avatar_2754579.svg");
            ps.executeUpdate();

            System.out.println("User registered successfully!");

            // Retrieve the generated ID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1); // Assuming `id` is an integer and is the first column
                    return new User(id, username, password, 0, 0, email, phone, address, gender, birthdate, avatar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if registration fails
    }

    public boolean updatePassword(String userName, String oldPassword, String newPassword) {
        String sql = "UPDATE [projectPrj301].[dbo].[User] "
                + "SET [Password] = CONVERT(VARCHAR(255), HASHBYTES('SHA2_256', ?), 2) "
                + "WHERE [UserName] = ? AND [Password] = CONVERT(VARCHAR(255), HASHBYTES('SHA2_256', ?), 2)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, userName);
            ps.setString(3, oldPassword);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            // Log lỗi
            e.printStackTrace();
            return false;
        }
    }
     public boolean NewPassWordByForgot(String userName, String newPassword) {
        String sql = "UPDATE [projectPrj301].[dbo].[User] "
                + "SET [Password] = CONVERT(VARCHAR(255), HASHBYTES('SHA2_256', ?), 2) "
                + "WHERE [UserName] = ? ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, userName);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            // Log lỗi
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        userDao dao = new userDao();
            boolean isUpdated = dao.NewPassWordByForgot("namx", "Lethenam6824@@");
//        User u = dao.getUserByID(1098);
//        System.out.println(u);
//        User u = dao.getUserByName("namm");
        //        dao.AddUser("lethe", "123", 0, 0);
        //        dao.deleteUsers(27);
        //User updatedUser = dao.updateUser(1093, "Lrwehdrtwe", "fdjhfgjgh", 1, 0, "lethedat112fjhgfh2000@gmail.com", "0867381554", "thanh hoa", "Male", "83478376", "fsdhdht"); // Example values
        //
        //    if (updatedUser != null) {
        //        System.out.println("User updated: " + updatedUser);
        //    } else {
        //        System.out.println("User update failed.");
        //    
        //}
        // Kiểm tra danh sách người dùng
        //        List<User> list = dao.getAllUsers();
        //        for (User user : list) {
        //            System.out.println(user);
        //        }
        // Đăng ký người dùng mới (ví dụ)
        // dao.signUp("leth", "Lethenam6824@", "ywertyew", "0382463735", "Yhabs", "Female", "2020-19-12","uytdyust");
        // Kiểm tra đăng nhập (ví dụ)
//        User user = dao.checkUser2("nams", "lethenam6824@gmail.com", "0123456789");
//        System.out.println(user);
    }
}
