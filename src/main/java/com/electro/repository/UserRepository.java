package com.electro.repository;

import com.electro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByNickNameContains(String key);

    User findFirstByNickNameEqualsOrMailEquals(String nick, String mail);

    User findFirstByMailEqualsAndPasswordEquals(String id, String pw);

}
