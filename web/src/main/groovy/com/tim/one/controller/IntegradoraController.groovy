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

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Controller
@RequestMapping("/modulusuno/emailer/*")
class IntegradoraController {

  @Autowired
  CommandValidator validator
  @Autowired
  MessageService messageDispatcher
  @Autowired
  Properties properties

  Log log = LogFactory.getLog(getClass())

  @RequestMapping(method = POST, value = "/newUser")
  @ResponseBody
  public ResponseEntity<String> newUser(@RequestBody String json){
    NameCommand command = new Gson().fromJson(json, NameCommand.class)
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
    }

    NewUserBean bean = new NewUserBean()
    bean.setEmail(properties.getProperty(ApplicationConstants.INTEGRADORA_ADMIN));
    bean.setName(command.getName())
    bean.setType(MessageType."${command.type}")
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
    bean.setType(MessageType.REGISTER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @RequestMapping(method = POST, value = "/forgot")
  @ResponseBody
  public ResponseEntity<String> forgot(@RequestBody String json){
    ForgotPasswordCommand command = new Gson().fromJson(json, ForgotPasswordCommand.class)
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

  @RequestMapping(method = POST, value = "/depositOrder")
  @ResponseBody
  public ResponseEntity<String> depositOrderByCompany(@RequestBody String json){
    DepositOrderCommand command = new Gson().fromJson(json, DepositOrderCommand.class)
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
    }

    DepositOrderBean bean = new DepositOrderBean()
    bean.setEmail(command.email)
    bean.setName(command.name)
    bean.setAccount(command.account)
    bean.setMessage(command.message)
    bean.setBank(command.bank)
    bean.setAccountBank(command.accountBank)
    bean.setUrl(command.url)
    bean.setType(MessageType.DEPOSIT_ORDER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)

  }

  @RequestMapping(method = POST, value = "/companyIntegrated")
  @ResponseBody
  public ResponseEntity<String> companyAssignedBuyer(@RequestBody String json){
    CompanyIntegratedCommand command = new Gson().fromJson(json, CompanyIntegratedCommand.class)
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
    }

    CompanyIntegratedBean bean = new CompanyIntegratedBean()
    bean.setEmail(command.emailResponse)
    bean.setName(command.nameCompany)
    bean.setMessage(command.message)
    bean.setUrl(command.url)
    bean.setType(MessageType.COMPANY_INTEGRATED)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @RequestMapping(method = POST, value = "/clientProvider")
  @ResponseBody
  public ResponseEntity<String> clientProvider(@RequestBody String json){
    NameCommand command = new Gson().fromJson(json, NameCommand.class)
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
    }

    NewUserBean bean = new NewUserBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setCompany(command.getCompany())
    bean.setType(MessageType."${command.type}")
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @RequestMapping(method = POST, value = "/authorizeSaleOrder")
  @ResponseBody
  public ResponseEntity<String> authorizeSaleOrder(@RequestBody String json){
    SaleOrderCommand command = new Gson().fromJson(json, SaleOrderCommand.class)
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
    }

    SaleOrderBean bean = new SaleOrderBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setRfc(command.getCompany())
    bean.setType(MessageType.SALE_ORDER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }
}
