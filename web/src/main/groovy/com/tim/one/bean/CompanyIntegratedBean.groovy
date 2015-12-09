package com.tim.one.bean

import com.tim.one.enums.MessageType

class CompanyIntegratedBean implements EmailBean {
	String email
	String name
	String message
  String url
	MessageType type
}
