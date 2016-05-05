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

import com.tim.one.bean.ErrorCode
import com.tim.one.enums.MessageType
import com.tim.one.bean.AssignationBean
import com.tim.one.bean.ForgotPasswordBean
import com.tim.one.bean.NewUserBean
import com.tim.one.command.AssignationCommand
import com.tim.one.command.ForgotPasswordCommand
import com.tim.one.command.NewUserCommand
import com.tim.one.command.RegisterCommand
import com.tim.one.service.MessageService
import com.tim.one.validator.CommandValidator
import com.tim.one.constant.ApplicationConstants

/**
 * @author josdem
 * @understands A class who knows how to send emails using Json
 *
 */

@Api(description = "Know how manage talentua user request to send emails")
@Controller
@RequestMapping("/services/email/*")
class TalentuaController {

	@Autowired
	private MessageService messageDispatcher
	@Autowired
	private CommandValidator validator
	@Autowired
	private Properties dynamic

	private Log log = LogFactory.getLog(getClass())

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
    bean.setToken(dynamic.getProperty(ApplicationConstats.FORGOT_PASSWORD_PREFIX) + command.getToken())
    bean.setEmail(command.getEmail())
    bean.setType(MessageType.FORGOT_PASSWORD)
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
    bean.setToken(dynamic.getProperty(ApplicationConstats.REGISTER_PREFIX) + command.getToken())
    bean.setEmail(command.getEmail())
    bean.setType(MessageType.REGISTER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/newUser")
	@ResponseBody
	ResponseEntity<String> newUser(@RequestBody NewUserCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    NewUserBean bean = new NewUserBean()
    bean.setEmail(dynamic.getProperty(ApplicationConstats.ADMIN_EMAIL))
    bean.setName(command.getName())
    bean.setType(MessageType.NEW_USER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/forgotUsername")
	@ResponseBody
	ResponseEntity<String> forgotUsername(@RequestBody NewUserCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    NewUserBean bean = new NewUserBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setType(MessageType.FORGOT_USERNAME)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/musicianAssignedFacilitator")
	@ResponseBody
	ResponseEntity<String> musicianAssignedFacilitator(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setType(MessageType.MUSICIAN_ASSIGNED_FACILITATOR)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/musicianAssignedUser")
	@ResponseBody
	ResponseEntity<String> musicianAssignedUser(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setEmailOptional(command.getEmailOptional())
    bean.setType(MessageType.MUSICIAN_ASSIGNED_USER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/musicianRefused")
	@ResponseBody
	ResponseEntity<String> musicianRefused(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setNote(command.getNote())
    bean.setType(MessageType.MUSICIAN_REFUSED)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/companyAssignedBuyer")
	@ResponseBody
	ResponseEntity<String> companyAssignedBuyer(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setType(MessageType.COMPANY_ASSIGNED_BUYER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/companyAssignedUser")
	@ResponseBody
	ResponseEntity<String> companyAssignedUser(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setEmailOptional(command.getEmailOptional())
    bean.setType(MessageType.COMPANY_ASSIGNED_USER)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/companyRefused")
	@ResponseBody
	ResponseEntity<String> companyRefused(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setNote(command.getNote())
    bean.setType(MessageType.COMPANY_REFUSED)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/companyEdited")
	@ResponseBody
	ResponseEntity<String> companyEdited(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setType(MessageType.COMPANY_EDITED)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}

  @ApiImplicitParams([
    @ApiImplicitParam(name = "name", value = "Name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "reference", value = "Reference", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "emailOptional", value = "Optional Email", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "note", value = "Note", required = true, dataType = "string", paramType = "query")
  ])
	@RequestMapping(method = POST, value = "/musicianEdited")
	@ResponseBody
	ResponseEntity<String> musicianEdited(@RequestBody AssignationCommand command){
		log.info "Sending email: ${command.dump()}"

		if(!validator.isValid(command)){
	    return new ResponseEntity<String>("Error: " + ErrorCode.VALIDATOR_ERROR.ordinal(), HttpStatus.BAD_REQUEST)
		}

    AssignationBean bean = new AssignationBean()
    bean.setEmail(command.getEmail())
    bean.setName(command.getName())
    bean.setReference(command.getReference())
    bean.setType(MessageType.MUSICIAN_EDITED)
    messageDispatcher.message(bean)
    return new ResponseEntity<String>("OK", HttpStatus.OK)
	}
}
