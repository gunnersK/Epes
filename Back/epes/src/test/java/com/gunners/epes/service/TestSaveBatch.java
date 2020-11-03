package com.gunners.epes.service;

import com.gunners.epes.entity.TaskEva;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSaveBatch {

    @Autowired
    ITaskEvaService taskEvaService;

    @Test
    public void aa(){
        List<TaskEva> list = Lists.newArrayList(
                new TaskEva().setTaskId(1).setEmpId("1"),
                new TaskEva().setTaskId(2).setEmpId("2")
        );
        taskEvaService.saveBatch(list);
        return;
    }
}
