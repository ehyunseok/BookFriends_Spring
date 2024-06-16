package com.daney.bookfriends.page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;

//PageImpl 클래스의 직렬화를 처리할 커스텀 serializer를 작성
public class PageSerializer extends StdSerializer<PageImpl<?>> {

    public PageSerializer() {
        super((Class<PageImpl<?>>)(Class<?>)PageImpl.class);
    }

    @Override
    public void serialize(PageImpl<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("content", value.getContent());
        gen.writeObjectField("pageable", value.getPageable());
        gen.writeNumberField("totalElements", value.getTotalElements());
        gen.writeEndObject();
    }
}