package com.wosarch.buysell.buysell.model.attachments;

import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = Attachment.ENTITY_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Attachment extends DatabaseObject {

    public static final String ENTITY_NAME = "attachments";

    private String etag;

    private String path;

    private String originalFilename;

    private String contentType;

    private boolean main;

    @Column(name = "attachmentOrder")
    private Integer order;

    public Attachment(Attachment attachment) {
        this.etag = attachment.getEtag();
        this.path = attachment.getPath();
        this.originalFilename = attachment.getOriginalFilename();
        this.contentType = attachment.getContentType();
        this.main = attachment.isMain();
        this.order = attachment.getOrder();
    }
}
