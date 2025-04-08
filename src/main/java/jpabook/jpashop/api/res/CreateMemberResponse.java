package jpabook.jpashop.api.res;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMemberResponse {

    private Long id;

    @Builder
    public CreateMemberResponse(Long id) {
        this.id = id;
    }
}
