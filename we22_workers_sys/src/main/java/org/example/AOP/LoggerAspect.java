package org.example.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggerAspect {
    @Before("execution(public * org.example.services.*.*(..))")
    public void before(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        String params = Arrays.toString(joinPoint.getArgs());
        System.out.println(methodName+"方法的参数是"+params);
    }
    @AfterReturning(value="execution(public * org.example.services.*.*(..))",returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println(methodName + "方法的结果时" + result);
    }
}
