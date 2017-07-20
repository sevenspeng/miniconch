package com.xiaohailuo.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DBHeartbeat {
	/**
	 * MySQL连接保持
	 * @return 数据库当前时间
	 */
	@Select("SELECT date_format(now(), '%Y-%c-%d %T') AS datetime")
	String pingMySQL();
}
