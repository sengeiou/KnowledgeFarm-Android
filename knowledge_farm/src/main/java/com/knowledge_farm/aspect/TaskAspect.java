package com.knowledge_farm.aspect;

import com.knowledge_farm.entity.Task;
import com.knowledge_farm.entity.User;
import com.knowledge_farm.task.service.TaskService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;

@Component
@Aspect
public class TaskAspect {

    @Pointcut("@annotation(com.knowledge_farm.annotation.Task)")
    public  void taskAspect() {}
    @Resource
    private TaskService taskService;

    /**
     * @Author 景光赞
     * @param joinPoint
     * @return java.lang.Object
     * @Description 每日任务完成
     * @Date 11:58 2020/4/17
     **/
    @Around("taskAspect()")
    public Object encode(ProceedingJoinPoint joinPoint) throws ParseException {
        String description = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(com.knowledge_farm.annotation.Task.class).description();
        System.out.println("*****Encode Start*****");
        Object[] args = joinPoint.getArgs();
        Object result = null;
        try {
            result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        User user = (User) args[0];
        Task task = user.getTask();
        System.out.println("1");
        if (description.equals("water")&&task.getWater()==0){
            taskService.finishTask(user,"water");
        }else if(description.equals("fertilize")&&task.getFertilize()==0){
            taskService.finishTask(user,"fertilize");
        }else if(description.equals("crop")&&task.getCrop()==0){
            taskService.finishTask(user,"crop");
        }else if(description.equals("harvest")&&task.getHarvest()==0){
            taskService.finishTask(user,"harvest");
        }else if(description.equals("help_water")&&task.getHelpWater()==0){
            taskService.finishTask(user,"help_water");
        }else if(description.equals("help_fertilize")&&task.getHelpFertilize()==0){
            taskService.finishTask(user,"help_fertilize");
        }
        System.out.println("2");
        System.out.println("*****Encode End*****");
        return result;
    }

}