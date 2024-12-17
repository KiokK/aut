package by.kiok.monitoringstarter.aspect;

import by.kiok.monitoringstarter.config.PerformanceMonitorProperties;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "monitoring.status", name = "enabled", havingValue = "true")
public class PerformanceMonitorAspect {

    private final PerformanceMonitorProperties properties;
    private static Logger logger = Logger.getLogger("InfoLogging");

    @Around("@annotation(by.kiok.monitoringstarter.annotation.MonitorPerformance)")
    public Object logMonitorMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > properties.getMinExecutionTime()) {
            String methodName = joinPoint.getSignature().getName();
            logger.info(String.format("Method [%s] executed in [%d] ms\n", methodName, executionTime));
        }

        return result;
    }

}
