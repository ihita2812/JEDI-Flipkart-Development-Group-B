package com.flipflit.client;

import com.flipfit.business.*;

public class notificationClient {
    public static void main(String[] args) {
        NotificationBusiness notification = new NotificationBusiness();
        notification.sendNotification(null);
        notification.deleteNotification(null);
    }
}