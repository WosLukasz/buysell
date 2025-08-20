package com.wosarch.buysell.auctions.restclient.attachments;

import com.wosarch.buysell.auctions.common.model.attachment.Attachment;

import java.util.List;

public interface AttachmentClient {

//    Boolean removeAttachment(Attachment attachment);

    Boolean removeAttachments(List<Attachment> attachments);

}
