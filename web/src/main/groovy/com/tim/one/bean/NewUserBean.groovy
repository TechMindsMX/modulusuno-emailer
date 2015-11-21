package com.tim.one.bean

import com.tim.one.enums.MessageType

class NewUserBean implements EmailBean {
	String email
	String name
	MessageType type
}
