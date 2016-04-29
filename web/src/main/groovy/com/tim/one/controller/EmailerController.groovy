package com.tim.one.controller

import static org.springframework.web.bind.annotation.RequestMethod.POST

import java.util.Properties

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams

import com.google.gson.Gson
import com.tim.one.bean.ErrorCode
import com.tim.one.enums.MessageType
import com.tim.one.bean.MessageBean
import com.tim.one.command.MessageCommand
import com.tim.one.service.MessageService
import com.tim.one.validator.CommandValidator

/**
 * @author josdem
 * @understands A class who knows how to send emails using Json
 *
 */

@Api(description="knows how send message to email")
@Controller
@RequestMapping("/services/emailer/*")
public class EmailerController {

	@Autowired
	private MessageService messageDispatcher
	@Autowired
	private CommandValidator validator

	private Log log = LogFactory.getLog(getClass())

  @ApiImplicitParams([
      @ApiImplicitParam(name = "email", value = "Email which send message", required = true, dataType = "string", paramType = "query"),
      @ApiImplicitParam(name = "message", value = "Message to send", required = true, dataType = "string", paramType = "query")
        ])

  @RequestMapping(method = POST, value = "/message", consumes="application/json")
  @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	ResponseEntity<String> message(@RequestBody MessageCommand command){
		log.info "Sending contact email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

		MessageBean bean = new MessageBean()
    bean.setEmail(command.getEmail())
    bean.setMessage(command.getMessage())
    bean.setType(MessageType.MESSAGE)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

}
