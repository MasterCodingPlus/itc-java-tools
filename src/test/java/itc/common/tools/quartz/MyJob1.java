package itc.common.tools.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.quartz
 * @ClassName: MyJob
 * @Description:
 * @Author: Mastercoding
 * @CreateDate: 2019/5/31 21:52
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/5/31 21:52
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class MyJob1 implements Job {
    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.err.println(new Date() + "job1");
    }
}
