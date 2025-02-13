package com.ttf.VideoUpload.service;

import com.ttf.VideoUpload.model.Contenuto;
import com.ttf.VideoUpload.repository.ContenutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContenutoService {

    private final ContenutoRepository contenutoRepository;

    public ContenutoService(ContenutoRepository contenutoRepository) {
        this.contenutoRepository = contenutoRepository;
    }

    public List<Contenuto> getAllContenuti() {
        return contenutoRepository.findAll();
    }

    public Optional<Contenuto> getContenutoById(Long id) {
        return contenutoRepository.findById(id);
    }

    public Contenuto saveContenuto(Contenuto contenuto) {
        return contenutoRepository.save(contenuto);
    }

    public void deleteContenuto(Long id) {
        contenutoRepository.deleteById(id);
    }
}
