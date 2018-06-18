package com.nju.tutorialtool.dao;

import com.nju.tutorialtool.model.ConfigurationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationItemDao extends JpaRepository<ConfigurationItem, Long> {
}
