package co.kr.sppartners.vo;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorityVo {

    private Long userId;
    private String authorityName;
}
