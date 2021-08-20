package com.example.cicd.core.handler;

import static org.springframework.boot.web.error.ErrorAttributeOptions.defaults;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.misc.GlobalErrorAttributes;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Order(-2)
@Component
public class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {
	
	public GlobalErrorHandler(GlobalErrorAttributes errorAttributes, Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
		super(errorAttributes, resources, applicationContext);
		super.setMessageWriters(configurer.getWriters());
		super.setMessageReaders(configurer.getReaders());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return route(all(), this::renderErrorResponse);
	}
	
	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Throwable e = getError(request);
		log.error(e.getMessage(), e);
		
		Map<String, Object> errorAttributes = getErrorAttributes(request, defaults());
		return status(BAD_REQUEST).contentType(APPLICATION_JSON).body(fromValue(errorAttributes));
	}
	
}
