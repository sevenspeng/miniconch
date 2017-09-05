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
public interface RecordMapper {

	@Select("SELECT * FROM record t WHERE t.lat = #{lat} AND t.lng = #{lng}")
	@Results({ @Result(property = "isOfficial", column = "officialflag"),
			@Result(property = "recordDate", column = "date")

	})
	List<Record> findAllRecordByLnglat(@Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng);

	@Select("SELECT * FROM record t WHERE t.id = #{id}")
	@Results({ @Result(property = "isOfficial", column = "officialflag"),
			@Result(property = "recordDate", column = "date")

	})
	Record findRecordDetailById(@Param("id") String id);

	@Select("SELECT recordFile FROM record t WHERE t.lat = #{lat} AND t.lng = #{lng} and t.officialflag= 'Y'")
	String findOfficialRecordByLnglat(@Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng);

	/*
	 * @Select("SELECT * FROM record t WHERE t.lng >= #{lngSouthwest} AND t.lng <= #{lngNortheast} AND t.lat >= #{latSouthwest} AND t.lat <= #{latNortheast}"
	 * ) List<Record> findAllRecordByCoordinateScope(@Param("lngNortheast")
	 * BigDecimal lngNortheast,@Param("latNortheast") BigDecimal
	 * latNortheast, @Param("lngSouthwest") BigDecimal
	 * lngSouthwest, @Param("latSouthwest") BigDecimal latSouthwest);
	 */
	@Select("SELECT t.lng,t.lat,COUNT(t.id) as recordNum FROM record t WHERE t.lng >= #{lngSouthwest} AND t.lng <= #{lngNortheast} AND t.lat >= #{latSouthwest} AND t.lat <= #{latNortheast} GROUP BY t.lng,t.lat")
	@Results({ @Result(property = "lng", column = "lng"), @Result(property = "lng", column = "lng"),
			@Result(property = "recordNum", column = "recordNum", javaType = Long.class, jdbcType = JdbcType.INTEGER) })
	List<Map<String, Object>> findAllRecordByCoordinateScope(@Param("lngNortheast") BigDecimal lngNortheast,
			@Param("latNortheast") BigDecimal latNortheast, @Param("lngSouthwest") BigDecimal lngSouthwest,
			@Param("latSouthwest") BigDecimal latSouthwest);

	@Insert("INSERT INTO record(id,title,uid,coordinates,lat,lng,officialflag, summary,icon,recordfile,replyCount,likeCount,description,date,poi,citycode,url) VALUES(#{id}, #{title}, #{uid},#{coordinates},#{lat},#{lng},#{officialflag},#{summary},#{icon},#{recordfile},#{replyCount},#{likeCount},#{description},sysdate(),#{poi},#{citycode},#{url})")
	int InsertNewRecord(@Param("id") String id, @Param("title") String title,@Param("uid") String uid,
			@Param("coordinates") String coordinates, @Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng,
			@Param("officialflag") String officialflag, @Param("summary") String summary,
			@Param("icon") String icon, @Param("recordfile") String recordfile,@Param("replyCount") int replyCount,@Param("likeCount") int likeCount,
			@Param("description") String description, @Param("poi") String poi, @Param("citycode") String citycode,
			@Param("url") String url);
	// @Select("SELECT * FROM USER WHERE NAME = #{name}")
	// User findByName(@Param("name") String name);
	//
	// @Insert("INSERT INTO USER(ID, NAME, AGE) VALUES(#{id}, #{name}, #{age})")
	// int insert(@Param("id") Long id, @Param("name") String name,
	// @Param("age") Integer age);
	//
	// @Select("SELECT * FROM record")
	// @Results({
	// @Result(id = true, property = "id", column = "id"),
	// @Result(property = "isOfficial", column = "officialflag"),
	// @Result(property = "title", column = "summary")
	// })
	// List<Record> findAll();
	//
	// @Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name}, #{age})")
	// int insert(@Param("name") String name, @Param("age") Integer age);
	//
	// @Update("UPDATE USER_TEST SET age=#{age} WHERE name=#{name}")
	// void update(User user);
	//
	// @Delete("DELETE FROM USER_TEST WHERE id =#{id}")
	// void delete(Long id);
	//
	// @Insert("INSERT INTO USER_TEST(name, age) VALUES(#{name}, #{age})")
	// int insertByUser(User user);
	//
	// @Insert("INSERT INTO USER_TEST(name, age)
	// VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
	// int insertByMap(Map<String, Object> map);
}
