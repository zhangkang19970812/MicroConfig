package com.nju.tutorialtool.dao;

import com.nju.tutorialtool.model.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDirMapDao extends JpaRepository<ServiceInfo, Long> {

}
