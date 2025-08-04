package cn.sparrowmini.common.rest;

import cn.sparrowmini.common.model.AppConfig;
import cn.sparrowmini.common.service.CommonJpaService;
import cn.sparrowmini.common.service.SimpleJpaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app-configs")
public class AppConfigController {

    @Autowired
    private CommonJpaService commonJpaService;

    @GetMapping("")
    @ResponseBody
    public Page<AppConfig> getEntityList(Pageable pageable, String filter){
        return commonJpaService.getEntityList(AppConfig.class, pageable, filter);
    }

}
