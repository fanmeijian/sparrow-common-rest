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
@RequestMapping("common-jpa")
public class CommonJpaController {

    @Autowired
    private CommonJpaService commonJpaService;

    @PostMapping("")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApiResponse<?> saveEntity(String className, @RequestBody List<Map<String, Object>> body) {
        Class<?> domainClass = null;
        try {
            domainClass = Class.forName(className);
            // 关键：构造一个 List<clazz> 的 JavaType
//            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<?> ids = commonJpaService.upsertEntity(domainClass, body);
        return new ApiResponse<>(ids);
    }

    @DeleteMapping("")
    @ResponseBody
    @Transactional
    public ApiResponse<Long> deleteEntity(String className, @RequestParam("id") Set<Object> ids) {
        Class<?> domainClass = null;
        try {
            domainClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ApiResponse<>(commonJpaService.deleteEntity(domainClass, ids));

    }

    @GetMapping("")
    @ResponseBody
    public Object getEntity(String className, @RequestParam("id") Object id) {
        try {
            Class<?> clazz = Class.forName(className);
            return commonJpaService.getEntity(clazz, id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/filter")
    @ResponseBody
    public Page<?> getEntityList(String className, Pageable pageable, String filter) {
        try {
            Class<?> clazz = Class.forName(className);
            return commonJpaService.getEntityList(clazz, pageable, filter);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
