package com.hungpt.myblog.service;

import com.hungpt.myblog.entity.Setting;
import com.hungpt.myblog.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;

    public Setting create() {
        return settingRepository.save(Setting.builder().key("foo").value("bar").build());
    }
}
