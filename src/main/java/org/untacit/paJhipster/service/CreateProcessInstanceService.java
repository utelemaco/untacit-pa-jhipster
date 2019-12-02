package org.untacit.paJhipster.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.untacit.paJhipster.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Component
public class CreateProcessInstanceService {

    private final Logger log = LoggerFactory.getLogger(CreateProcessInstanceService.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    public void createProcessInstance(String camundaProcessDefinitionId, String camundaDeploymentId, Object entity) {
        String businessKey = getProcessInstanceBusinessKey(entity);
        ProcessInstance camundaProcessInstance = runtimeService.createProcessInstanceById(camundaProcessDefinitionId)
            .businessKey(businessKey)
            .setVariable("processInstance", entity)
            .setVariable("pi", entity)
            .execute();
        log.debug("ProcessInstance created: {}", camundaProcessInstance.getId());
        setCamundaProcessDefinitionId(entity, camundaProcessDefinitionId);
        setCamundaDeploymentId(entity, camundaDeploymentId);
        setCamundaProcessInstanceId(entity, camundaProcessInstance.getId());
    }

    private String getProcessInstanceBusinessKey(Object entity) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ProcessInstanceBusinessKey.class)) {
                try {
                    field.setAccessible(true);
                    return (String) field.get(entity);
                } catch (IllegalAccessException e) {
                    log.error("Error retrieving field {}: {}", field, e);
                    return null;
                }
            }
        }
        return null;
    }

    private void setCamundaDeploymentId(Object entity, String deploymentId) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(DeploymentId.class)) {
                try {
                    field.setAccessible(true);
                    field.set(entity, deploymentId);
                } catch (IllegalAccessException e) {
                    log.error("Error setting field {}: {}", field, e);
                }
            }
        }
    }

    private void setCamundaProcessDefinitionId(Object entity, String processDefinitionId) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ProcessDefinitionId.class)) {
                try {
                    field.setAccessible(true);
                    field.set(entity, processDefinitionId);
                } catch (IllegalAccessException e) {
                    log.error("Error setting field {}: {}", field, e);
                }
            }
        }
    }

    private void setCamundaProcessInstanceId(Object entity, String processInstanceId) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ProcessInstanceId.class)) {
                try {
                    field.setAccessible(true);
                    field.set(entity, processInstanceId);
                } catch (IllegalAccessException e) {
                    log.error("Error setting field {}: {}", field, e);
                }
            }
        }
    }

}
