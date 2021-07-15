package com.kang.ignite.utils;

import com.fasterxml.jackson.databind.JsonNode;

public class CptData  extends JFormatObject{

    public String chainname;
    private String source;
    private String version;
    private String content;
    public JsonNode contentObj;

    public CptData() {
    }

    public CptData(String _source, String _version, JsonNode _contentObj) {
        this.source = _source;
        this.version = _version;
        this.content = _contentObj.toString();
        this.contentObj = _contentObj;
    }

    public CptData(String _source, String _version, String _content) {
        this.source = _source;
        this.version = _version;
        this.content = _content;
    }

    public String getChainname() {
        return this.chainname;
    }

    public void setChainname(String chainname) {
        this.chainname = chainname;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JsonNode getContentObj() {
        return this.contentObj;
    }

    public void setContentObj(JsonNode contentObj) {
        this.contentObj = contentObj;
    }

}
