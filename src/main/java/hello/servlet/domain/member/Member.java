package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private Long id; // 회원 저장소 FK
    private String username;
    private int age;

    public Member(){}
    public Member(String username, int age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }
}
