package com.xiaohailuo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SignMapper {
	@Select("SELECT * FROM sign")
	List<Sign> findAll();
	
	@Select("SELECT * FROM sign t WHERE t.rid = #{rid} and t.um = #{um}")
	@Results({
		@Result(id = true, property = "id", column = "id"),
		@Result(property = "rid", column = "rid"),
		@Result(property = "um", column = "um"),
		@Result(property = "date", column = "date"),
	})
	Sign findSignByRid(@Param("rid") String rid, @Param("um") String um);

	@Insert("INSERT INTO sign(rid, um, date) VALUES(#{rid}, #{um}, sysdate())")
	int insert(@Param("rid") String rid,@Param("um") String um);
	
}
