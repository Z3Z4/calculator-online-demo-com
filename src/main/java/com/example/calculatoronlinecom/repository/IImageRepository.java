package com.example.calculatoronlinecom.repository;

import com.example.calculatoronlinecom.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IImageRepository extends JpaRepository<ImageModel, Long> {
       Optional<ImageModel> findByUserId(Long id);
       Optional<ImageModel> findByPostId(Long id);

}
