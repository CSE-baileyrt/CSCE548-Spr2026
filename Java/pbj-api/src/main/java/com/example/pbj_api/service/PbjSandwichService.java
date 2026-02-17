package com.example.pbj_api.service;

import com.example.pbj_api.model.PbjSandwich;
import com.example.pbj_api.repository.PbjSandwichRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PbjSandwichService {

    private final PbjSandwichRepository repo;

    public PbjSandwichService(PbjSandwichRepository repo) {
        this.repo = repo;
    }

    public PbjSandwich save(PbjSandwich obj) { return repo.save(obj); }

    public void delete(int id) { repo.deleteById(id); }

    public PbjSandwich getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<PbjSandwich> getAll() { return repo.findAll(); }
}

