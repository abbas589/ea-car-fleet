package edu.miu.carfleet.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author bazz
 * Apr 26 2023
 * 12:05
 */
@Aspect
@Configuration
public class LoggerAdvice {

    Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);

    @Before("execution(* edu.miu.carfleet.service.*.*(..))")
    public void daoMethodLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("--------------------------------------------------------------\n");
        sb.append(String.format("Calling method: %s\n", methodName));
        sb.append("Method arguments:\n");
        for (Object arg : methodArgs) {
            sb.append(String.format("- %s\n", arg));
        }
        sb.append("--------------------------------------------------------------\n");

        logger.info(sb.toString());
    }

}
