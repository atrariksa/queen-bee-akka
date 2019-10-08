package com.queenbee.actors;

import java.util.concurrent.*;

public class Player {
    public static void main(String[] args) {
//        MasterActor masterActor = new MasterActor();
//        masterActor.testRun("masterActor.testRun");
        Player p = new Player();
        String[] messages = {"run slowly", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again", "sleep", "run again"};
        System.out.println("message number : " + messages.length);
        ExecutorService executor = Executors.newFixedThreadPool(1, new ThreadFactory() {
            int count = 1;
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "custom-executor-" + count++);
            }
        });
        for (int i = 0; i < 3; i++) {
            p.run(messages[i], executor);
        }
//        System.out.println("==================================");
//        System.out.println("==================================");
//        for (int i = 0; i < 5; i++) {
//            p.run(messages[i], executor);
//        }
//        executor.shutdown();
//        System.out.println("executor.isShutdown()" + executor.isShutdown());
//        System.out.println("executor.isTerminated()" + executor.isTerminated());
    }

//    private void run(String message, Executor executor) {
//        boolean doingTask = CompletableFuture.runAsync(()->{
//            String threadName = Thread.currentThread().getName();
//            System.out.println(threadName + " : " + message);
//            if (message.equals("sleep")) {
//                try {
//                    System.out.println(threadName + " is going to sleep for 10000ms");
//                    Thread.sleep(10000);
//                    System.out.println(threadName + " wakes up");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (message.equals("run slowly")) {
//                try {
//                    System.out.println(threadName + " is going to sleep for 5000ms");
//                    Thread.sleep(5000);
//                    System.out.println(threadName + " wakes up");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        },executor).whenComplete((act,th)->{
//            String threadName = Thread.currentThread().getName() + " in when complete ";
//            System.out.println(threadName + "success");
//        }).isDone();
//        String threadName = Thread.currentThread().getName() + " outside completable  ";
//        System.out.println(threadName + " isDone : " + doingTask);
//    }

    private void run(String message, Executor executor) {
        boolean doingTask = CompletableFuture.supplyAsync(()->{
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " : " + message);
            if (message.equals("sleep")) {
                try {
                    System.out.println(threadName + " is going to sleep for 10000ms");
                    Thread.sleep(10000);
                    System.out.println(threadName + " wakes up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (message.equals("run slowly")) {
                try {
                    System.out.println(threadName + " is going to sleep for 5000ms");
                    Thread.sleep(5000);
                    System.out.println(threadName + " wakes up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Object supply = new Object();
            return supply;
        },executor).whenComplete((act,th)->{
            String threadName = Thread.currentThread().getName() + " in when complete ";
            System.out.println(threadName + "success");
        }).isDone();
        String threadName = Thread.currentThread().getName() + " outside completable  ";
        System.out.println(threadName + " isDone : " + doingTask);
    }
}
