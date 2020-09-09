package io.github.parkcheolu.netpotato.utils;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;

public class RequestUtils {

    public static StringBuilder formatParams(HttpRequest request) {
        StringBuilder responseData = new StringBuilder();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        if (! params.isEmpty()) {
            params.entrySet().forEach(p -> {
                String key = p.getKey();
                List<String> values = p.getValue();
                for (String aValue : values) {
                    responseData.append("Paramater: ").append(key.toUpperCase()).append(" = ")
                            .append(aValue.toUpperCase()).append("\r\n");
                }
            });
            responseData.append("\r\n");
        }
        return responseData;
    }

    public static StringBuilder formatBody(HttpContent httpContent) {
        StringBuilder responseData = new StringBuilder();
        ByteBuf content = httpContent.content();
        if (content.isReadable()) {
            responseData.append(content.toString(CharsetUtil.UTF_8).toUpperCase())
                    .append("\r\n");
        }
        return responseData;
    }

    public static StringBuilder evaluateDecoderResult(HttpObject httpObject) {
        StringBuilder responseData = new StringBuilder();
        DecoderResult result = httpObject.decoderResult();

        if (! result.isSuccess()) {
            responseData.append("..Decoder Failure: ");
            responseData.append(result.cause());
            responseData.append("\r\n");
        }
        return responseData;
    }

    public static StringBuilder prepareLastResponse(HttpRequest request, LastHttpContent trailer) {
        StringBuilder responseData = new StringBuilder();
        responseData.append("Good Bye!\r\n");
        if (! trailer.trailingHeaders().isEmpty()) {
            responseData.append("\r\n");
            trailer.trailingHeaders().names().forEach(name -> {
                trailer.trailingHeaders().getAll(name).forEach(value -> {
                    responseData.append("P.S. Trailing Headers: ");
                    responseData.append(name).append(" = ").append(value).append("\r\n");
                });
            });
            responseData.append("\r\n");
        }
        return responseData;
    }
}
