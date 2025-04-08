package jpabook.jpashop.api.req;

import jpabook.jpashop.domain.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMemberRequest {

    private String name;
    private Address address;

    @Builder
    public UpdateMemberRequest(String name, Address address) {
        this.name = name;
        this.address = address;
    }

}
