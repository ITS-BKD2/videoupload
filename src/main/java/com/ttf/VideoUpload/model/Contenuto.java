package com.ttf.videoupload.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "contenuto")
public class Contenuto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
    private String video; // Nome del file salvato nello storage

    @Transient
    private MultipartFile videoFile; // Non verrà salvato nel DB, solo per l'upload

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }

    public MultipartFile getVideoFile() { return videoFile; }
    public void setVideoFile(MultipartFile videoFile) { this.videoFile = videoFile; }
}
