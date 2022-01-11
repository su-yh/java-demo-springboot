package com.suyh.demo.config;

import com.suyh.demo.component.ArmsDemoMetrics;
import com.suyh.demo.component.SampleBean;
import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.export.ConditionalOnEnabledMetricsExport;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    /**
     * 参考: {@link PrometheusMetricsExportAutoConfiguration}
     */
    @ConditionalOnEnabledMetricsExport("prometheus")
    @Bean
    public ArmsDemoMetrics armsDemoMetrics() {
        return new ArmsDemoMetrics();
    }

    @ConditionalOnBean(CollectorRegistry.class)
    @Bean
    public SampleBean sampleBean(CollectorRegistry collectorRegistry) {
        return new SampleBean(collectorRegistry);
    }
}
