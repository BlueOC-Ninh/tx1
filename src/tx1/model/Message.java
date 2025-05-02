/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tx1.model;
import java.util.Date;
/**
 *
 * @author ninhdo
 */
public class Message {
    private int id;
    private String content;
    private Date sendDate;
    private int userId;
    private int roomId;
    
    // Constructor mặc định
    public Message() {
    }
    
    // Constructor với tất cả tham số
    public Message(int id, String content, Date sendDate, int userId, int roomId) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.userId = userId;
        this.roomId = roomId;
    }
    
    // Constructor không có ID (dùng khi tạo message mới)
    public Message(String content, Date sendDate, int userId, int roomId) {
        this.content = content;
        this.sendDate = sendDate;
        this.userId = userId;
        this.roomId = roomId;
    }
    
    // Các phương thức getter và setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                ", userId=" + userId +
                ", roomId=" + roomId +
                '}';
    }
}
