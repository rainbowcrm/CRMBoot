package com.primus.abstracts;

import com.techtrade.rads.framework.ui.controls.UIText;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/hello")
@RestController
public class HelloWorld {

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index() {
        UIText test = new UIText("id");
        return "{Hello World}";

    }




}
