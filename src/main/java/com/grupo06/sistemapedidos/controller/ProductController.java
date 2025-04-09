package com.grupo06.sistemapedidos.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo06.sistemapedidos.model.Producto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@Controller

public class ProductController {
        
    @GetMapping("/producto")
    public Producto getProducto(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/all/producto")
    public ArrayList<Producto> getAllProductos() {
        return new String();
    }

    @PostMapping("/producto")
    public Producto postProducto(@RequestBody Producto entity) {
        
        
        return entity;
    }

    @PutMapping("path/{id}")
    public String putProducto(@PathVariable Integer id, @RequestBody String entity) {
         
        
        return entity;
    }
    
    @DeleteMapping("/del/producto/{id}")
    public void deleteUser(@PathVariable Integer id) {
        
    }


}
