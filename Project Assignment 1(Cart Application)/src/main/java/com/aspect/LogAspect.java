package com.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class LogAspect {

    @Before("execution(* com.repository.CartRepository.*(..))")
    public void logBefore(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Start time:- " + LocalTime.now());
        System.out.println(">>> AOP method execution started " + methodName);
    }

    @After("execution(* com.repository.CartRepository.*(..))")
    public void logAfter(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("End time:- " + LocalTime.now());
        System.out.println(">>> AOP method completed successfully " + methodName);
    }
}
