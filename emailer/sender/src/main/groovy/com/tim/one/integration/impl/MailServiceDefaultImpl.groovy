package com.tim.one.integration.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils

import com.tim.one.integration.MailService

import freemarker.template.Configuration
import freemarker.template.Template
import javax.mail.internet.MimeMessage

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Service
class MailServiceImpl implements MailService {

  @Autowired
  Configuration configuration
  @Autowired
  JavaMailSender javaMailSender

  Log log = LogFactory.getLog(getClass())

  void sendMailWithTemplate(final Map values, final Map model, final String template) {

    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true)
        Template myTemplate = configuration.getTemplate(template)
        message.setTo(values.email)
        message.setSubject(values.subject)
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(myTemplate, model)
        message.setText(text, true)
      }
    }

    this.javaMailSender.send(preparator)
  }
}


