package io.eightmonth.sc.demoprovideruser.dao;

import io.eightmonth.sc.demoprovideruser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
