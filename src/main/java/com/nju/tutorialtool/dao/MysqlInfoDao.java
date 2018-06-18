package com.nju.tutorialtool.dao;

import com.nju.tutorialtool.model.MysqlInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlInfoDao extends JpaRepository<MysqlInfo, Long> {

}
