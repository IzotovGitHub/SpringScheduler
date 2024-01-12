package izotov.scheduler.config.scheduler.triggers;

import izotov.scheduler.config.jobs.TriggerProperties;
import izotov.scheduler.config.jobs.factory.YamlPropertySourceFactory;
import izotov.scheduler.config.scheduler.triggers.parser.TriggerPropertiesParser;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties
@PropertySource(value = "classpath:scheduler/triggers.yaml", factory = YamlPropertySourceFactory.class)
@Getter
public class TriggersConfig {
    
    // TODO валидация конфига
    public List<TriggerProperties> triggerProperties;
    
    public void setTriggerGroups(Map<String, Map<String, Object>> groups) {
        triggerProperties = groups.entrySet().stream()
                .flatMap(e -> TriggerPropertiesParser.parse(e.getKey(), e.getValue()).stream())
                .toList();
    }
    
}
