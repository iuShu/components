package org.iushu.jdk.thread;

import org.iushu.jdk.Utils;

import java.util.Random;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * CompletableFuture (expands Future)
 *
 * @see java.util.concurrent.CompletableFuture since 1.8
 * @see ForkJoinPool
 *
 * @author iuShu
 * @since 6/2/21
 */
public class CompletableFutureCase {

    static CompletableFuture completableFuture(boolean supplier) {
        if (supplier)
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("supplier begin");
                Utils.sleep(2000);
                System.out.println("supplier end");
                return "Done";
            });
        return CompletableFuture.runAsync(() -> {
            System.out.println("runnable begin");
            Utils.sleep(2000);
            System.out.println("runnable end");
        });
    }

    /**
     * @see CompletableFuture#get()
     * @see CompletableFuture#get(long, TimeUnit)
     * @see CompletableFuture#getNow(Object) return result if finished or default value
     */
    static void simpleCase() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            Utils.sleep(2000);
            completableFuture.complete("task finished");
        }).start();
        try {
            System.out.println("task's request dispatched");
            String result = completableFuture.get();    // block forever unless task completed
            System.out.println("task completed: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see Runnable#run()
     * @see CompletableFuture#runAsync(Runnable)
     * @see CompletableFuture#runAsync(Runnable, Executor)
     *
     * @see Supplier#get()
     * @see CompletableFuture#supplyAsync(Supplier)
     * @see CompletableFuture#supplyAsync(Supplier, Executor)
     */
    static void demonstration() {
        CompletableFuture runnableFuture = completableFuture(false);
        CompletableFuture supplierFuture = completableFuture(true);
        try {
            runnableFuture.get();                   // Runnable have no return

            Object result = supplierFuture.get();   // Supplier have return
            System.out.println("supplier finished: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * serial task
     *
     * @see CompletableFuture#thenRun(Runnable)
     * @see CompletableFuture#thenRunAsync(Runnable)
     * @see CompletableFuture#thenRunAsync(Runnable, Executor)
     *
     * @see CompletableFuture#thenAccept(Consumer)
     * @see CompletableFuture#thenAcceptAsync(Consumer)
     * @see CompletableFuture#thenAcceptAsync(Consumer, Executor)
     *
     * @see CompletableFuture#thenApply(Function)
     * @see CompletableFuture#thenApplyAsync(Function)
     * @see CompletableFuture#thenApplyAsync(Function, Executor)
     *
     * @see CompletableFuture#thenCompose(Function)
     * @see CompletableFuture#thenComposeAsync(Function)
     * @see CompletableFuture#thenComposeAsync(Function, Executor)
     */
    static void serialTasks() {
        CompletableFuture runnableFuture = completableFuture(false)
                .thenRun(() -> System.out.println("thenRun received: "));
        CompletableFuture acceptFuture = completableFuture(true)
                .thenAccept(obj -> System.out.println("thenAccept: " + obj));
        CompletableFuture supplierFuture = completableFuture(true)
                .thenApply(obj -> {
                    System.out.println("thenApply received: " + obj);
                    return obj + " then apply";
                });
        CompletableFuture composeFuture = completableFuture(true)
                .thenCompose(obj -> {
                    System.out.println("thenCompose: " + obj);
                    return CompletableFuture.supplyAsync(() -> {
                        Utils.sleep(500);
                        System.out.println("CompletionStage: " + obj);
                        return obj + " then compose";
                    });
                });

        try {
            runnableFuture.get();

            Object result = supplierFuture.get();
            System.out.println("thenApply finished: " + result);

            acceptFuture.get();

            result = composeFuture.get();
            System.out.println("thenCompose finished: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * aggregation AND
     *
     * combine two task
     * @see CompletableFuture#thenCombine(CompletionStage, BiFunction)
     * @see CompletableFuture#thenCombineAsync(CompletionStage, BiFunction)
     * @see CompletableFuture#thenCombineAsync(CompletionStage, BiFunction, Executor)
     *
     * combine multiple task
     * @see CompletableFuture#allOf(CompletableFuture[]) waiting all task finished
     * @see CompletableFuture#anyOf(CompletableFuture[]) shutdown if one task finished
     *
     * @see CompletableFuture#thenAcceptBoth(CompletionStage, BiConsumer)
     * @see CompletableFuture#thenAcceptBothAsync(CompletionStage, BiConsumer)
     * @see CompletableFuture#thenAcceptBothAsync(CompletionStage, BiConsumer, Executor)
     *
     * @see CompletableFuture#runAfterBoth(CompletionStage, Runnable)
     * @see CompletableFuture#runAfterBothAsync(CompletionStage, Runnable)
     * @see CompletableFuture#runAfterBothAsync(CompletionStage, Runnable, Executor)
     */
    static void aggregationAndTasks() {
        CompletableFuture<Double> weightFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("fetching weight data");
            Utils.sleep(1500);
            System.out.println("weight: " + 66.38d);
            return 66.38d;
        });
        CompletableFuture<Double> heightFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("fetching height data");
            Utils.sleep(2700);
            System.out.println("height: " + 178.50d);
            return 178.50d;
        });
        CompletableFuture<Double> ageFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("fetching age data");
            Utils.sleep(800);
            System.out.println("age: " + 25.0d);
            return 25.0d;
        });
        CompletableFuture<Double> genderFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("fetching gender data");
            Utils.sleep(800);
            System.out.println("gender: " + 1d);
            return 1d;
        });

        // combine two task
//        CompletableFuture<Double> combinedFuture = weightFuture.thenCombine(heightFuture, (w, h) -> {
//            System.out.println("calculating BMI(Body Mass Index)...");
//            Utils.sleep(800);
//            return w / ((h / 100) * (h / 100));
//        });
//        CompletableFuture<Void> acceptBothFuture = weightFuture.thenAcceptBoth(heightFuture, (w, h) -> {
//            System.out.println("storing BMI(Body Mass Index)...");
//            Utils.sleep(800);   // store to database
//            System.out.println("BMI data stored");
//        });
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(weightFuture, heightFuture, ageFuture, genderFuture);
//        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(weightFuture, heightFuture, ageFuture, genderFuture);

        try {
//            Double bmi = combinedFuture.get();
//            System.out.println("calculated result: " + bmi);

//            acceptBothFuture.get();
            allOfFuture.get();

//            Object anyoneResult = anyOfFuture.get();
//            System.out.println("fetching over: " + anyoneResult);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * aggregation OR
     *
     * @see CompletableFuture#runAfterEither(CompletionStage, Runnable)
     * @see CompletableFuture#runAfterEitherAsync(CompletionStage, Runnable)
     * @see CompletableFuture#runAfterEitherAsync(CompletionStage, Runnable, Executor)
     *
     * @see CompletableFuture#acceptEither(CompletionStage, Consumer)
     * @see CompletableFuture#acceptEitherAsync(CompletionStage, Consumer)
     * @see CompletableFuture#acceptEitherAsync(CompletionStage, Consumer, Executor)
     *
     * @see CompletableFuture#applyToEither(CompletionStage, Function)
     * @see CompletableFuture#applyToEitherAsync(CompletionStage, Function)
     * @see CompletableFuture#applyToEitherAsync(CompletionStage, Function, Executor)
     */
    static void aggregationOrTasks() {
        CompletableFuture<String> zookeeper = CompletableFuture.supplyAsync(() -> {
            System.out.println("connect to zookeeper");
            Utils.sleep(810);
            System.out.println("get data: order-2181");
            return "order-2181";
        });
        CompletableFuture<String> redis = CompletableFuture.supplyAsync(() -> {
            System.out.println("connect to redis");
            Utils.sleep(800);
            System.out.println("get redis data: order-6379");
            return "order-6379";
        });
        CompletableFuture<String> future = zookeeper.applyToEither(redis, obj -> {
            System.out.println("future: " + obj);
            return obj + " apply to either";
        });

        try {
            Object result = future.get();
            System.out.println("result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see CompletableFuture#exceptionally(Function) active if exception throwed
     * @see CompletableFuture#handle(BiFunction) like 'finally' in try-catch
     */
    static void exceptionHandleCase() {
        CompletableFuture<String> supplier = CompletableFuture.supplyAsync(() -> {
            System.out.println("task begin");
            Utils.sleep(600);
            if (new Random().nextInt(2) > 0)
                throw new IllegalStateException("unknown state");
            System.out.println("task end");
            return "28dnPm^zG-5672&15672&25672";
        });
//        CompletableFuture<String> exception = supplier.exceptionally(e -> {
//            System.out.println("error in task: " + e.getMessage());
//            return "exception-default";
//        });
        CompletableFuture<String> handler = supplier.handle((val, e) -> {
            if (e == null) {
                System.out.println("everything is ok");
                return val;
            }
            System.out.println("catch error: " + e.getMessage());
            return "handler-default";
        });

        try {
//            String result = exception.get();
//            System.out.println("result: " + result);

            String handled = handler.get();
            System.out.println("result: " + handled);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        simpleCase();
//        demonstration();
//        serialTasks();
//        aggregationAndTasks();
//        aggregationOrTasks();
        exceptionHandleCase();
    }

}
