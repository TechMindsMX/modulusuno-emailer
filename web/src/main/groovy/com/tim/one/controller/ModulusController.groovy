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
import com.tim.one.command.ProcessorPayrollCommand
import com.tim.one.bean.ProcessorPayrollBean

/**
 * @author josdem
 * @understands A class who knows how to send emails using Json
 *
 */

@Api(description = "Know how manage modulusweb user request to send mails")
@Controller
@RequestMapping("/services/modulus/*")
class ModulusController {

	@Autowired
	MessageService messageDispatcher
	@Autowired
	CommandValidator validator

	Log log = LogFactory.getLog(getClass())

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "account", value = "Account", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "stpAccount", value = "STP Account", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/createAccount")
	@ResponseBody
	ResponseEntity<String> createAccount(@RequestBody AccountCommand command){
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

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "token", value = "Token", required = true, dataType = "string", paramType = "query")
  ])
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
    bean.setType(MessageType.REGISTER_MODULUS)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "company", value = "Company", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "type", value = "Lead Type", required = true, dataType = "string", paramType = "query")
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

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "token", value = "Token", required = true, dataType = "string", paramType = "query")
  ])
  @RequestMapping(method = POST, value = "/forgot")
  @ResponseBody
  ResponseEntity<String> forgot(@RequestBody ForgotPasswordCommand command){
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
    }

    ForgotPasswordBean bean = new ForgotPasswordBean()
    bean.setToken(command.getToken())
    bean.setEmail(command.getEmail())
    bean.setType(MessageType.FORGOT_PASSWORD_MODULUS)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "account", value = "Account", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "message", value = "Message", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "bank", value = "Bank", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "accountBank", value = "Bank Account", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "url", value = "Url", required = true, dataType = "string", paramType = "query")
  ])
  @RequestMapping(method = POST, value = "/depositOrder")
  @ResponseBody
  ResponseEntity<String> depositOrderByCompany(@RequestBody DepositOrderCommand command){
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
    bean.setType(MessageType.DEPOSIT_ORDER_MODULUS)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)

  }

  @ApiImplicitParams([
    @ApiImplicitParam(name = "emailResponse", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "message", value = "Message", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "url", value = "Url", required = true, dataType = "string", paramType = "query")
  ])
  @RequestMapping(method = POST, value = "/processorPayrolls")
  @ResponseBody
  ResponseEntity<String> processorPayrolls(@RequestBody ProcessorPayrollCommand command){
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
    }

    ProcessorPayrollBean bean = new ProcessorPayrollBean()
    bean.setEmail(command.emailResponse)
    bean.setMessage(command.message)
    bean.setUrl(command.url)
    bean.setType(MessageType.PROCESSOR_PAYROLL)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @ApiImplicitParams([
    @ApiImplicitParam(name = "emailResponse", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "nameCompany", value = "Company", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "message", value = "Message", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "url", value = "Url", required = true, dataType = "string", paramType = "query")
  ])
  @RequestMapping(method = POST, value = "/companyIntegrated")
  @ResponseBody
  ResponseEntity<String> companyAssignedBuyer(@RequestBody CompanyIntegratedCommand command){
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
    }

    CompanyIntegratedBean bean = new CompanyIntegratedBean()
    bean.setEmail(command.emailResponse)
    bean.setName(command.nameCompany)
    bean.setMessage(command.message)
    bean.setUrl(command.url)
    bean.setType(MessageType.COMPANY_INTEGRATED_MODULUS)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "company", value = "Company", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "type", value = "Lead Type", required = true, dataType = "string", paramType = "query")
  ])
  @RequestMapping(method = POST, value = "/clientProvider")
  @ResponseBody
  ResponseEntity<String> clientProvider(@RequestBody NameCommand command){
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
    }
    log.info MessageType."${command.type}"
    NewUserBean bean = new NewUserBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setCompany(command.getCompany())
    bean.setType(MessageType."${command.type}")
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "rfc", value = "RFC", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "url", value = "Url", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "type", value = "Lead Type", required = true, dataType = "string", paramType = "query")
  ])
  @RequestMapping(method = POST, value = "/authorizeSaleOrder")
  @ResponseBody
  ResponseEntity<String> authorizeSaleOrder(@RequestBody SaleOrderCommand command){
    log.info "Sending email: ${command.dump()}"

    if(!validator.isValid(command)){
      return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST);
    }

    SaleOrderBean bean = new SaleOrderBean()
    bean.setEmail(command.email)
    bean.setName(command.name)
    bean.setRfc(command.rfc)
    bean.setUrl(command.url)
    bean.setType(MessageType.SALE_ORDER_MODULUS)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
  }

}
