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

import com.example.pbj_api.model.PeanutButter;
import com.example.pbj_api.service.PeanutButterService;

@RestController
@RequestMapping("/api/pb")
public class PeanutButterController {

    private final PeanutButterService service;

    public PeanutButterController(PeanutButterService service) {
        this.service = service;
    }

    @PostMapping
    public PeanutButter create(@RequestBody PeanutButter obj) {
        return service.save(obj);
    }

    @PutMapping
    public PeanutButter update(@RequestBody PeanutButter obj) {
        return service.save(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public PeanutButter getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PeanutButter> getAll() {
        return service.getAll();
    }
}
