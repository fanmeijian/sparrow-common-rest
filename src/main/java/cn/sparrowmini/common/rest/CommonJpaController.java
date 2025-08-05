package cn.sparrowmini.common.rest;

import cn.sparrowmini.common.model.ApiResponse;
import cn.sparrowmini.common.service.CommonJpaService;
import cn.sparrowmini.common.service.SimpleJpaFilter;
import cn.sparrowmini.common.util.JpaUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("common-jpa-controller")
public class CommonJpaController {

    @Autowired
    private CommonJpaService commonJpaService;

    @PostMapping("/{className}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse<?> saveEntity(@PathVariable String className, @RequestBody List<Object> body){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Class<?> clazz = Class.forName(className);
            // 关键：构造一个 List<clazz> 的 JavaType
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            List<?> ids = commonJpaService.saveEntity(new ObjectMapper().convertValue(body, type));
            return new ApiResponse<>(ids);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @PatchMapping("/{className}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateEntity(@PathVariable String className,@RequestBody List<Map<String, Object>> entities){
        try {
            Class<?> clazz = Class.forName(className);
            commonJpaService.updateEntity(clazz,entities);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @DeleteMapping("/{className}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteEntity(@PathVariable String className,@RequestParam("id") Set<Object> ids){
        try {
            Class<?> clazz = Class.forName(className);
            commonJpaService.deleteEntity(clazz, ids);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping( "/{className}/get-by-id")
    @ResponseBody
    public Object getEntity(@PathVariable String className,@RequestParam String id){
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> idClass = JpaUtils.getIdType(clazz);
            Object id_ = new ObjectMapper().convertValue(id, idClass);
            return commonJpaService.getEntity(clazz, id_);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
