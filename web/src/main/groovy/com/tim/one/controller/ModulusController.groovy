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

import com.google.gson.Gson
import com.tim.one.bean.ErrorCode
import com.tim.one.bean.AccountBean
import com.tim.one.command.AccountCommand
import com.tim.one.command.NewUserCommand
import com.tim.one.command.NameCommand
import com.tim.one.bean.NewUserBean
import com.tim.one.command.RegisterCommand
import com.tim.one.bean.ForgotPasswordBean
import com.tim.one.command.ForgotPasswordCommand
import com.tim.one.bean.CompanyIntegratedBean
import com.tim.one.command.CompanyIntegratedCommand
import com.tim.one.validator.CommandValidator
import com.tim.one.service.MessageService
import com.tim.one.enums.MessageType
import com.tim.one.constant.ApplicationConstants
import com.tim.one.command.DepositOrderCommand
import com.tim.one.bean.DepositOrderBean
import com.tim.one.command.SaleOrderCommand
import com.tim.one.bean.SaleOrderBean

/**
 * @author josdem
 * @understands A class who knows how to send emails using Json
 *
 */

@Controller
@RequestMapping("/modulus/*")
class ModulusController {

	@Autowired
	MessageService messageDispatcher
	@Autowired
	CommandValidator validator

	Log log = LogFactory.getLog(getClass())

	@RequestMapping(method = POST, value = "/createAccount")
	@ResponseBody
	ResponseEntity<String> createAccount(@RequestBody String json){
		AccountCommand command = new Gson().fromJson(json, AccountCommand.class)
		log.info "Sending contact email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

		def bean = new AccountBean()
    bean.setEmail(command.email)
    bean.setName(command.name)
    bean.setAccount(command.account)
    bean.setStpAccount(command.stpAccount)
    bean.setType(MessageType.ACCOUNT)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

	@RequestMapping(method = POST, value = "/register")
  @ResponseBody
  public ResponseEntity<String> register(@RequestBody String json){
    RegisterCommand command = new Gson().fromJson(json, RegisterCommand.class)
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
    }

    ForgotPasswordBean bean = new ForgotPasswordBean()
    bean.setToken(command.getToken())
    bean.setEmail(command.getEmail())
    bean.setType(MessageType.REGISTER_MODULUS)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

}
