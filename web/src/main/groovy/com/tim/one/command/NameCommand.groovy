package com.tim.one.command

import org.hibernate.validator.constraints.SafeHtml
import org.hibernate.validator.constraints.Email
import com.tim.one.enums.LeadType

class NameCommand implements Command {

  @SafeHtml
	String name
  @SafeHtml
	String company
  @Email
	String email

  LeadType type

}
