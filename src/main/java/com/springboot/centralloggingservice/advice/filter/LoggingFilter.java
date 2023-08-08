package com.springboot.centralloggingservice.advice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String requestBody = getStringValue(cachedBodyHttpServletRequest.getCachedBody(), request.getCharacterEncoding());
        LOGGER.info("STARTED PROCESSING REQUEST : METHOD = {}; URI = {}; PAYLOAD = {}", request.getMethod(), request.getRequestURI(), requestBody);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(cachedBodyHttpServletRequest, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;


        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        LOGGER.info("FINISHED PROCESSING : RESPONSE CODE={}; RESPONSE={}; TIME TAKEN={} ms", response.getStatus(), responseBody, timeTaken);
        responseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
