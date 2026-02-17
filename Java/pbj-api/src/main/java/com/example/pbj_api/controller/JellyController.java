package com.example.pbj_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pbj_api.model.Jelly;
import com.example.pbj_api.service.JellyService;

@RestController
@RequestMapping("/api/jelly")
public class JellyController {

    private final JellyService service;

    public JellyController(JellyService service) {
        this.service = service;
    }

    @PostMapping
    public Jelly create(@RequestBody Jelly obj) {
        return service.save(obj);
    }

    @PutMapping
    public Jelly update(@RequestBody Jelly obj) {
        return service.save(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Jelly getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Jelly> getAll() {
        return service.getAll();
    }
}
