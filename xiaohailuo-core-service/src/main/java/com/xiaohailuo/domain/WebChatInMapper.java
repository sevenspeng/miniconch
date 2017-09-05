package com.xiaohailuo.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
@Mapper
public interface WebChatInMapper {
//	@Insert("INSERT INTO comments(rid, uid, comment, date) VALUES(#{rid}, #{uid}, #{comment}, sysdate())")
//	int insert(@Param("rid") String rid,@Param("uid") String uid,@Param("comment") String comment);
//	
//	@Insert("INSERT INTO likerecord(rid, uid) VALUES(#{rid}, #{uid})")
//	int insert(@Param("rid") String rid, @Param("uid") String uid);
//	
//	@Select("SELECT COUNT(1) from likerecord t where t.rid = #{rid}")
//	int findLikeCountByRid(String rid);
//	
//	
//	@Select("SELECT t.lng,t.lat,COUNT(t.id) as recordNum FROM record t WHERE t.lng >= #{lngSouthwest} AND t.lng <= #{lngNortheast} AND t.lat >= #{latSouthwest} AND t.lat <= #{latNortheast} GROUP BY t.lng,t.lat")
//	@Results({
//		@Result(property = "lng", column = "lng"),
//		@Result(property = "lng", column = "lng"),
//		@Result(property = "recordNum", column = "recordNum", javaType=Long.class,jdbcType=JdbcType.INTEGER)
//	})
//	List<Map<String,Object>> findAllRecordByCoordinateScope(@Param("lngNortheast") BigDecimal lngNortheast,@Param("latNortheast") BigDecimal latNortheast, @Param("lngSouthwest") BigDecimal lngSouthwest, @Param("latSouthwest") BigDecimal latSouthwest);

}
