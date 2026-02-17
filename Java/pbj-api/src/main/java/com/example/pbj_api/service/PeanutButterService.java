package com.example.pbj_api.service;

import com.example.pbj_api.model.PeanutButter;
import com.example.pbj_api.repository.PeanutButterRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PeanutButterService {

    private final PeanutButterRepository repo;

    public PeanutButterService(PeanutButterRepository repo) {
        this.repo = repo;
    }

    public PeanutButter save(PeanutButter obj) { return repo.save(obj); }

    public void delete(int id) { repo.deleteById(id); }

    public PeanutButter getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<PeanutButter> getAll() { return repo.findAll(); }
}
