package com.itheima.mapper;

import com.itheima.pojo.Business;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BusinessMapper {

    /**
     * 根据userId查询商家信息
     */
    @Select("SELECT * FROM test.business WHERE user_id = #{userId}")
    Business getByUserId(Long userId);

    /**
     * 更新商家信息
     * 登录的用户可以从localstorage中获得相应的id
     */
    @Update("UPDATE test.business SET " +
            "company_name = #{companyName}, " +
            "contact_person = #{contactPerson}, " +
            "address = #{address}, " +
            "intro = #{intro} " +
            "WHERE id = #{id}")
    void updateById(Business business);
}