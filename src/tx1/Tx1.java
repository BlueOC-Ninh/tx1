/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tx1;

import tx1.config.ConnectionPool;
import tx1.dao.MessageDAO;
import tx1.model.Message;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author ninhdo
 */
public class Tx1 {

    /**
     * @param args the command line arguments
     */
 
     public static void main(String[] args) {
         try {
            // ConnectionPool sẽ tự động khởi tạo khi getInstance() được gọi lần đầu tiên
            System.out.println("Khởi tạo Connection Pool...");
            
            // Hiển thị thông tin về pool
            ConnectionPool pool = ConnectionPool.getInstance();
            System.out.println("Tổng số kết nối: " + pool.getSize());
            System.out.println("Số kết nối khả dụng: " + pool.getAvailableConnections());
            
            
            // Khi kết thúc ứng dụng, đóng tất cả các kết nối
            System.out.println("Đóng Connection Pool...");
            pool.shutdown();
            
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
    
    
}
