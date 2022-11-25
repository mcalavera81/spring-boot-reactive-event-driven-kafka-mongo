package com.example.demo.interceptor

import org.springframework.integration.config.GlobalChannelInterceptor
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
@GlobalChannelInterceptor(patterns = ["*-out-*"])
class GlobalOutChannelInterceptor : ChannelInterceptor {

    override fun afterSendCompletion(message: Message<*>, channel: MessageChannel, sent: Boolean, ex: Exception?) {
        super.afterSendCompletion(message, channel, sent, ex)
        println(String(message.payload as ByteArray, StandardCharsets.UTF_8))
    }
}
