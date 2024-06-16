package com.daney.bookfriends.page;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//PageImpl 클래스의 역직렬화를 처리할 커스텀 deserializer를 작성
public class PageDeserializer extends StdDeserializer<PageImpl<?>> {

    public PageDeserializer() {
        super((Class<PageImpl<?>>)(Class<?>)PageImpl.class);
    }

    @Override
    public PageImpl<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jp.getCodec();
        JsonNode node = codec.readTree(jp);

        JsonNode contentNode = node.get("content");
        JsonNode pageableNode = node.get("pageable");
        JsonNode totalNode = node.get("totalElements");

        List<Object> content = new ArrayList<>();
        contentNode.forEach(jsonNode -> {
            try {
                content.add(codec.treeToValue(jsonNode, Object.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        PageRequest pageRequest = codec.treeToValue(pageableNode, PageRequest.class);
        long total = totalNode.asLong();

        return new PageImpl<>(content, pageRequest, total);
    }
}
