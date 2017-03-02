package com.yunkuent.sdk;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

class HttpMethod extends HttpEntityEnclosingRequestBase {
	
	private String method = "GET";

	public HttpMethod() {
		super();
	}

	public HttpMethod(final URI uri) {
		super();
		setURI(uri);
	}

	public HttpMethod(final String uri) {
		super();
		setURI(URI.create(uri));
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
