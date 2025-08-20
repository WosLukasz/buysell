package com.wosarch.buysell.auctions.restclient.attachments;

import com.wosarch.buysell.auctions.common.model.attachment.Attachment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "attachments", path = "/attachments")
public interface AttachmentFeignClient {

    @RequestMapping(method = RequestMethod.DELETE, path = "/remove-many")
    ResponseEntity<Boolean> removeAttachments(List<Attachment> attachments);
}