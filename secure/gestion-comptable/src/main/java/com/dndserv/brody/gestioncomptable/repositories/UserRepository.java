package com.dndserv.brody.gestioncomptable.repositories;

import com.dndserv.brody.gestioncomptable.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.login=?1")
    User findByLogin(String login);

    @Query("select u from User u where u.email=?1")
    User findByEmail(String email);

    @Query("select case when count(u)>0 then true else false END from User u where u.email=?1")
    Boolean verifyIfEmailExists(String email);

    @Query("select case when count(u)>0 then true else false END from User u where u.phone=?1")
    Boolean verifyIfPhoneExists(String phone);

    @Query("select case when count(u)>0 then true else false END from User u where u.login=?1")
    Boolean verifyIfLoginExists(String login);

    @Query("select u from User u where u.firstname like :kw or u.name like :kw")
    List<User> searchUsers(@Param("kw") String kw);
}
