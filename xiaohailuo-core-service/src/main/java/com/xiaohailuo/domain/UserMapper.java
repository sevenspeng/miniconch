package com.xiaohailuo.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE NAME = #{name}")
    @Results({
    	@Result(id = true, property = "id", column = "id"),
        @Result(property = "whatisup", column = "what's up")
    })
    User findByName(@Param("name") String name);

    @Select("SELECT * FROM user WHERE NAME = #{name} AND PASSWORD = #{password}")
    @Results({
    	@Result(id = true, property = "id", column = "id"),
        @Result(property = "whatisup", column = "what's up")
    })
    User findByNameAndPassword(@Param("name") String name,@Param("password") String password);
    
    @Select("SELECT * FROM user where id=#{id} ")
    @Results({
    	@Result(id = true, property = "id", column = "id"),
        @Result(property = "whatisup", column = "what's up")
    })
    User findById(@Param("id") String id);
    
    @Insert("INSERT INTO user(id, name, nickname, profilephoto, subscribetime,password) VALUES(#{id}, #{name}, #{nickname}, #{profilephoto}, sysdate(),#{password})")
    int insert(@Param("id") String id, @Param("name") String name,@Param("nickname") String nickname, @Param("profilephoto") String profilephoto,@Param("password") String password);
    
    @Update("UPDATE user SET nickname=#{nickname} WHERE name=#{name}")
    int updateNickName(User user);    
    @Update("UPDATE user SET country=#{country},province=#{province},city=#{city} WHERE name=#{name}")
    int updateOrigo(User user);    
    @Update("UPDATE user SET image_url=#{image_url} WHERE name=#{name}")
    int updateImageUrl(User user);
    @Update("UPDATE user SET personnotes=#{personnotes} WHERE name=#{name}")
    int updatePersonNotes(User user);
    @Update("UPDATE user SET nickname=#{nickname},country=#{country},province=#{province},city=#{city},signature=#{signature},lastupdatetime=now(),gender=#{gender},profilephoto=#{profilephoto} WHERE id=#{id}")
    int updateAllInfro(User user);
    
    @Select("select count(*) from footprint where uid=#{uid}")
    int getFootprintNumByUid(String uid);

    /*@Results({
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    @Select("SELECT name, age FROM user")
    List<User> findAll();

    @Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Update("UPDATE USER_TEST SET age=#{age} WHERE name=#{name}")
    void update(User user);

    @Delete("DELETE FROM USER_TEST WHERE id =#{id}")
    void delete(Long id);

    @Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name}, #{age})")
    int insertByUser(User user);

    @Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);*/
}