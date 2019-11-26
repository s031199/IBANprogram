

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    public static Connection init() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://createyourweb.lt:3306/u838202579_123456789";
        String username = "u838202579_1234";
        String password = "123456789";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
            //return DriverManager.getConnection(host, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
