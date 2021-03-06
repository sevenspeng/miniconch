package com.xiaohailuo.domain;

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
public interface UserMapper {

    @Select("SELECT * FROM user WHERE NAME = #{name}")
    @Results({
    	@Result(id = true, property = "id", column = "id"),
        @Result(property = "whatisup", column = "what's up")
    })
    User findByName(@Param("name") String name);

    @Select("SELECT * FROM user WHERE NAME = #{name} AND PASSWORD = #{password}")
    @Results({
    	@Result(id = true, property = "id", column = "id"),
        @Result(property = "whatisup", column = "what's up")
    })
    User findByNameAndPassword(@Param("name") String name,@Param("password") String password);
    
    @Select("SELECT * FROM user WHERE uid = #{uid}")
    @Results({
    	@Result(id = true, property = "id", column = "id"),
        @Result(property = "whatisup", column = "what's up")
    })
    User findByuid(@Param("uid") String uid);
    
    @Select("SELECT * FROM user where id=#{id} ")
    @Results({
    	@Result(id = true, property = "id", column = "id"),
        @Result(property = "whatisup", column = "what's up")
    })
    User findById(@Param("id") String id);
    
    
	@Update("UPDATE user SET mycoin= mycoin + 1 WHERE id=#{uid}")
    @Results({
       @Result(property = "uid", column = "id")
    })
	int updateMycoinSendComment(@Param("uid") String uid);
	

	@Update("UPDATE user SET mycoin= mycoin + 5 WHERE id=#{uid}")
    @Results({
        @Result(property = "uid", column = "id")
     })
	int updateMycoinSendRecord(@Param("uid") String uid);
    
    @Insert("INSERT INTO user(id, name, nickname, profilephoto, subscribetime,password) VALUES(#{id}, #{name}, #{nickname}, #{profilephoto}, sysdate(),#{password})")
    int insert(@Param("id") String id, @Param("name") String name,@Param("nickname") String nickname, @Param("profilephoto") String profilephoto,@Param("password") String password);
    
    @Update("UPDATE user SET nickname=#{nickname} WHERE name=#{name}")
    int updateNickName(User user);    
    @Update("UPDATE user SET country=#{country},province=#{province},city=#{city} WHERE name=#{name}")
    int updateOrigo(User user);    
    @Update("UPDATE user SET image_url=#{image_url} WHERE name=#{name}")
    int updateImageUrl(User user);
    @Update("UPDATE user SET personnotes=#{personnotes} WHERE name=#{name}")
    int updatePersonNotes(User user);
    @Update("UPDATE user SET nickname=#{nickname},country=#{country},province=#{province},city=#{city},signature=#{signature},lastupdatetime=now(),gender=#{gender},profilephoto=#{profilephoto} WHERE id=#{id}")
    int updateAllInfro(User user);
    
    @Select("select count(*) from footprint where uid=#{uid}")
    int getFootprintNumByUid(String uid);

    @Update("UPDATE user SET nickname=#{nickname},country=#{country},province=#{province},city=#{city},gender=#{sex},profilephoto=#{headimgurl} WHERE id=#{openid}")
    int updateWXuserinfo(WXuserinfo user);
    @Insert("INSERT INTO user(id, nickname, profilephoto, country,province,city,gender,subscribetime) VALUES(#{openid}, #{nickname}, #{headimgurl}, #{country}, #{province}, #{city}, #{sex},now())")
    void addUser(WXuserinfo wxuserinfo);
}