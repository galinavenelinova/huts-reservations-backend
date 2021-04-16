package com.example.demo.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HutInputModel {
    private String name;
    private String shortInfo;
    private String longInfo;
    private Integer bedCapacity;
    private MountainInputModel mountain;
}
