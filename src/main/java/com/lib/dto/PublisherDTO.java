package com.lib.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private Long id;

    private String name;

    private Boolean builtIn = false;

    public String findByName(String name) {
      return this.name;
    }
}
