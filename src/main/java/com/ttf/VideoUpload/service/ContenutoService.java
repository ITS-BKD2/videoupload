package com.ttf.videoupload.service;

import com.ttf.videoupload.model.Contenuto;
import com.ttf.videoupload.repository.ContenutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContenutoService {

    @Autowired
    private ContenutoRepository repository;

    public List<Contenuto> getAllContenuti() {
        return repository.findAll();
    }

    public void saveContenuto(Contenuto contenuto) {
        repository.save(contenuto);
    }
}
