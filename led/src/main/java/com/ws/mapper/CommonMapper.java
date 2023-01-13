package com.ws.mapper;

import com.ws.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public interface CommonMapper {
    void saveLogin(LoginUP login);
}
