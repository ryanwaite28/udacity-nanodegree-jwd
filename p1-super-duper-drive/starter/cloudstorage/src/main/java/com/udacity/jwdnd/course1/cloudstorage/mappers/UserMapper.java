package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;


@Mapper
public interface UserMapper {
  @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
  @Options(
    useGeneratedKeys = true,
    keyProperty = "userId"
  )
  int insert(User user);
  
  @Select("SELECT * FROM USERS WHERE username = #{username}")
  User getUser(String username);
  
  @Delete("DELETE FROM USERS WHERE username = #{username}")
  Integer delete(String username);
}
