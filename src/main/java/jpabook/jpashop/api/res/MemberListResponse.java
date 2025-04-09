package jpabook.jpashop.api.res;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberListResponse {

    private String name;
    private Address address;

    @Builder
    protected MemberListResponse(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public static MemberListResponse of(Member member) {
        return MemberListResponse.builder()
                .name(member.getName())
                .address(member.getAddress())
                .build();
    }
}
