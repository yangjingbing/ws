package com.ws.until;

import lombok.Data;

@Data
public class TagInfo {
    private String head;
    private String tenantId;
    private String nodeId;
    private String timeTage;
    private Object content;

    /*public String getHead() {
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

    public String getTimeTage() {
        return timeTage;
    }

    public void setTimeTage(String timeTage) {
        this.timeTage = timeTage;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }*/
}
