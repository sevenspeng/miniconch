package com.xiaohailuo.domain;

import java.math.BigDecimal;
import java.util.Date;
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
public interface FootprintMapper {
	
	
	//插入足迹表
	@Insert("INSERT INTO footprint(uid,coordinates,lng,lat,city,province,country,date) VALUES( #{uid},#{coordinates},#{lng},#{lat},#{city},#{province},#{country},sysdate())")
	@Results({ 
		@Result(property = "footprintDate", column = "date")

    })
	int InsertNewFootprint(Footprint footprint);
	
	//通过uid查询足迹表
	@Select("SELECT * FROM footprint  WHERE uid = #{uid} order by  date desc limit 0,1")
	@Results({ 
			@Result(property = "footprintDate", column = "date")
	})
	Footprint findLatestFootprintByUID(@Param("uid") String uid);



	
	
    /**
     * 根据Uid查询所以足记
     * @param uid
     * @return
     */
	@Select("SELECT * FROM footprint t WHERE t.uid=#{id} order by date desc")
	@Results({ 
			@Result(property = "footprintDate", column = "date")
	})
	List<Footprint> findAllFootprintByUID(@Param("id") String uid);

	
	
    /**
     * 根据用户数量
     * @param uid
     * @return
     */
	@Select("SELECT count(*) FROM user")
	int getUserTatalNum();
	
	
    /**
     * 查询足记数量排名
     * @param uid
     * @return
     */
	@Select("select rank from(SELECT *,(SELECT count(DISTINCT score) FROM (  SELECT uid,count(*) score FROM footprint group by uid)  AS b WHERE a.score<b.score)+1 AS rank FROM (  SELECT uid,count(*) score FROM footprint group by uid)  AS a ORDER BY rank) r where uid=#{id}")
	int getUserRank(@Param("id") String uid);
	
	
	
    /**
     * 查询国家数量
     * @param uid
     * @return
     */
	@Select("SELECT count(distinct(t.country)) FROM footprint t where t.uid=#{id}")
	int getCountryNumForfootprint(@Param("id") String uid);
	
    /**
     * 查询省份数量
     * @param uid
     * @return
     */
	@Select("SELECT count(distinct(t.province)) FROM footprint t where t.uid=#{id}")
	int getProvinceNumForfootprint(@Param("id") String uid);
	
	
    /**
     * 查询城市数量
     * @param uid
     * @return
     */
	@Select("SELECT count(distinct(t.city)) FROM footprint t where t.uid=#{id}")
	int getCityNumForfootprint(@Param("id") String uid);
	
	
	
	
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


}
