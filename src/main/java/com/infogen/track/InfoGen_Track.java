package com.infogen.track;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * AOP的工具类,可以获取存放在ThreadLocal中的对象
 * 
 * @author larry/larrylv@outlook.com/创建时间 2015年5月4日 下午2:11:06
 * @since 1.0
 * @version 1.0
 */
public class InfoGen_Track {
	public final Logger logger = Logger.getLogger(InfoGen_Track.class.getName());
	private static final ThreadLocal<HttpServletRequest> thread_local_request = new ThreadLocal<>();
	private static final ThreadLocal<HttpServletResponse> thread_local_response = new ThreadLocal<>();

	public static HttpServletRequest getRequest() {
		return thread_local_request.get();
	}

	public static void setRequest(HttpServletRequest request) {
		thread_local_request.set(request);
	}

	public static HttpServletResponse getResponse() {
		return thread_local_response.get();
	}

	public static void setResponse(HttpServletResponse response) {
		thread_local_response.set(response);
	}
}