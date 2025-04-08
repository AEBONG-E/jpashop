package jpabook.jpashop.api.req;

import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMemberRequest {

    @NotEmpty
    private String name;

    private Address address;

    @Builder
    public CreateMemberRequest(String name, Address address) {
        this.name = name;
        this.address = address;
    }

}
