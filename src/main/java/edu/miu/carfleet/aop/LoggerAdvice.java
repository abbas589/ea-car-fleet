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

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("| %-38s | %-49s |\n", "Method Name", methodName);
        System.out.println("|----------------------------------------|---------------------------------------------------|");
        System.out.printf("| %-38s | %-49s |\n", "Method Arguments", "Values");
        System.out.println("|----------------------------------------|---------------------------------------------------|");

        for (int i = 0; i < methodArgs.length; i++) {
            System.out.printf("| %-38s | %-60s |\n", "arg" + (i + 1), methodArgs[i]);
        }

        System.out.println("--------------------------------------------------------------------------------------------");
    }

}
