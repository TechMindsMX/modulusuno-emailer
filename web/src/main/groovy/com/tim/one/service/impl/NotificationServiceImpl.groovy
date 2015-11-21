package com.tim.one.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

import com.tim.one.integration.MailService
import com.tim.one.service.NotificationService
import com.tim.one.bean.EmailBean
import com.tim.one.constant.ApplicationConstants

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Service
class NotificationServiceImpl implements NotificationService {

  @Autowired
  MailService mailService
  @Autowired
  Properties properties

  private Log log = LogFactory.getLog(getClass())

  @Override
  void sendNotification(EmailBean bean) {
    def (sender, subject, templateName) = obtainSubjectAndResourceToSendNotification(bean)
      def data = [email:bean.email, sender:sender, subject:subject, templateName:templateName]
      mailService.sendMailWithTemplate(data, bean.properties, data.templateName)
  }

  def obtainSubjectAndResourceToSendNotification(EmailBean bean){
    String templateKey = "${bean.type.toString()}_PATH"
    String subjectKey = "${bean.type.toString()}_SUBJECT"

    String templateName = properties.getProperty(templateKey)
    String sender = properties.getProperty(ApplicationConstants.SENDER)
    String subject = properties.getProperty(subjectKey)

    log.info("Sending email with subject: " + subject)
    [sender, subject, templateName]
  }

}

