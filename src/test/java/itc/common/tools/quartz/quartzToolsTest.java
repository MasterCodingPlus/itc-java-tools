package itc.common.tools.quartz;

import org.junit.Test;
import org.quartz.JobDetail;

public class quartzToolsTest {

    @Test
    public void createJob() throws InterruptedException {
        final JobDetail job1 = QuartzTools.createJob("123", MyJob.class, "0/2 * * * * ? *");
        final JobDetail job2 = QuartzTools.createJob("1233", MyJob1.class, "0/5 * * * * ? ");
//        final JobDetail job3 = QuartzTools.createJob("1234", MyJob.class, "0 * * * * ? *");

        Thread.sleep(60000);
        QuartzTools.delJob("1233");
        Thread.currentThread().join();
    }

    @Test
    public void getJob() {
    }

    @Test
    public void getTrigger() {
    }

    @Test
    public void createJob1() {
    }

    @Test
    public void createJob2() {
    }
}