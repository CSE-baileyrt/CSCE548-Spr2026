package com.example.pbj_api.service;

import com.example.pbj_api.model.Jelly;
import com.example.pbj_api.repository.JellyRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class JellyService {

    private final JellyRepository repo;

    public JellyService(JellyRepository repo) {
        this.repo = repo;
    }

    public Jelly save(Jelly obj) { return repo.save(obj); }

    public void delete(int id) { repo.deleteById(id); }

    public Jelly getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Jelly> getAll() { return repo.findAll(); }
}
