package com.kata.pre_3_1_2_sb.api.response;

import com.kata.pre_3_1_2_sb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoleResponse {
    private Integer id;

    private String name;
}
