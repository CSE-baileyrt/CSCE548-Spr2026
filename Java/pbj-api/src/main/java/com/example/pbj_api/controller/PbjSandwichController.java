package com.example.pbj_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pbj_api.model.PbjSandwich;
import com.example.pbj_api.service.PbjSandwichService;

@RestController
@RequestMapping("/api/sandwich")
public class PbjSandwichController {

    private final PbjSandwichService service;

    public PbjSandwichController(PbjSandwichService service) {
        this.service = service;
    }

    @PostMapping
    public PbjSandwich create(@RequestBody PbjSandwich obj) {
        return service.save(obj);
    }

    @PutMapping
    public PbjSandwich update(@RequestBody PbjSandwich obj) {
        return service.save(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public PbjSandwich getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PbjSandwich> getAll() {
        return service.getAll();
    }
}
