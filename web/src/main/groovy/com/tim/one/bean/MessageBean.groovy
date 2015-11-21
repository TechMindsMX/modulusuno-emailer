package com.tim.one.bean

import com.tim.one.enums.MessageType

class MessageBean implements EmailBean {
	String email
	String message
	MessageType type
}
