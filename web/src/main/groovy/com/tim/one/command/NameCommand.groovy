package com.tim.one.command

import org.hibernate.validator.constraints.SafeHtml
import com.tim.one.enums.LeadType

class NameCommand implements Command {

  @SafeHtml
	String name
  @SafeHtml
	String company
  LeadType type

}
