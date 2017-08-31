package com.example.mylibrary;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Mihovil on 24/08/2017.
 */

@Aspect
public class TraceAspect {

    private static final String POINTCUT_METHOD = "execution(@com.example.mylibrary.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.example.mylibrary.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        DebugLog.log(className, buildLogMessage(methodName, stopWatch.getTotalTimeMillis()));

        return result;
    }

    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("\n\n\nDebug logger zapis koristeci TraceAspect!!!! --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]\n\n\n");

        return message.toString();
    }
}
