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
public interface UserTestMapper {

    @Select("SELECT * FROM USER_TEST WHERE NAME = #{name}")
    @Results({
        @Result(property = "name", column = "name"),
        @Result(property = "age", column = "age")
    })
    UserTest findByName(@Param("name") String name);
    
    /*@Select("SELECT * FROM USER_TEST WHERE ID = #{id}")
    @Results({
    	@Result(property = "id", column = "id"),
    	@Result(property = "name", column = "name"),
    	@Result(property = "age", column = "age"),
    	@Result(property = "address", column = "address"),
    	@Result(property = "address2", column = "address")
    })
    UserTest findById(@Param("id") Long id);*/
    
    @Select("SELECT B.nickname,A.id,A.age,A.address FROM USER_TEST A, USER B WHERE A.ID = #{id} AND B.NAME = 'ChinaYLong2016'")
    @Results({
    	@Result(property = "id", column = "id"),
    	@Result(property = "name", column = "nickname"),
    	@Result(property = "age", column = "age"),
    	@Result(property = "address", column = "address"),
    	@Result(property = "address2", column = "address")
    })
    UserTest findById(@Param("id") Long id);

    @Insert("INSERT INTO USER_TEST(ID, NAME, AGE, ADDRESS) VALUES(#{id}, #{name}, #{age}, #{address})")
    int insert(@Param("id") Long id, @Param("name") String name, @Param("age") Integer age, @Param(value = "address") String address);
    /*@Insert("INSERT INTO USER_TEST(ID, NAME, AGE, ADDRESS) VALUES(#{id}, #{name}, #{age}, #{addresss})")
    int insert(@Param("id") Long id, @Param("name") String name, @Param("age") Integer age, @Param(value = "addresss") String address);*/
    
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