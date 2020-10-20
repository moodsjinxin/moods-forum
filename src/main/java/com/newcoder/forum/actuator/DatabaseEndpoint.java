package com.newcoder.forum.actuator;


import com.newcoder.forum.util.CommunityUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Endpoint(id = "database")
public class DatabaseEndpoint  {
    private static final Logger logger  = LoggerFactory.getLogger(DatabaseEndpoint.class);
    private  final  String getSuccess = "获取链接成功！";
    private  final  String getFail    = "获取链接失败！";

    @Autowired
    private DataSource dataSource;

    @ReadOperation
     public String checkConnection() {
        try ( Connection connection = dataSource.getConnection();) {

            return CommunityUtil.getJSONString(0, getSuccess);
        } catch (SQLException e) {
            logger.error("获取链接失败："+ e.getMessage());
            return CommunityUtil.getJSONString(1, getFail);
        }
    }
}
