package com.ttf.VideoUpload.repository;

import com.ttf.VideoUpload.model.Contenuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenutoRepository extends JpaRepository<Contenuto, Long> {
}
