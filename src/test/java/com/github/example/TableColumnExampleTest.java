package com.github.example;

import com.github.liuanxin.query.core.TableColumnTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

@SpringBootTest(classes = WebApplication.class)
public class TableColumnExampleTest {

    @Autowired
    private TableColumnTemplate tableColumnTemplate;

    @Test
    public void generateModel() {
        URL url = TableColumnExampleTest.class.getClassLoader().getResource("");
        if (url != null) {
            File file = new File(url.getFile());
            Path path = file.getParentFile().getParentFile().toPath();
            String targetPath = path + "/src/main/java";
            String packagePath = "com.github.example.model";
            tableColumnTemplate.generateModel(targetPath, packagePath);
        }
    }
}
