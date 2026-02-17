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

import com.example.pbj_api.model.Bread;
import com.example.pbj_api.service.BreadService;

@RestController
@RequestMapping("/api/bread")
public class BreadController {

    private final BreadService service;

    public BreadController(BreadService service) {
        this.service = service;
    }

    @PostMapping
    public Bread create(@RequestBody Bread obj) {
        return service.save(obj);
    }

    @PutMapping
    public Bread update(@RequestBody Bread obj) {
        return service.save(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Bread getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Bread> getAll() {
        return service.getAll();
    }
}
