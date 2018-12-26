package com.sunso.lab.framework.concurrent.future.demo;

import java.util.concurrent.*;

/**
 * @Title:Cook
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午4:43
 * @m444@126.com
 */
public class Cook {
    //private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void cook() {
        FutureTask<Water> waterTask = doWater();
        FutureTask<Rich> richTash = doRich();
        FutureTask<Food> foodTask = doFood();
        try {
            Water water = waterTask.get();
            Rich rich = richTash.get();
            Food food = foodTask.get();
            System.out.println("cook ok....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private FutureTask<Water> doWater() {
        WaterCall waterCall = new WaterCall();
        FutureTask waterTask = new FutureTask(waterCall);
        //executorService.submit(waterTask);
        runFutureTask(waterTask);
        return waterTask;
    }

    private FutureTask<Rich> doRich() {
        RichCall richCall = new RichCall();
        FutureTask richTash = new FutureTask(richCall);
        //executorService.submit(richCall);
        runFutureTask(richTash);
        return richTash;
    }

    private FutureTask<Food> doFood() {
        FoodCall foodCall = new FoodCall();
        FutureTask foodTask = new FutureTask(foodCall);
        //executorService.submit(foodTask);
        runFutureTask(foodTask);
        return foodTask;
    }

    private void runFutureTask(FutureTask futureTask) {
        new Thread(futureTask).start();
    }

    public static void main(String[] args) {
        new Cook().cook();
    }
}
