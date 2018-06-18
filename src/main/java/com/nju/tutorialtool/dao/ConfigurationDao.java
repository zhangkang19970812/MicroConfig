package com.nju.tutorialtool.dao;

import com.nju.tutorialtool.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationDao extends JpaRepository<Configuration, Long> {

}
