/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tx1.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author ninhdo
 */
public class ConnectionPool {
     private static ConnectionPool instance;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    
    private ConnectionPool() {
        try {
            // Tải driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Khởi tạo pool với các kết nối
            connectionPool = new ArrayList<>(DatabaseConfig.INITIAL_POOL_SIZE);
            for (int i = 0; i < DatabaseConfig.INITIAL_POOL_SIZE; i++) {
                connectionPool.add(createConnection());
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Lỗi khi khởi tạo pool kết nối", e);
        }
    }
    
    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }
    
    public Connection getConnection() {
        lock.lock();
        try {
            if (connectionPool.isEmpty()) {
                if (usedConnections.size() < DatabaseConfig.MAX_POOL_SIZE) {
                    connectionPool.add(createConnection());
                } else {
                    throw new RuntimeException("Pool kết nối đã đạt kích thước tối đa");
                }
            }
            
            Connection connection = connectionPool.remove(connectionPool.size() - 1);
            
            // Kiểm tra xem kết nối có bị đóng hoặc không hợp lệ
            if (connection.isClosed() || !connection.isValid(1)) {
                connection = createConnection();
            }
            
            usedConnections.add(connection);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy kết nối", e);
        } finally {
            lock.unlock();
        }
    }
    
    public boolean releaseConnection(Connection connection) {
        lock.lock();
        try {
            usedConnections.remove(connection);
            return connectionPool.add(connection);
        } finally {
            lock.unlock();
        }
    }
    
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.JDBC_URL,
                DatabaseConfig.JDBC_USER,
                DatabaseConfig.JDBC_PASSWORD
        );
    }
    
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
    
    public int getAvailableConnections() {
        return connectionPool.size();
    }
    
    public int getUsedConnections() {
        return usedConnections.size();
    }
    
    public void shutdown() {
        lock.lock();
        try {
            // Đóng tất cả các kết nối đang được sử dụng
            for (Connection connection : usedConnections) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng kết nối đang sử dụng: " + e.getMessage());
                }
            }
            usedConnections.clear();
            
            // Đóng tất cả các kết nối có sẵn
            for (Connection connection : connectionPool) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng kết nối pool: " + e.getMessage());
                }
            }
            connectionPool.clear();
        } finally {
            lock.unlock();
        }
    }
}
