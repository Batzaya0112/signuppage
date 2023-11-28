package com.example.application.data.repository;

import com.example.application.data.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Long>, JpaSpecificationExecutor<ImageEntity> {
}
