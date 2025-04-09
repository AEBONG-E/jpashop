package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.api.req.CreateMemberRequest;
import jpabook.jpashop.api.req.UpdateMemberRequest;
import jpabook.jpashop.api.res.CreateMemberResponse;
import jpabook.jpashop.api.res.MemberListResponse;
import jpabook.jpashop.api.res.ResultResponse;
import jpabook.jpashop.api.res.UpdateMemberResponse;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return this.memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public ResultResponse membersV2() {

        List<MemberListResponse> response = this.memberService.findMembers().stream()
                .map(MemberListResponse::of)
                .collect(Collectors.toList());

        return new ResultResponse(response);

    }

    /**
     * 회원 등록 api v1 (엔티티를 파라미터로 활용하는 케이스)
     * @param member
     * @return CreateMemberResponse
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {

        Long id = this.memberService.join(member);
        return CreateMemberResponse.builder()
                .id(id)
                .build();
    }

    /**
     * 회원 등록 api v2 (dto를 파라미터로 활용하는 케이스)
     * @param request
     * @return CreateMemberResponse
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = Member.builder()
                .name(request.getName())
                .address(request.getAddress())
                .build();

        Long id = this.memberService.join(member);
        return CreateMemberResponse.builder()
                .id(id)
                .build();
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        this.memberService.update(id, request.getName(), request.getAddress());
        Member findMember = this.memberService.findOne(id);

        return UpdateMemberResponse.builder()
                .id(findMember.getId())
                .name(findMember.getName())
                .address(findMember.getAddress())
                .build();

    }

}
