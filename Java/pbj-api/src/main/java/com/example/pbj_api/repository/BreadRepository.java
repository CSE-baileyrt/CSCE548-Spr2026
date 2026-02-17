package com.example.pbj_api.repository;

import com.example.pbj_api.model.Bread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreadRepository extends JpaRepository<Bread, Integer> {}
