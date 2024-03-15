package org.happinessmeta.last.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdate {
    private String name;
    private String password;
    private List<String> techStack;
    private String position;
}
