package com.naturalprogrammer.spring.tutorial.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DemoBean {
	
	private static final Log log = LogFactory.getLog(DemoBean.class);
	
	public DemoBean () {
		log.info("DemoBean created!");
	}
	
	public String foo() {
		return "something";
	}

}
