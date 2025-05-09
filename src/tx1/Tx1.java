/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tx1;

import tx1.config.ConnectionPool;

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
            System.out.println("Khởi tạo Connection Pool...");
            
            ConnectionPool pool = ConnectionPool.getInstance();
            System.out.println("Tổng số kết nối: " + pool.getSize());
            System.out.println("Số kết nối khả dụng: " + pool.getAvailableConnections());
            
            
            System.out.println("Hiển thị giao diện...");
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new view().setVisible(true);
                }
            });
           
            
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
    
    
}
