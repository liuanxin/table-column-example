package com.github.example;

import com.github.liuanxin.query.core.TableColumnTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WebApplication.class)
public class TableColumnExampleTest {

    @Autowired
    private TableColumnTemplate tableColumnTemplate;

    @Test
    public void generateModel() {
        String targetPath = "/home/admin/temp";
        String packagePath = "com.github.example.model";
        tableColumnTemplate.generateModel(targetPath, packagePath);
    }
}
