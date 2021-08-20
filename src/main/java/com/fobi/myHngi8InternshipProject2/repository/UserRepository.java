package com.fobi.myHngi8InternshipProject2.repository;

import com.fobi.myHngi8InternshipProject2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
