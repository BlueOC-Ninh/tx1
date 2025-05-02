/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tx1.config;

/**
 *
 * @author ninhdo
 */
public class DatabaseConfig {
     // Thông tin kết nối đến cơ sở dữ liệu
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/tx1?useSSL=false&serverTimezone=UTC";
    public static final String JDBC_USER = "ducninh";
    public static final String JDBC_PASSWORD = "N_112233n";
    
    // Thông số cho pool kết nối
    public static final int INITIAL_POOL_SIZE = 5;
    public static final int MAX_POOL_SIZE = 20;
    public static final long CONNECTION_TIMEOUT = 10000; 
}
