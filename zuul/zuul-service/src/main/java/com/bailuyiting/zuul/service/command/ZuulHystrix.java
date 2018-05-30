package com.bailuyiting.zuul.service.command;



import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
//@Component
public class ZuulHystrix  extends HystrixCommand<String>{

	protected ZuulHystrix(HystrixCommandGroupKey group) {
		super(group);
	}

	@Override
	protected String run() throws Exception {
		HystrixCommandProperties.Setter()
		   .withExecutionTimeoutInMilliseconds(900000);
		return null;
	}



}
