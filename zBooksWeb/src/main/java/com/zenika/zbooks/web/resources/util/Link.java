package com.zenika.zbooks.web.resources.util;

import com.google.common.base.Objects;

public class Link {

    private String rel;
    private String href;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("rel", rel)
                .add("href", href)
                .toString();
    }
}
