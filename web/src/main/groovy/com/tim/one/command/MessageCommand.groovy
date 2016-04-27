package com.tim.one.command

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value="MessageCommand", description="Message command model")
class MessageCommand implements Command {

	@Email
	@NotNull
  @ApiModelProperty(value = "Email")
	String email

	@SafeHtml
  @ApiModelProperty(value="Message")
	String message

}
