package com.wosarch.buysell.auctions.restclient.attachments.model;

import java.util.List;

public interface AttachmentClient {

    Boolean removeAttachment(Attachment attachment);

    Boolean removeAttachments(List<Attachment> attachments);

}
