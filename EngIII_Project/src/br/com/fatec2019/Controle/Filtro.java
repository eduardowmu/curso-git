package br.com.fatec2019.Controle;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class Filtro implements Filter
{	@Override public void destroy() 
	{	
	}

	@Override public void doFilter(ServletRequest req, ServletResponse resp, FilterChain arg2)
			throws IOException, ServletException 
	{		
	}

	@Override public void init(FilterConfig arg0) throws ServletException 
	{	
	}	
}
