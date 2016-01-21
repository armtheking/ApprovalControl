package com.necl.core.serviceImpl;

import com.necl.core.dao.ConfigSystemDao;
import com.necl.core.model.ConfigSystem;
import com.necl.core.service.ConfigSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigSystemServiceImpl implements ConfigSystemService{

    @Autowired
    private ConfigSystemDao configSystemDao;
    
    @Override
    public ConfigSystem findByKey(String key) throws Exception {
        return configSystemDao.findByKey(key);
    }

    @Override
    public boolean update(ConfigSystem configSystem) throws Exception {
        return configSystemDao.update(configSystem);
    }
    
}
