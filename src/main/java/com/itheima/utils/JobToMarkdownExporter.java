package com.itheima.utils;

import com.itheima.mapper.JobMapper;
import com.itheima.pojo.Job;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

@Component

public class JobToMarkdownExporter implements CommandLineRunner {

    private final JobMapper jobMapper;

    public JobToMarkdownExporter(JobMapper jobMapper) {
        this.jobMapper = jobMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 查询所有有效兼职
        List<Job> jobList = jobMapper.selectAll();

        // 2. 创建目录
        String path = "src/main/resources/content/";
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();

        // 3. 生成 jobs.md
        File file = new File(dir, "jobs.md");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("# 兼职信息知识库\n\n");

            for (Job job : jobList) {
                String md = """
                    ## %s
                    - 工作内容：%s
                    - 薪资：%s
                    - 工作地点：%s
                    - 招聘人数：%s

                    """.formatted(
                        job.getTitle(),
                        job.getContent(),
                        job.getSalary(),
                        job.getAddress(),
                        job.getNum()
                );
                writer.write(md);
            }
            System.out.println("✅ 知识库已生成：" + file.getAbsolutePath());
        }
    }
}