package by.kiok.monitoringstarter.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "monitoring.status")
public class PerformanceMonitorProperties {

    private boolean enabled = true;
    private long minExecutionTime = 100;
}
