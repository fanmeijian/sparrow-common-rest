package cn.sparrowmini.common.rest;

import cn.sparrowmini.common.model.AppConfig;
import cn.sparrowmini.common.model.AppConfigAttachment;
import cn.sparrowmini.common.repository.AppConfigAttachmentRepository;
import cn.sparrowmini.common.repository.AppConfigRepository;
import cn.sparrowmini.common.service.CommonJpaService;
import cn.sparrowmini.common.service.SimpleJpaFilter;
import cn.sparrowmini.common.view.AppConfigInfo;
import cn.sparrowmini.common.view.AppConfigView;
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
@RequestMapping("app-configs")
public class AppConfigController {

    @Autowired
    private AppConfigRepository appConfigRepository;

    @Autowired
    private AppConfigAttachmentRepository appConfigAttachmentRepository;

    @GetMapping("")
    @ResponseBody
    public Page<AppConfigView> getEntityList(Pageable pageable, String filter){
        return appConfigRepository.findAll(pageable, filter, AppConfigView.class);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AppConfigInfo getEntityList(@PathVariable String id){
        return appConfigRepository.findByIdProjection(id, AppConfigInfo.class).orElseThrow();
    }

    @GetMapping("/attachments/{attachmentId}")
    @ResponseBody
    public AppConfigAttachment getAttachment(@PathVariable String attachmentId){
        return appConfigAttachmentRepository.findById(attachmentId).orElseThrow();
    }

    @DeleteMapping("/attachments")
    @ResponseBody
    public void deleteAttachment(@RequestParam("id") Set<String> attachmentIds){
        appConfigAttachmentRepository.deleteByIds(attachmentIds);
    }

}
