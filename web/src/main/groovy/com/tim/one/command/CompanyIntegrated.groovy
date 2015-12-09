package com.tim.one.command

import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml
import org.hibernate.validator.constraints.URL

class CompanyIntegratedCommand implements Command {

	@Email
	@NotNull
	String emailResponse

  @SafeHtml
  String nameCompany

	@SafeHtml
	String message

  @URL
  String url

}
