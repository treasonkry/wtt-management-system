package com.wtt.demo.aspect;

import com.wtt.demo.annotation.OperationLog;
import com.wtt.demo.pojo.UserActionLog;
import com.wtt.demo.service.UserActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 操作日志AOP切面
 * 自动记录用户操作行为
 */
@Aspect
@Component
public class OperationLogAspect {
    
    @Autowired
    private UserActionLogService userActionLogService;
    
    @Around("@annotation(com.wtt.demo.annotation.OperationLog)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        // 获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        
        // 创建日志对象
        UserActionLog log = new UserActionLog();
        log.setModule(operationLog.module());
        log.setActionType(operationLog.actionType());
        log.setDescription(operationLog.description());
        
        if (request != null) {
            log.setRequestUrl(request.getRequestURI());
            log.setRequestMethod(request.getMethod());
            log.setIpAddress(getIpAddress(request));
            log.setUserAgent(request.getHeader("User-Agent"));
            
            // 从session中获取用户信息（需要根据实际情况调整）
            Object adminUser = request.getSession().getAttribute("admin");
            Object playerUser = request.getSession().getAttribute("player");
            
            if (adminUser != null) {
                log.setUsername("admin");
                log.setUserType("admin");
            } else if (playerUser != null) {
                // 这里假设player对象有getUsername方法，需要根据实际情况调整
                log.setUsername("player");
                log.setUserType("player");
            } else {
                log.setUsername("anonymous");
                log.setUserType("guest");
            }
        }
        
        log.setCreateTime(LocalDateTime.now());
        
        Object result = null;
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            
            // 记录响应时间和状态码
            long endTime = System.currentTimeMillis();
            log.setResponseTime((int) (endTime - startTime));
            log.setStatusCode(200);
            
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            log.setResponseTime((int) (endTime - startTime));
            log.setStatusCode(500);
            log.setDescription(log.getDescription() + " [异常: " + e.getMessage() + "]");
            throw e;
        } finally {
            // 异步保存日志（避免影响主业务流程）
            saveLogAsync(log);
        }
        
        return result;
    }
    
    /**
     * 异步保存日志
     */
    private void saveLogAsync(UserActionLog log) {
        new Thread(() -> {
            try {
                userActionLogService.insert(log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    /**
     * 获取客户端IP地址
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个代理时，第一个IP为真实IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
