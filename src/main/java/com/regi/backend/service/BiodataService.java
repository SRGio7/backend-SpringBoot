package com.regi.backend.service;

import com.regi.backend.dto.BiodataDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BiodataService {

    List<BiodataDTO> bios = new ArrayList<>();

    public BiodataDTO dataDiri(String name){
        for(BiodataDTO bio : bios){
            if(bio.getName().equalsIgnoreCase(name)){
                return bio;
            }
        }
        return null;
    }

    public List<BiodataDTO> listDataDiri(){
//        List<BiodataDTO> bios = new ArrayList<>();
//
//        BiodataDTO bio1 = new BiodataDTO();
//        bio1.setName("Reyvan");
//        bio1.setAge(23);
//        bio1.setGender('M');
//
//        BiodataDTO bio2 = new BiodataDTO();
//        bio2.setName("Lucklee");
//        bio2.setAge(22);
//        bio2.setGender('M');
//
//        BiodataDTO bio3 = new BiodataDTO();
//        bio3.setName("Yori");
//        bio3.setAge(21);
//        bio3.setGender('M');
//
//        BiodataDTO bio4 = new BiodataDTO();
//        bio4.setName("Emily");
//        bio4.setAge(20);
//        bio4.setGender('F');
//
//        BiodataDTO bio5 = new BiodataDTO();
//        bio5.setName("Tiara");
//        bio5.setAge(19);
//        bio5.setGender('F');
//
//        bios.add(bio1);
//        bios.add(bio2);
//        bios.add(bio3);
//        bios.add(bio4);
//        bios.add(bio5);

        return bios;
    }

    public List<BiodataDTO> newData(BiodataDTO biodataDTO){
        BiodataDTO bio = new BiodataDTO();
        bio.setName(biodataDTO.getName());
        bio.setGender(biodataDTO.getGender());
        bio.setAge(biodataDTO.getAge());
        bios.add(bio);
        return bios;
    }
}