package com.microservicetest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This class is a dummy filter to be used in runtime time at the occasion where
 * the core library is not found. <br>
 * This Filter won't be performing any operation besides the forward of the
 * requests.
 *
 * @author andre
 *
 */
public class DummyFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
