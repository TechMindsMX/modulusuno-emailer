package com.tim.one.integration

interface MailService {
  void sendMailWithTemplate(Map values, Map model, String template)
}
