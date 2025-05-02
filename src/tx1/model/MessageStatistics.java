/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tx1.model;

/**
 *
 * @author ninhdo
 */
public class MessageStatistics {
    private int totalMessages;
    private int topSenderUserId;
    private int topRoomId;


    public int getTotalMessages() {
            return totalMessages;
        }

        public void setTotalMessages(int totalMessages) {
            this.totalMessages = totalMessages;
        }

        public int getTopSenderUserId() {
            return topSenderUserId;
        }

        public void setTopSenderUserId(int topSenderUserId) {
            this.topSenderUserId = topSenderUserId;
        }

        public int getTopRoomId() {
            return topRoomId;
        }

        public void setTopRoomId(int topRoomId) {
            this.topRoomId = topRoomId;
        }
}
