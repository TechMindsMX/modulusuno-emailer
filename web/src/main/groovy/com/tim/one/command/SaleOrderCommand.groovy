package com.tim.one.command

import org.hibernate.validator.constraints.SafeHtml
import org.hibernate.validator.constraints.Email
import javax.validation.constraints.NotNull
import com.tim.one.enums.LeadType

class SaleOrderCommand implements Command {

  @SafeHtml
	@NotNull
	String name

  @SafeHtml
	@NotNull
	String rfc

  @Email
	@NotNull
	String email

  @URL
	@NotNull
  String url

  LeadType type
}
