package com.makingdevs.integration.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tim.one.integration.MailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mail-context.xml" })
@Ignore
public class TestMailService {

  @Autowired
  private MailService mailService;

  @Test
  public void testMail5() throws MessagingException {
    Map values = new HashMap();
    Map model = new HashMap();
    mailService.sendMailWithTemplate(values, model, "template.ftl");
  }
}
