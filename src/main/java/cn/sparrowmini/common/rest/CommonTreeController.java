package cn.sparrowmini.common.rest;

import cn.sparrowmini.common.model.ApiResponse;
import cn.sparrowmini.common.model.BaseTree;
import cn.sparrowmini.common.service.CommonTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("common-tree")
public class CommonTreeController {

    @Autowired
    private CommonTreeService commonTreeService;

    /**
     * 移动节点
     * @param currentId
     * @param nextId
     */
    @PatchMapping("/move")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void moveNode(Object currentId, Object nextId,String domainClassName){
        Class<? extends BaseTree> domainClass = null;
        try {
            domainClass = (Class<? extends BaseTree>) Class.forName(domainClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        commonTreeService.moveNode(currentId,nextId, domainClass);
    }

    /**
     * 获取子节点
     * @param parentId
     * @param pageable
     * @return
     */
    @GetMapping("/children")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public <T extends BaseTree> Page<T> getChildren(Object parentId, Pageable pageable,String domainClassName){
        Class<T> domainClass = null;
        try {
            domainClass = (Class<T>) Class.forName(domainClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return commonTreeService.getChildren(parentId,pageable,domainClass);
    }

    /**
     * 节点详情
     * @param id
     * @return
     */
    @GetMapping
    @ResponseBody
    public <T extends BaseTree> T getNode(Object id,String domainClassName){
        Class<T> domainClass = null;
        try {
            domainClass = (Class<T>) Class.forName(domainClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return commonTreeService.getNode(id,domainClass);
    }

    /**
     * 新增或更新节点
     * @param entitiesMap
     * @return
     */
    @PostMapping()
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public <T extends BaseTree, ID> ApiResponse<List<ID>> saveNode(List<Map<String, Object>> entitiesMap,String domainClassName){
        Class<T> domainClass = null;
        try {
            domainClass = (Class<T>) Class.forName(domainClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return commonTreeService.saveNode(entitiesMap,domainClass);
    }

    /**
     * 删除节点
     * @param ids
     */
    @DeleteMapping("")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public <T extends BaseTree, ID> void deleteNode(Set<ID> ids,String domainClassName){
        Class<T> domainClass = null;
        try {
            domainClass = (Class<T>) Class.forName(domainClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        commonTreeService.deleteNode(ids, domainClass);
    }
}
