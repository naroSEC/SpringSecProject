package com.spbt.secproject.repository;

import com.spbt.secproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long>  {

    Account findByUserid(String userid);

    Account findByUsername(String userName);

    @Query(value = "select * from account where userid = :userid and userpw = :userpw", nativeQuery = true)
    Account findSQLByUserid(String userid, String userpw);

}
