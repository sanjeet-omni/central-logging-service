package com.springboot.centralloggingservice.advice;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    private final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value = "execution(* com.springboot.centralloggingservice.*.*.*(..)) && !within(com.springboot.centralloggingservice.controller.*)")
    public void myPointCut() {}

    @Around("myPointCut()")
    public Object logMethodExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();

//        logger.info("API Method : {}, URI : {}", request.getMethod(), request.getRequestURI());

        ObjectMapper objectMapper = new ObjectMapper();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        Object[] arguments = proceedingJoinPoint.getArgs();

        logger.info("Method invoked {}: {}() arguments {}", className, methodName, objectMapper.writeValueAsString(arguments));
        Object result = proceedingJoinPoint.proceed();
        logger.info("{}: {}() Response {}", className, methodName, objectMapper.writeValueAsString(result));

        return result;
    }

    @Around("@annotation(com.springboot.centralloggingservice.advice.TrackExecutionTime)")
    public Object trackExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Total time taken = {} ms", (endTime - startTime));
        return result;
    }

}