package com.regi.backend.controller;

import com.regi.backend.dto.BiodataDTO;
import com.regi.backend.service.BiodataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biodata")
public class BiodataController {

    @Autowired
    BiodataService biodataService;



    @GetMapping("/detail/{name}")
    public BiodataDTO detail(@PathVariable("name") String name){
        return biodataService.dataDiri(name);
    }

    @GetMapping("/list")
    public List<BiodataDTO> listDataDiri(){
        return biodataService.listDataDiri();
    }

    @PostMapping("/add")
    public List<BiodataDTO> add(@RequestBody BiodataDTO bio){
        return biodataService.newData(bio);
    }
}
