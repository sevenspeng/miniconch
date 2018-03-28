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
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;



@Mapper
public interface RecordMapper {

	@Select("SELECT * FROM record t WHERE t.lat = #{lat} AND t.lng = #{lng}")
	@Results({ @Result(property = "isOfficial", column = "officialflag"),
			@Result(property = "recordDate", column = "date")

	})
	List<Record> findAllRecordByLnglat(@Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng);
	
	 
	@Select("SELECT t.uid,t.id as recordID,t.title,t.icon,t.recordFile,u.nickname,t.duration,u.profilephoto,t.description,t.replyCount,t.likeCount,t.date  FROM record t,user u WHERE  t.lng =#{lng} AND t.lat =#{lat} and t.uid=u.id GROUP BY t.lng,t.lat")
	@Results({
			@Result(property = "uid", column = "uid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "recordID", column = "recordID", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "title", column = "title", javaType = String.class, jdbcType = JdbcType.VARCHAR),		
			@Result(property = "icon", column = "icon", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "profilephoto", column = "profilephoto", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "nickname", column = "nickname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "duration", column = "duration", javaType = Integer.class, jdbcType = JdbcType.INTEGER),			
			@Result(property = "recordFile", column = "recordFile", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "description", column = "description", javaType = String.class, jdbcType = JdbcType.VARCHAR),		
			@Result(property = "replyCount", column = "replyCount", javaType = String.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "likeCount", column = "likeCount", javaType = String.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "recordDate", column = "date", javaType = String.class, jdbcType = JdbcType.TIMESTAMP)			
			})
	List<Map<String, Object>> getRecordListByPosition(@Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng);

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
	@Select("SELECT t.id as recordID,t.lng,t.lat,t.title,t.officialflag,t.icon,t.recordFile,u.nickname,t.duration  FROM record t,user u WHERE t.lng >= #{lngSouthwest} AND t.lng <= #{lngNortheast} AND t.lat >= #{latSouthwest} AND t.lat <= #{latNortheast} and t.uid=u.id")
	@Results({ @Result(property = "lng", column = "lng"), @Result(property = "lng", column = "lng"),
			@Result(property = "recordID", column = "recordID", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "title", column = "title", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "isOfficial", column = "officialflag", javaType = String.class, jdbcType = JdbcType.CHAR),
			@Result(property = "icon", column = "icon", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "recordFile", column = "recordFile", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "nickname", column = "nickname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "duration", column = "duration", javaType = Integer.class, jdbcType = JdbcType.INTEGER)})
	List<Map<String, Object>> findAllRecordByCoordinateScope(@Param("lngNortheast") BigDecimal lngNortheast,
			@Param("latNortheast") BigDecimal latNortheast, @Param("lngSouthwest") BigDecimal lngSouthwest,
			@Param("latSouthwest") BigDecimal latSouthwest);


	@Insert("INSERT INTO record(id,title,uid,coordinates,lat,lng,officialflag, summary,icon,recordfile,replyCount,likeCount,description,date,poi,citycode,url,duration) VALUES(#{id}, #{title}, #{uid},#{coordinates},#{lat},#{lng},#{officialflag},#{summary},#{icon},#{recordfile},#{replyCount},#{likeCount},#{description},sysdate(),#{poi},#{citycode},#{url},#{duration})")
	int InsertNewRecord(@Param("id") String id, @Param("title") String title,@Param("uid") String uid,
			@Param("coordinates") String coordinates, @Param("lat") BigDecimal lat, @Param("lng") BigDecimal lng,
			@Param("officialflag") String officialflag, @Param("summary") String summary,
			@Param("icon") String icon, @Param("recordfile") String recordfile,@Param("replyCount") int replyCount,@Param("likeCount") int likeCount,
			@Param("description") String description, @Param("poi") String poi, @Param("citycode") String citycode,
			@Param("url") String url , @Param("duration") int duration);
	
	@Update("UPDATE record SET replyCount=replyCount + 1 WHERE id=#{id}")
	int updateReplyCount(@Param("id") String id);	
	
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
