

package com.jjlb.common;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BaseResult<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value="请求返回标示(true:成功;false:异常)")
	private boolean success = true;
	
	
	@ApiModelProperty(value="状态编号10000:成功;10001：处理中;10002：失败;10003:身份证已存在;10004:登录密码错误,剩余重试次数;10005:注册发送短信时图形验证码过期;11110：代表帐户在其他设备登录;11111：代表session超时;")
	private String code ="10000";
	
	
	@ApiModelProperty(value="返回信息")
	private String msg = "success";
	
	
	@ApiModelProperty(value="单体对象")
	private T obj;
	
	
	@ApiModelProperty(value="集合")
	private List<T> lists;
	
	
	@ApiModelProperty(value="分页列表")
	private PageInfo<T> pages;

	@ApiModelProperty(hidden = true)
	private Map<?,?> map;

	public BaseResult() {
		super();
	}

	public BaseResult(T obj) {
		super();
		this.obj = obj;
	}
	
	public BaseResult(List<T> lists) {
		super();
		this.lists = lists;
	}

	public BaseResult(PageInfo<T> pages) {
		super();
		this.pages = pages;
	}
	
	public BaseResult(boolean success, String code, String msg) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public BaseResult(boolean success, String code, String msg, T obj) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}

	public BaseResult(boolean success, String code, String msg, List<T> lists) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.lists = lists;
	}

	public BaseResult(boolean success, String code, String msg, PageInfo<T> pages) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.pages = pages;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public PageInfo<T> getPages() {
		return pages;
	}

	public void setPages(PageInfo<T> pages) {
		this.pages = pages;
	}

	public Map<?, ?> getMap() {
		return map;
	}

	public void setMap(Map<?, ?> map) {
		this.map = map;
	}
}