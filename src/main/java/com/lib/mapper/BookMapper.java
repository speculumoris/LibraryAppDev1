package com.lib.mapper;

import com.lib.domain.*;
import com.lib.dto.BookDTO;
import com.lib.dto.request.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {


}
