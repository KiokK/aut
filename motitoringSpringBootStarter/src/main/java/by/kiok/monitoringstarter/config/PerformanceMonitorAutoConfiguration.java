package by.kiok.monitoringstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import by.kiok.monitoringstarter.aspect.PerformanceMonitorAspect;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PerformanceMonitorAspect.class, PerformanceMonitorProperties.class})
@ConditionalOnProperty(prefix = "monitoring.status", name = "enabled", havingValue = "true")
public class PerformanceMonitorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PerformanceMonitorAspect performanceMonitorAspect() {
        return new PerformanceMonitorAspect(performanceMonitorProperties());
    }

    @Bean
    @ConditionalOnMissingBean
    public PerformanceMonitorProperties performanceMonitorProperties() {
        return new PerformanceMonitorProperties();
    }
}
