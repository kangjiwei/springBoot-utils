package com.upload.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rongrong on 2019/12/17.
 */
@Controller
public class SystemController {

     @RequestMapping("/index")
     public  String   index(){
         return "/index.html";
     }


}
