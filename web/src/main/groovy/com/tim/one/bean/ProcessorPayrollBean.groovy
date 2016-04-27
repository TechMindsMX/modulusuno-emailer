package com.tim.one.bean

import com.tim.one.enums.MessageType

class ProcessorPayrollBean implements EmailBean {
	String email
	String message
  String url
	MessageType type
}
