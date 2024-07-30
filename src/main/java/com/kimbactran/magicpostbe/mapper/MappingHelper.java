package com.kimbactran.magicpostbe.mapper;

import com.kimbactran.magicpostbe.config.ModelMapperConfig;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractCondition;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MappingHelper {
    private ModelMapper modelMapper;
    private final ModelMapperConfig.ModelMapperFactory modelMapperFactory;

    public MappingHelper(ModelMapperConfig.ModelMapperFactory modelMapperFactory) {
        this.modelMapperFactory = modelMapperFactory;
        this.modelMapper = modelMapperFactory.getMapper();
    }

    public <T, H> List<T> mapList(List<H> source, Class<T> targetClass) {
        return CollectionUtils.isEmpty(source) ?
                Collections.emptyList() :
                source.stream().map(i -> modelMapper.map(i, targetClass)).collect(Collectors.toList());
    }

    public <T, H> Page<T> mapPage(Page<H> source, Class<T> targetClass) {
        return source.map(i -> modelMapper.map(i, targetClass));
    }

    public <T, H> T map (H source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public <T, H> void mapIfSourceNotNull (T source, H targetClass) {
        ModelMapper mapper = modelMapperFactory.getMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        mapper.map(source, targetClass);
    }

    public <T, H> void mapIfSourceNotNullAndStringNotBlank(T source, H targetClass) {
        ModelMapper tmpMapper = modelMapperFactory.getMapper();
        tmpMapper.getConfiguration()
                .setPropertyCondition(isStringNotBlank());
        tmpMapper.map(source, targetClass);
    }

    public <T, H> T mapIgnoreAmbiguity(H source, Class<T> targetClass) {
        ModelMapper mapper = modelMapperFactory.getMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper.map(source, targetClass);
    }

    private Condition<Object, Object> isStringNotBlank() {
        return new AbstractCondition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> context) {
                if(context.getSource() instanceof String){
                    return StringUtils.isNotBlank(String.valueOf(context.getSource()));
                }
                return Objects.nonNull(context.getSource());
            }
        };
    }
}
