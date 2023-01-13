package com.ws.entity;

import java.util.Date;

public class TagInfo {
    private String head;
    private String tenantId;
    private String nodeId;
    private Date timeTage;
    private Object content;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Date getTimeTage() {
        return timeTage;
    }

    public void setTimeTage(Date timeTage) {
        this.timeTage = timeTage;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
