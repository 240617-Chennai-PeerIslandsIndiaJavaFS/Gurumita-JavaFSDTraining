package org.example.Models;

import java.util.Date;

public class Messages {
        private int message_id;
        private int sender_id;
        private int recipient_id;
        private String message_content;
        private Date timestamp;

        public Messages(int message_id, int sender_id, int recipient_id, String message_content, Date timestamp) {
            this.message_id = message_id;
            this.sender_id = sender_id;
            this.recipient_id = recipient_id;
            this.message_content = message_content;
            this.timestamp = timestamp;
        }

        public Messages() {
        }

        public int getMessage_id() {
            return message_id;
        }

        public void setMessage_id(int message_id) {
            this.message_id = message_id;
        }

        public int getSender_id() {
            return sender_id;
        }

        public void setSender_id(int sender_id) {
            this.sender_id = sender_id;
        }

        public int getRecipient_id() {
            return recipient_id;
        }

        public void setRecipient_id(int recipient_id) {
            this.recipient_id = recipient_id;
        }

        public String getMessage_content() {
            return message_content;
        }

        public void setMessage_content(String message_content) {
            this.message_content = message_content;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
}
