package jpabook.jpashop.api.res;

import jpabook.jpashop.domain.Address;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMemberResponse {

    private Long id;
    private String name;
    private Address address;

    @Builder
    public UpdateMemberResponse(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

}
