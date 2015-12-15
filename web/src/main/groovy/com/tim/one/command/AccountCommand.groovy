package com.tim.one.command

import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.SafeHtml

class AccountCommand implements Command {

	@Email
	@NotNull
	String email

  @NotNull
  @SafeHtml
  String name

  @NotNull
	@SafeHtml
	String account

  @NotNull
	@SafeHtml
	String stpAccount

}
