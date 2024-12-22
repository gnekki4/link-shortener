package ru.gnekki4.linkshortener.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gnekki4.linkshortener.dto.CreateLinkInfoRequest;
import ru.gnekki4.linkshortener.model.LinkInfo;
import ru.gnekki4.linkshortener.model.LinkInfoResponse;

@Mapper(componentModel = "spring")
public interface LinkInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "openingCount", constant = "0L")
    LinkInfo fromCreateRequest(CreateLinkInfoRequest request, String shortLink);

    LinkInfoResponse toResponse(LinkInfo linkInfo);
}
