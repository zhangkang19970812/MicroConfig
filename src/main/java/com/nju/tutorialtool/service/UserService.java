package com.nju.tutorialtool.service;

import com.nju.tutorialtool.dao.UserDao;
import com.nju.tutorialtool.model.User;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 每当一个用户来访问此系统时，就在数据库中增加一个User的id
     * 并返回当前新用户的id
     * @return
     */
    public String addUser() {
        User user = new User();
        Integer id = userDao.findMaxId();
        if (id == null) {
            id = 0;
        }
        id ++;
        user.setId(id);
        userDao.save(user);
        userDao.flush();
        return String.valueOf(id);
    }

    /**
     * 返回当前用户所在文件夹
     * @return
     */
    public String getUserFolder() {
        return BaseDirConstant.projectBaseDir + File.separator + "user" + String.valueOf(userDao.findMaxId());
    }
}
