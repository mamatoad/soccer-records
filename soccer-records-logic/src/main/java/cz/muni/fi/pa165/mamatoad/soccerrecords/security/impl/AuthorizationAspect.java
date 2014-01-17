package cz.muni.fi.pa165.mamatoad.soccerrecords.security.impl;

import cz.muni.fi.pa165.mamatoad.soccerrecords.security.SecurityFacade;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author Maros Klimovsky
 */
@Aspect
@Component
public class AuthorizationAspect {
    
    @Autowired
    private SecurityFacade securityFacade;
        
    @Around("bean(*Service)")
    public Object intercept(ProceedingJoinPoint joinPoint) throws Exception, Throwable {
        
       final String methodName = joinPoint.getSignature().getName();
       final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
       Method method = methodSignature.getMethod();
        
       if (method.getDeclaringClass().isInterface()) {
            method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());    
        }
       
       if (!securityFacade.authorize(method)) {
           if (securityFacade.getCurrentLoggedInUser() == null) {
               throw new AccessControlException("Not logged in.");
           }
           throw new AccessControlException("Operation '" 
                   + method.getName()
                   + "' is not authorized for current user.");
       }
       
       return joinPoint.proceed();
    }
}