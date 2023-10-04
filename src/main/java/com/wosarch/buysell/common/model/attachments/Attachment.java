package com.wosarch.buysell.common.model.attachments;

import lombok.Data;

import java.io.Serializable;

@Data
public class Attachment implements Serializable {
    private String id;
    private String path;
    private String originalFilename;
    private String contentType;
    private boolean main;
}
