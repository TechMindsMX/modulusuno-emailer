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
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams

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


import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Api(description="knows how manage integra user requests to send mails")
@Controller
@RequestMapping("/services/modulusuno/emailer/*")
class IntegradoraController {

  @Autowired
  CommandValidator validator
  @Autowired
  MessageService messageDispatcher
  @Autowired
  Properties properties

  Log log = LogFactory.getLog(getClass())

  @ApiImplicitParams([
        @ApiImplicitParam(name = "name", value = "User", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "company", value = "Company", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "type", value = "Lead Type", required = true, dataType = "string", paramType = "query", allowableValues = "NEW_USER")
  ])

  @RequestMapping(method = POST, value = "/newUser")
  @ResponseBody
  ResponseEntity<String> newUser(@RequestBody NameCommand command){
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
  ResponseEntity<String> register(@RequestBody RegisterCommand command){
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
  ResponseEntity<String> forgot(@RequestBody String json){
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
  ResponseEntity<String> depositOrderByCompany(@RequestBody String json){
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
  ResponseEntity<String> companyAssignedBuyer(@RequestBody String json){
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
  ResponseEntity<String> clientProvider(@RequestBody String json){
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
  ResponseEntity<String> authorizeSaleOrder(@RequestBody String json){
    SaleOrderCommand command = new Gson().fromJson(json, SaleOrderCommand.class)
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
    }

    SaleOrderBean bean = new SaleOrderBean()
    bean.setEmail(command.email)
    bean.setName(command.name)
    bean.setRfc(command.rfc)
    bean.setUrl(command.url)
    bean.setType(MessageType.SALE_ORDER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }
}
