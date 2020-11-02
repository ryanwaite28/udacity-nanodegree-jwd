package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface CredentialMapper {
  @Insert("INSERT INTO CREDENTIALS (url, username, salt, password, userid) VALUES(#{url}, #{username}, #{salt}, #{password}, #{userId})")
  @Options(
    useGeneratedKeys = true,
    keyProperty = "credentialId"
  )
  int insert(Credential credential);
  
  @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
  Credential getById(Integer credentialId);
  
  @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
  List<Credential> getByUserId(Integer userId);
  
  @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialId}")
  int update(Credential note);
  
  @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
  int delete(Integer credentialId);
}
