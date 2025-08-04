package cn.sparrowmini.common.rest;

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
@RequestMapping("common-jpa-controller")
public class CommonJpaController {

    @Autowired
    private CommonJpaService commonJpaService;

    @PostMapping("/{className}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveEntity(@PathVariable String className,@RequestBody Object[] body){
        commonJpaService.saveEntity(className,body);
    }

    @PatchMapping("/{className}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateEntity(@PathVariable String className,@RequestBody List<Map<String, Object>> entities){
        commonJpaService.updateEntity(className,entities);
    }

    @PatchMapping("/{className}/delete")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEntity(@PathVariable String className,@RequestBody Object[] ids){
        commonJpaService.deleteEntity(className, ids);
    }

    @PostMapping( "/{className}/get-by-id")
    @ResponseBody
    public Object getEntity(@PathVariable String className,@RequestBody Map<String, Object> map){
        return commonJpaService.getEntity(className, map.get("id"));

    }


    @PostMapping("/{className}/filter")
    @ResponseBody
    public Page<Object> getEntityList(@PathVariable String className, Pageable pageable,@RequestBody List<SimpleJpaFilter> filterList){
        return commonJpaService.getEntityList(className, pageable, filterList);
    }

    @GetMapping("/{className}")
    @ResponseBody
    public Page<?> getEntityList(@PathVariable String className, Pageable pageable, String filter){
        try {
            Class<?> clazz = Class.forName(className);
            return commonJpaService.getEntityList(clazz, pageable, filter);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
