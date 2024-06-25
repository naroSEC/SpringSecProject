package com.spbt.secproject.Mapper;

import com.spbt.secproject.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {
    @Select("SELECT * FROM account WHERE userid = '${userid}' AND userpw = '${userpw}'")
    Account findByUserAndUserpw(String userid, String userpw);
}
