package com.tim.one.messengine

import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.ObjectMessage

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.tim.one.integration.MailService
import com.tim.one.bean.EmailBean
import com.tim.one.service.NotificationService

@Service
class JmsMessageListener implements MessageListener {

  @Autowired
  NotificationService notificationService

  Log log = LogFactory.getLog(getClass())

  def void onMessage(Message message) {
    log.info 'Email message received'
    Object messageBean = ((ObjectMessage) message).getObject()
    notificationService.sendNotification(messageBean)
  }

}
