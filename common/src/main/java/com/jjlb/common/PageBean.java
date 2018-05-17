package com.jjlb.common;

import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModelProperty;

public class PageBean {

	@ApiModelProperty(value="页号,默认为1")
	private Integer pageNum = 1;
	
	@ApiModelProperty(value="页码,默认为10")
	private Integer pageSize= 10;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		
		if (StringUtils.isEmpty(pageNum) || pageNum == 0) {
			pageNum = 1;
		}
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		
		if (StringUtils.isEmpty(pageSize) || pageSize == 0) {
			pageSize = 10;
		}
		this.pageSize = pageSize;
	}
	
	
	
}
