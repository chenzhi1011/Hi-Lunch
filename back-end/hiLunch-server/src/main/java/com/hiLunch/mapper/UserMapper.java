package com.hiLunch.mapper;

import com.hiLunch.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User getById(Long id);
    @Insert("insert into user (id,pwd,department,email,create_time,update_time)" +
            " values (#{id},#{pwd},#{department},#{email},#{createTime},#{updateTime})")
    void insert(User user);

    void update(User user);
}
