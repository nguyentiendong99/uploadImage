package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Controller
public class InfoController {

    @RequestMapping("/info")
    public String register(){
        return "info";
    }
    @PostMapping("/register")
    public String save(
            @RequestParam("name") String name,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("filecv") MultipartFile filecv ,
            ModelMap modelMap
    ){
        Info info = new Info();
        info.setName(name);
        if (photo.isEmpty() || filecv.isEmpty()){
            return "info";
        }
        Path path = Paths.get("uploads/");
        try{
            InputStream inputStream = photo.getInputStream();
            Files.copy(inputStream , path.resolve(photo.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            info.setPhoto(photo.getOriginalFilename().toLowerCase());


            inputStream = filecv.getInputStream();
            Files.copy(inputStream , path.resolve(filecv.getOriginalFilename()) ,
                    StandardCopyOption.REPLACE_EXISTING);
            modelMap.addAttribute("INFOR" , info);
            modelMap.addAttribute("FILE_NAME" , filecv.getOriginalFilename());
            modelMap.addAttribute("FILE_TYPE" , filecv.getContentType());
            modelMap.addAttribute("FILE_SIZE" , filecv.getSize());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "view-info";
    }
}
