package com.excilys.ebi.bank.web.interceptor;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.excilys.ebi.bank.service.AnnotationScanner;

public class AnnotatedMethodHandlerInterceptor<A extends Annotation> implements HandlerInterceptor {

	private final Class<A> annotationType;

	@Autowired
	private AnnotationScanner annotationScanner;

	public AnnotatedMethodHandlerInterceptor(Class<A> annotationType) {
		this.annotationType = annotationType;
	}

	protected A getAnnotation(HandlerMethod handlerMethod) {
		return annotationScanner.getMethodOrTypeAnnotation(annotationType, handlerMethod.getMethod(), handlerMethod.getBeanType());
	}

	private boolean shouldIntercept(HandlerMethod handlerMethod) {
		return getAnnotation(handlerMethod) != null;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = HandlerMethod.class.cast(handler);
			if (shouldIntercept(handlerMethod)) {
				return preHandleInternal(request, response, handlerMethod);
			}
		}
		return true;
	}

	protected boolean preHandleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = HandlerMethod.class.cast(handler);
			if (shouldIntercept(handlerMethod)) {

				@SuppressWarnings({ "rawtypes", "unchecked" })
				Map<String, ?> pathVariables = (Map) request.getAttribute(View.PATH_VARIABLES);
				if (pathVariables != null && modelAndView != null) {
					modelAndView.addAllObjects(pathVariables);
				}
				postHandleInternal(request, response, handlerMethod, modelAndView);
			}
		}
	}

	protected void postHandleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = HandlerMethod.class.cast(handler);
			if (shouldIntercept(handlerMethod)) {
				afterCompletionInternal(request, response, handlerMethod, ex);
			}
		}
	}

	public void afterCompletionInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) throws Exception {
	}
}
