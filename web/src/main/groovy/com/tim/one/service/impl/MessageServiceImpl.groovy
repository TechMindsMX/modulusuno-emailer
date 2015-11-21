package com.tim.one.service.impl

import javax.jms.JMSException
import javax.jms.Message
import javax.jms.ObjectMessage
import javax.jms.Session
import javax.jms.Destination

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator
import org.springframework.stereotype.Service

import com.tim.one.service.MessageService
import com.tim.one.bean.EmailBean

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Service
class MessageServiceImpl implements MessageService {

  @Autowired
  JmsTemplate template
  @Autowired
  Destination destination

  Log log = LogFactory.getLog(getClass())

  void message(final EmailBean bean) {
    log.info "CALLING Message"

    template.send(destination, new MessageCreator() {
      Message createMessage(Session session) throws JMSException {
        ObjectMessage message = session.createObjectMessage()
        message.setObject(bean)
        return message
      }
    })
  }
}


