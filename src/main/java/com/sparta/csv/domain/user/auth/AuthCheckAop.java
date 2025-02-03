package com.sparta.csv.domain.user.auth;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthCheckAop {

	private final HttpServletRequest httpServletRequest;

	@Around("@annotation(com.sparta.csv.domain.user.auth.AuthCheck)")
	public Object authCheck(ProceedingJoinPoint joinPoint) throws Throwable {

		String auth = (String) httpServletRequest.getAttribute("auth");
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		AuthCheck authCheck = method.getAnnotation(AuthCheck.class);

		if (authCheck != null && !authCheck.value().equals(auth)) {
			throw new SecurityException(String.format("권한 없음 : 필요한 권한 : %s, 가지고 있는 권한 : %s",authCheck.value(), auth));
		}

		return joinPoint.proceed();
	}

}
