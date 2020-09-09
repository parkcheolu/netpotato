package io.github.parkcheolu.netpotato.handlers;

import io.github.parkcheolu.netpotato.utils.RequestUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoHttpRequestHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(EchoHttpRequestHandler.class);
    private HttpRequest request;
    private StringBuilder responseData = new StringBuilder();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete!");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("channelRead0 entered");
        if (msg instanceof HttpRequest) {
            logger.info("msg is HttpRequest");
            HttpRequest request = this.request = (HttpRequest) msg;

            if (HttpUtil.is100ContinueExpected(request)) {
                logger.info("100 continue request");
                writeResponse(ctx);
            }
            responseData.setLength(0);
            responseData.append(RequestUtils.formatParams(request));
        }
        responseData.append(RequestUtils.evaluateDecoderResult(request));

        if (msg instanceof HttpContent) {
            logger.info("msg is HttpContent");
            HttpContent httpContent = (HttpContent) msg;
            responseData.append(RequestUtils.formatBody(httpContent));
            responseData.append(RequestUtils.evaluateDecoderResult(request));

            if (msg instanceof LastHttpContent) {
                logger.info("msg is LastHttpContent");
                LastHttpContent trailer = (LastHttpContent) msg;
                responseData.append(RequestUtils.prepareLastResponse(request, trailer));
                writeResponse(ctx, trailer, responseData);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("exceptionCaught!");
        cause.printStackTrace();;
        ctx.close();
    }

    private void writeResponse(ChannelHandlerContext ctx) {
        logger.info("writeResponse!");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.CONTINUE,
                Unpooled.EMPTY_BUFFER);
        ctx.write(response);
    }

    private void writeResponse(ChannelHandlerContext ctx, LastHttpContent trailer, StringBuilder responseData) {
        logger.info("writeResponse - responseData :\n {}", responseData);
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                ((HttpObject) trailer).decoderResult().isSuccess() ? HttpResponseStatus.OK : HttpResponseStatus.BAD_REQUEST,
                Unpooled.copiedBuffer(responseData.toString(), CharsetUtil.UTF_8));

        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
                    httpResponse.content().readableBytes());
            httpResponse.headers().set(HttpHeaderNames.CONNECTION,
                    HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write(httpResponse);

        if (! keepAlive) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
