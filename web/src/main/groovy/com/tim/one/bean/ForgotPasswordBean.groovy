package com.tim.one.bean

import com.tim.one.enums.MessageType

class ForgotPasswordBean implements EmailBean {
	String email
	String token
	MessageType type
}
