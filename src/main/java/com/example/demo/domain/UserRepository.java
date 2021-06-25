package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
