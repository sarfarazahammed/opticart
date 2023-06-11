package com.sarfaraz.opticart.user.converter;

import com.sarfaraz.opticart.commons.converter.Converter;
import com.sarfaraz.opticart.user.dto.MemberDto;
import com.sarfaraz.opticart.user.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements Converter<Member, MemberDto> {

    @Override
    public Member convertToEntity(MemberDto dto) {
        if (dto == null) return null;
        Member member = new Member();
        member.setName(dto.getName());
        member.setDob(dto.getDateOfBirth());
        member.setGender(dto.getGender());

        return member;
    }

    @Override
    public MemberDto convertToDto(Member entity) {
        if (entity == null) return null;
        MemberDto member = new MemberDto();
        member.setId(entity.getId());
        member.setName(entity.getName());
        member.setDateOfBirth(entity.getDob());
        member.setGender(entity.getGender());
        return member;
    }

    public Member convertToEntity(MemberDto member, Member entity) {
        if (entity == null) return null;
        entity.setName(member.getName());
        entity.setDob(member.getDateOfBirth());
        entity.setGender(member.getGender());
        return entity;
    }

}
