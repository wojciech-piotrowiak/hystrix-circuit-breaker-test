package com.example.demo;

import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

public class HystrixTest {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            HystrixTest.TestConfiguration.class);
    HystrixService connectionService = context.getBean(HystrixService.class);

    @Test
    public void testCircuitBreaker() throws InterruptedException {
        HystrixCommandProperties.Setter()
                .withCircuitBreakerRequestVolumeThreshold(3);
        HystrixCommandProperties.Setter()
                .withCircuitBreakerEnabled(true);

        incorrectStep();
        incorrectStep();
        incorrectStep();
        incorrectStep();
        incorrectStep();
        System.out.println();
        System.out.println();
        System.out.println();

        final long l = System.currentTimeMillis();
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);
        correctStep(l);

        //wait more than resetTimeout
        System.out.println();
        System.out.println();
        System.out.println();
        Thread.sleep(21_000L);
        correctStep(l);

    }

    private void incorrectStep() throws InterruptedException {
        doFailedUpload(connectionService);
        Thread.sleep(1_000L);
        System.out.println();
    }

    private void correctStep(final long l) throws InterruptedException {
        doCorrectUpload(connectionService);
        Thread.sleep(1_000L);
        printTime(l);
    }

    private void printTime(final long l) {
        System.out.println(String.format("%d ms after last failure", (System.currentTimeMillis() - l)));
    }

    private void doFailedUpload(HystrixService externalService) throws InterruptedException {
        System.out.println("before fail");
        externalService.doUpload("FAIL");
        System.out.println("after fail");
        Thread.sleep(900);
    }

    private void doCorrectUpload(HystrixService externalService) throws InterruptedException {
        System.out.println("before ok");
        externalService.doUpload("");
        System.out.println("after ok");
        Thread.sleep(900);
    }

    @Configuration
    @EnableAspectJAutoProxy
    @EnableCircuitBreaker
    @EnableHystrixDashboard
    protected static class TestConfiguration {

        @Bean
        public HystrixService externalService() {
            return new HystrixService();
        }


    }

}
