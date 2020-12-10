package com.upload.Controller;

import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rongrong on 2019/12/17.
 */
@RestController
public class SystemControllers {


     @GetMapping("/index")
     public  String   index(){
         return "/index";
     }

}
