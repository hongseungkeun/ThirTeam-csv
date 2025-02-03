package com.sparta.csv.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.csv.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
