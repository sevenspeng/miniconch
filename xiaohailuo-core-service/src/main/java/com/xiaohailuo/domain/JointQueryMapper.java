package com.xiaohailuo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xiaohailuo.bean.CommentInfo;

@Mapper
public interface JointQueryMapper {
//	@Select("SELECT a.uid, c.nickname, c.profilephoto, b.date, b.comment FROM record a, comments b, user c WHERE a.id = b.rid AND b.uid = c.id AND b.rid = a.id AND a.id = #{id}")
//	/*@Results({
//		@Result(column = "", property = "")
//	})*/
//	//List<CommentInfo> findCommentsByRid(@Param("id") String id);
//	List<CommentInfo> findCommentsByRid(String id);
	
	@Select("SELECT a.uid, c.nickname, c.profilephoto, date_format(b.date,'%Y-%c-%d %T') as date, b.comment FROM record a, comments b, user c WHERE a.id = b.rid AND b.uid = c.id AND b.rid = a.id AND a.id = #{id} ORDER BY b.date desc")
	List<CommentInfo> findCommentsByRid(@Param("id") String id);

}
