package com.zenika.zbooks.web.resources.util;

import java.util.ArrayList;
import java.util.List;

public class Links {

    private List<Link> links;

    public Links() {
        this.links = new ArrayList<>();
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }
}
