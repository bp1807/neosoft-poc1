package com.neosoft.poc1.repo;

import com.neosoft.poc1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User>, JpaRepository<User, Integer> {

    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    void purgeUserById(@Param("id") Integer id);

    @Query("SELECT CASE WHEN COUNT(u)> 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = :id")
    boolean isRowPresent(@Param("id") Integer id);
}
