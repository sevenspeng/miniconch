package com.xiaohailuo.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LikeMapper {
	
	@Insert("INSERT INTO likerecord(rid, uid) VALUES(#{rid}, #{uid})")
	int insert(@Param("rid") String rid, @Param("uid") String uid);
	
	@Select("SELECT COUNT(1) from likerecord t where t.rid = #{rid}")
	int findLikeCountByRid(String rid);
}
