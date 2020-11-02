package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface NoteMapper {
  @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
  @Options(
    useGeneratedKeys = true,
    keyProperty = "noteId"
  )
  int insert(Note note);
  
  @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
  Note getNote(Integer noteId);
  
  @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
  List<Note> getNotesByUser(Integer userId);
  
  @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
  int update(Note note);
  
  @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
  int delete(Integer noteId);
}
