package com.kostenko.youtube.analytic.repository.security;

import com.kostenko.youtube.analytic.entity.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, String> {
}
