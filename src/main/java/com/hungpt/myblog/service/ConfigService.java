package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.request.ConfigRequest;
import com.hungpt.myblog.dto.response.ConfigResponse;
import com.hungpt.myblog.entity.Config;
import com.hungpt.myblog.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfigService {
    private final ConfigRepository configRepository;

    public List<ConfigResponse> getConfigs() {
        List<Config> configs = configRepository.findAll();
        return configs.stream()
                .map(config -> new ConfigResponse().fromEntity(config))
                .collect(Collectors.toList());
    }

    /**
     * Updates a configuration setting.
     *
     * @param configRequest Request DTO containing updated configuration data.
     * @return Updated configuration response.
     */
    public ConfigResponse updateConfig(ConfigRequest configRequest) {
        // Find the config by key using stream filtering
        Config existingConfig = configRepository.findAll().stream()
                .filter(config -> config.getKey().equals(configRequest.getKey()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Config not found"));

        // Update the configuration with new values
        existingConfig.setValue(configRequest.getValue());
        existingConfig.setDescription(configRequest.getDescription());
        existingConfig.setType(configRequest.getType());

        // Save the updated configuration
        configRepository.save(existingConfig);

        // Return updated configuration response
        return new ConfigResponse().fromEntity(existingConfig);
    }
}
