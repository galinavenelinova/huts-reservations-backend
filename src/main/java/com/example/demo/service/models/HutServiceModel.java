package com.example.demo.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HutServiceModel {
    private String id;
    private String name;
    private String shortInfo;
    private String longInfo;
    private String bedCapacity;
    private MountainServiceModel mountain;
}
