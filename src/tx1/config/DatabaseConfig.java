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
    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3309/tx1?useSSL=false&serverTimezone=UTC";
    public static final String JDBC_USER = "myuser";
    public static final String JDBC_PASSWORD = "mypass123";

    
    public static final int INITIAL_POOL_SIZE = 5;
    public static final int MAX_POOL_SIZE = 20;
    public static final long CONNECTION_TIMEOUT = 10000; 
}
