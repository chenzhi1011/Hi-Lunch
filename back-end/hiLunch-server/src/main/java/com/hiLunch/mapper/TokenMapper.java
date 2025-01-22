package com.hiLunch.mapper;

import com.hiLunch.annotation.AutoFill;
import com.hiLunch.entity.PersistToken;
import com.hiLunch.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenMapper {
    @Select("select * from hiLunch_db.persist_token where secret_token= #{token};")
    PersistToken check(String token);

    @Insert("insert into persist_token (secret_token,create_time) values (#{secretToken},#{createTime})")
    @AutoFill(OperationType.INSERT)
    void insert(PersistToken persistToken);

    @Delete("delete from persist_token where secret_token = #{token} ")
    void delete(String token);
}
