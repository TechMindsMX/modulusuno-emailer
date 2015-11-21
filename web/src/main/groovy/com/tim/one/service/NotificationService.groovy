package com.tim.one.service

import com.tim.one.bean.EmailBean

interface NotificationService {
  void sendNotification(EmailBean bean)
}

