package com.tim.one.bean

import com.tim.one.enums.MessageType

class ContactBean implements EmailBean {
	String email
	String name
	String emailOptional
	String phone
	String subject
	String message
	MessageType type
}
