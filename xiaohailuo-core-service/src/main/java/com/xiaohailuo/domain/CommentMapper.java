package com.xiaohailuo.domain;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface CommentMapper {

	@Select("SELECT * FROM comments")
	List<Comment> findAll();

	@Select("SELECT * FROM comments t WHERE t.rid = #{rid} AND t.lng = #{lng}")
	@Results({ @Result(id = true, property = "id", column = "id"), @Result(property = "rid", column = "rid"),
			@Result(property = "uid", column = "uid"), @Result(property = "comment", column = "comment"),
			@Result(property = "date", column = "date"),

	})
	Comment findCommentByRid(@Param("rid") String rid);

	@Insert("INSERT INTO comments(rid, uid, comment, date) VALUES(#{rid}, #{uid}, #{comment}, sysdate())")
	int insert(@Param("rid") String rid, @Param("uid") String uid, @Param("comment") String comment);

	@Select("SELECT u.profilephoto,u.nickname,m.content,m.createDate,m.content,m.type,m.status,m.id FROM message m,user u where m.replyUid=u.id and m.uid=#{uid} order by m.createDate desc")
	@Results({
			@Result(property = "profilephoto", column = "profilephoto", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "nickname", column = "nickname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "status", column = "status", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createDate", column = "createDate", javaType = String.class, jdbcType = JdbcType.TIMESTAMP) })
	List<Map<String, Object>> getMessageListByUid(@Param("uid") String uid);

	@Update("update message set status='1' where id=#{id}")
	int updateMessageStatusById(@Param("id") String id);

}
