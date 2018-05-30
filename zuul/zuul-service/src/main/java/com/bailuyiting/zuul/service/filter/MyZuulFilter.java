package com.bailuyiting.zuul.service.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;

//@Component
public class MyZuulFilter extends ZuulFilter{

	@Override
	public Object run() {
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
