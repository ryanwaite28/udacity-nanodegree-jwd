package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface FileMapper {
  @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
  @Options(
    useGeneratedKeys = true,
    keyProperty = "fileId"
  )
  int insert(File file);
  
  @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
  File getFile(Integer fileId);
  
  @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
  File getFileByName(String fileName);
  
  @Select("SELECT * FROM FILES WHERE userid = #{userId}")
  List<File> getFilesByUser(Integer userId);
  
  @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
  int delete(Integer fileId);
}
