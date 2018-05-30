package com.bailuyiting.commons.core.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="t_sys_parameter")
public class SysParameter extends AbstractLongEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4297620455885527940L;
	private String paramName;
	private String paramValue;
	private String paramMemo;
	private Integer paramLevel;
	private String paramType;
	private String paramCnname;

	public SysParameter() {
		super();
	}

	public SysParameter(String paramName) {
		this.paramName = paramName;
	}

	public SysParameter(String paramName, String paramValue, String paramMemo,
			int paramLevel, String paramType) {
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.paramMemo = paramMemo;
		this.paramLevel = paramLevel;
		this.paramType = paramType;
	}

 

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamMemo() {
		return this.paramMemo;
	}

	public void setParamMemo(String paramMemo) {
		this.paramMemo = paramMemo;
	}

	public Integer getParamLevel() {
		return this.paramLevel;
	}

	public void setParamLevel(Integer paramLevel) {
		this.paramLevel = paramLevel;
	}

	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamCnname() {
		return paramCnname;
	}

	public void setParamCnname(String paramCnname) {
		this.paramCnname = paramCnname;
	}
	
	
}
