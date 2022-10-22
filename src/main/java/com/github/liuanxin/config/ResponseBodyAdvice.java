package com.github.liuanxin.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnClass({ HttpServletResponse.class, ResponseBody.class })
@ControllerAdvice(annotations = { Controller.class, RestController.class })
public class ResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    private final ObjectMapper objectMapper;

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter parameter,
                                           ServerHttpRequest request, ServerHttpResponse response) {
        if (log.isInfoEnabled()) {
            String json;
            try {
                json = objectMapper.writeValueAsString(bodyContainer.getValue());
            } catch (JsonProcessingException e) {
                return;
            }

            StringBuilder sbd = new StringBuilder();
            Class<?> clazz = parameter.getContainingClass();
            String className = clazz.getName();
            sbd.append(className);

            Method method = parameter.getMethod();
            String methodName = method != null ? method.getName() : "";
            if (!methodName.trim().isEmpty()) {
                sbd.append("#").append(methodName);
            }
            sbd.append(" return(").append(json).append(")");
            log.info(sbd.toString());
        }
    }
}
