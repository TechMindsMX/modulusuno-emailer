package com.tim.one.command

import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml

class ProcessorPayrollCommand implements Command {

	@Email
	@NotNull
	String emailResponse

	@SafeHtml
	String message

  String url

}
