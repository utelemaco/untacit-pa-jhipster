package org.untacit.paJhipster.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class CancelProcessInstanceService {

    private final Logger log = LoggerFactory.getLogger(CancelProcessInstanceService.class);

    @Autowired
    private RuntimeService runtimeService;

    private void cancelProcessInstance(String processDefinitionIdentifier, Object entityId) {
        //TODO: cancel a process instance by a processDefinitionIdentifier and a entityId...
        //log.debug("ProcessInstance canceled: {}", processInstanceId);
    }

}
