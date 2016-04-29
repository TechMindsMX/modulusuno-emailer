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
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiImplicitParam

import com.google.gson.Gson
import com.tim.one.bean.ErrorCode
import com.tim.one.enums.MessageType
import com.tim.one.bean.ContactBean
import com.tim.one.bean.ForgotPasswordBean
import com.tim.one.command.ContactCommand
import com.tim.one.command.ForgotPasswordCommand
import com.tim.one.service.MessageService
import com.tim.one.validator.CommandValidator
import com.tim.one.constant.ApplicationConstants

/**
 * @author josdem
 * @understands A class who knows how to send emails using Json
 *
 */

@Api(description = "Know how manage Valuarte Users requests to send mails")
@Controller
@RequestMapping("/services/valuarte/*")
class ValuarteController {

	@Autowired
	private MessageService messageDispatcher
	@Autowired
	private CommandValidator validator
	@Autowired
	private Properties properties

	private Log log = LogFactory.getLog(getClass())

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "phone", value = "Phone", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "subject", value = "Subject", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "message", value = "Message", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/contact")
	@ResponseBody
	ResponseEntity<String> contact(@RequestBody ContactCommand command){
		log.info "Sending contact email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

		ContactBean bean = new ContactBean()
    bean.setEmail(properties.getProperty(ApplicationConstants.VALUARTE_TARGET))
    bean.setName(command.getName())
    bean.setEmailOptional(command.getEmailOptional())
    bean.setPhone(command.getPhone())
    bean.setSubject(command.getSubject())
    bean.setMessage(command.getMessage())
    bean.setType(MessageType.CONTACT)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "token", value = "Token", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/forgotPassword")
	@ResponseBody
	ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    ForgotPasswordBean bean = new ForgotPasswordBean()
    bean.setToken(command.getToken())
    bean.setEmail(command.getEmail())
    bean.setType(MessageType.FORGOT_PASSWORD)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

}
