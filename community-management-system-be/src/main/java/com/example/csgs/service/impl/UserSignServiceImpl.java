package com.example.csgs.service.impl;

import com.example.csgs.service.UserSignService;
import com.example.csgs.utils.RedisUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Log4j
@Service
public class UserSignServiceImpl implements UserSignService {
    @Resource
    RedisUtils redisUtils;

    /**
     * 退出登陆
     * @param request 携带Cookie至后端，清除redis缓存的token
     */
    @Override
    public boolean signOut(HttpServletRequest request) {
        String csgs_token = request.getHeader("csgs_token");
        if (!"".equals(csgs_token)) {
            if (RedisUtils.hasKey(csgs_token)) {
                log.info("用户 uid: " + redisUtils.get(csgs_token) + " 退出登录...");
                redisUtils.remove(csgs_token);
                return true;
            }
        }
        return false;
    }
}

