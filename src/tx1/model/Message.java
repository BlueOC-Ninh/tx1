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
    private int messageTypeId; 

    public Message() {
    }

    public Message(int id, String content, Date sendDate, int userId, int roomId, int messageTypeId) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.userId = userId;
        this.roomId = roomId;
        this.messageTypeId = messageTypeId;
    }

    public Message(String content, Date sendDate, int userId, int roomId, int messageTypeId) {
        this.content = content;
        this.sendDate = sendDate;
        this.userId = userId;
        this.roomId = roomId;
        this.messageTypeId = messageTypeId;
    }

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

    public int getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(int messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", messageTypeId=" + messageTypeId +
                '}';
    }
}