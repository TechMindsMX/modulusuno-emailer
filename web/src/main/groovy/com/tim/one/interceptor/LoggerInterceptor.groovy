package com.tim.one.interceptor

import groovy.transform.AutoClone
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import com.tim.one.service.LoggerService
import com.tim.one.constant.ApplicationConstants

class LoggerInterceptor implements HandlerInterceptor {

	@Autowired
  LoggerService loggerService
	@Autowired
	Properties dynamic

	def whiteList = []

	@PostConstruct
	public void setup(){
		whiteList = dynamic.getProperty(ApplicationConstants.WHITE_LIST).tokenize(',')
	}

  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
  	def data = [:]
  	data.remoteHost = request.remoteHost
  	data.timeInMillis = System.currentTimeMillis()
  	data.method = request.method
  	data.requestURL = request.requestURL
  	data.parameters = request.parameterMap

  	loggerService.notifyRequest(data)
    return true
  }


  void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
  }


  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
  }

}
