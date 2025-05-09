/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tx1.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tx1.model.Message;
import tx1.config.ConnectionPool;
import tx1.model.MessageStatistics;
/**
 *
 * @author ninhdo
 */


public class MessageDAO {
    
    private static final String SELECT_ALL = "SELECT * FROM message ORDER BY send_date DESC";
    private static final String INSERT = "INSERT INTO message (content, send_date, user_id, room_id, message_type_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE message SET content = ?, send_date = ?, user_id = ?, room_id = ?, message_type_id = ? WHERE id = ?";

    private static final String DELETE = "DELETE FROM message WHERE id = ?";
    private static final String SEARCH = "SELECT * FROM message WHERE content LIKE ? ORDER BY send_date DESC";
    
    private static final String COUNT_MSG = "SELECT COUNT(*) FROM message WHERE send_date BETWEEN ? AND ?";
    private static final String TOP_USER = "SELECT user_id FROM message WHERE send_date BETWEEN ? AND ? GROUP BY user_id ORDER BY COUNT(*) DESC LIMIT 1";
    private static final String TOP_ROOM = "SELECT room_id FROM message WHERE send_date BETWEEN ? AND ? GROUP BY room_id ORDER BY COUNT(*) DESC LIMIT 1";
    
    public List<Message> layTatCa() {
        List<Message> messages = new ArrayList<>();
        Connection connection = null;
        
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                messages.add(extractMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách tin nhắn", e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        
        return messages;
    }
    
    public Message them(Message message) {
        Connection connection = null;
        
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, message.getContent());
            statement.setTimestamp(2, new Timestamp(message.getSendDate().getTime()));
            statement.setInt(3, message.getUserId());
            statement.setInt(4, message.getRoomId());
            statement.setInt(5, message.getMessageTypeId());

            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Thêm tin nhắn thất bại, không có dòng nào được thêm.");
            }
            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                message.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Thêm tin nhắn thất bại, không lấy được ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm tin nhắn", e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        
        return message;
    }
    
    public boolean capNhat(Message message) {
        Connection connection = null;
        
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, message.getContent());
            statement.setTimestamp(2, new Timestamp(message.getSendDate().getTime()));
            statement.setInt(3, message.getUserId());
            statement.setInt(4, message.getRoomId());
            statement.setInt(5, message.getMessageTypeId());
            statement.setInt(6, message.getId());

            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật tin nhắn với ID " + message.getId(), e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }
    
    public boolean xoa(int id) {
        Connection connection = null;
        
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa tin nhắn với ID " + id, e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
    }
    
    public List<Message> timKiem(String keyword) {
        List<Message> messages = new ArrayList<>();
        Connection connection = null;
        
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SEARCH);
            statement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                messages.add(extractMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm kiếm tin nhắn có chứa '" + keyword + "'", e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().releaseConnection(connection);
            }
        }
        
        return messages;
    }
    
    private Message extractMessageFromResultSet(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getInt("id"));
        message.setContent(resultSet.getString("content"));
        message.setSendDate(new Date(resultSet.getTimestamp("send_date").getTime()));
        message.setUserId(resultSet.getInt("user_id"));
        message.setRoomId(resultSet.getInt("room_id"));
        message.setMessageTypeId(resultSet.getInt("message_type_id"));

        return message;
    }
    

    
    
    public MessageStatistics thongKe(Date from, Date to) {
        MessageStatistics result = new MessageStatistics();

        try (Connection conn = ConnectionPool.getInstance().getConnection()) {

            try (PreparedStatement stmt = conn.prepareStatement(COUNT_MSG)) {
                stmt.setTimestamp(1, new Timestamp(from.getTime()));
                stmt.setTimestamp(2, new Timestamp(to.getTime()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    result.setTotalMessages(rs.getInt(1));
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(TOP_USER)) {
                stmt.setTimestamp(1, new Timestamp(from.getTime()));
                stmt.setTimestamp(2, new Timestamp(to.getTime()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    result.setTopSenderUserId(rs.getInt(1));
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(TOP_ROOM)) {
                stmt.setTimestamp(1, new Timestamp(from.getTime()));
                stmt.setTimestamp(2, new Timestamp(to.getTime()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    result.setTopRoomId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thống kê tin nhắn", e);
        }

        return result;
    }
    
}