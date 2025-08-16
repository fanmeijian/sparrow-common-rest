package cn.sparrowmini.common.rest;

import cn.sparrowmini.common.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("permissions")
public class AppPermissionController {
    @Autowired
    private ScopeService scopeService;

    @PostMapping("/scopes/synchronize")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void synchronizeScope(){
        scopeService.synchronize();
    }
}
