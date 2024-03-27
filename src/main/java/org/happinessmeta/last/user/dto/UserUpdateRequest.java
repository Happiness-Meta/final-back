package org.happinessmeta.last.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(message = "비밀번호는 9자리 이상 입력해주세요.")
    private String password;
    private List<String> techStack;
    private String position;
    private String address;
    private String telephone;
    private String industry;
}
