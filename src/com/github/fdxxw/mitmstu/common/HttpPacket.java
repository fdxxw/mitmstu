package com.github.fdxxw.mitmstu.common;

import java.util.HashSet;
import java.util.Set;

public class HttpPacket {
	
	private String time;
	
	private String sourceIp;
	
	private String targetIp;
	
	private String path;
	
	private Set<String> paths = new HashSet<String>();
	
	private String method;
	
	private String host;
	
	private String connection;
	
	private String user_agent;
	
	private String accept_encoding;
	
	private String accept_language;
	
	private String accept_charset;
	
	private String cookie;

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getTargetIp() {
		return targetIp;
	}

	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getAccept_encoding() {
		return accept_encoding;
	}

	public void setAccept_encoding(String accept_encoding) {
		this.accept_encoding = accept_encoding;
	}

	public String getAccept_language() {
		return accept_language;
	}

	public void setAccept_language(String accept_language) {
		this.accept_language = accept_language;
	}

	public String getAccept_charset() {
		return accept_charset;
	}

	public void setAccept_charset(String accept_charset) {
		this.accept_charset = accept_charset;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Set<String> getPaths() {
		return paths;
	}

	public void setPaths(Set<String> paths) {
		this.paths = paths;
	}
	
	
}
