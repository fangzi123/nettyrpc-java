package com.nettyrpc.stater;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({NettyRpcServerAutoConfiguration.class})
public @interface EnableRpcServer {
}
