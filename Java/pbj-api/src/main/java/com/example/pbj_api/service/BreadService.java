package com.example.pbj_api.service;

import com.example.pbj_api.model.Bread;
import com.example.pbj_api.repository.BreadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BreadService {

    private final BreadRepository repo;

    public BreadService(BreadRepository repo) {
        this.repo = repo;
    }

    public Bread save(Bread b) { return repo.save(b); }

    public void delete(int id) { repo.deleteById(id); }

    public Bread getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Bread> getAll() { return repo.findAll(); }
}
