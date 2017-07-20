package com.xiaohailuo.domain;

import java.math.BigDecimal;
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
public interface CommentMapper {
	
	@Select("SELECT * FROM comments")
	List<Comment> findAll();
	
	@Select("SELECT * FROM comments t WHERE t.rid = #{rid} AND t.lng = #{lng}")
	@Results({
		@Result(id = true, property = "id", column = "id"),
		@Result(property = "rid", column = "rid"),
		@Result(property = "uid", column = "uid"),
		@Result(property = "comment", column = "comment"),
		@Result(property = "date", column = "date"),
		
	})
	Comment findCommentByRid(@Param("rid") String rid);

	@Insert("INSERT INTO comments(rid, uid, comment, date) VALUES(#{rid}, #{uid}, #{comment}, sysdate())")
	int insert(@Param("rid") String rid,@Param("uid") String uid,@Param("comment") String comment);
	
//	@Select("SELECT * FROM USER WHERE NAME = #{name}")
//	User findByName(@Param("name") String name);
//
//	@Insert("INSERT INTO USER(ID, NAME, AGE) VALUES(#{id}, #{name}, #{age})")
//	int insert(@Param("id") Long id, @Param("name") String name, @Param("age") Integer age);
//
//	@Select("SELECT name, age FROM record")
//	@Results({ @Result(property = "name", column = "name"), @Result(property = "age", column = "age") })
//	List<User> findAll();
//
//	@Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name}, #{age})")
//	int insert(@Param("name") String name, @Param("age") Integer age);
//
//	@Update("UPDATE USER_TEST SET age=#{age} WHERE name=#{name}")
//	void update(User user);
//
//	@Delete("DELETE FROM USER_TEST WHERE id =#{id}")
//	void delete(Long id);
//
//	@Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name}, #{age})")
//	int insertByUser(User user);
//
//	@Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
//	int insertByMap(Map<String, Object> map);
}
