package com.example.test.mytestdemo.okhttpUtil.demo;

public class Result {

	public String code ;
	public String message  ;
	
	public Result() {
		// TODO Auto-generated constructor stub
	}

	public Result(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Result{" +
				"code='" + code + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
