package com.tim.one.controller

import static org.springframework.web.bind.annotation.RequestMethod.POST

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.http.HttpStatus
import com.google.gson.Gson

import com.tim.one.command.NewUserCommand
import com.tim.one.bean.mail.NewUserBean
import com.tim.one.validator.CommandValidator
import com.tim.one.integration.MessageService
import com.tim.one.bean.MessageType

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Controller
@RequestMapping("/modulusuno/emailer/*")
class IntegradoraController {

  @Autowired
	CommandValidator validator
  @Autowired
	MessageService messageDispatcher

	Log log = LogFactory.getLog(getClass())

  @RequestMapping(method = POST, value = "/newUser")
	@ResponseBody
	public ResponseEntity<String> newUser(@RequestBody String json){
		NewUserCommand command = new Gson().fromJson(json, NewUserCommand.class)
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
		}

    NewUserBean bean = new NewUserBean()
    bean.setEmail('joseluis.delacruz@gmail.com')
    bean.setName(command.getName())
    bean.setType(MessageType.NEW_USER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

}
