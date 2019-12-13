package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.service.CommonService;

@RestController
public class CommonController {
    private CommonService commonService;

    @Autowired
    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping(value = "/statistics")
    @ResponseBody
    public ResponseEntity getStatistics() {
        return ResponseEntity.ok(commonService.getStatistics());
    }

    @GetMapping(value = "/clear")
    public ResponseEntity clearAll() {
        commonService.clearAll();
        return ResponseEntity.ok().build();
    }
}
