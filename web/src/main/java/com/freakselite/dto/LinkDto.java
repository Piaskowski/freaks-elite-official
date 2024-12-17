package com.freakselite.dto;

import com.freakselite.model.Link;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkDto {

    // == fields ==
    private int id;
    private String name;
    @NotEmpty(message = "link nie może być pusty")
    private String url;

    // constructors ==
    public LinkDto(Link link) {
        this.id = link.getId();
        this.name = link.getName();
        this.url = link.getUrl();
    }

    // == public methods ==
    public Link toLink(){
        Link link = new Link();

        link.setId(id);
        link.setName(name);
        link.setUrl(url);

        return link;
    }

}
