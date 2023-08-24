package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    static final int N = 10000;

    static final int MAX_NUMBER_OF_THREADS = 50;

    public static int[][] splitArray(int[] arrayToSplit, int chunkSize) {
        if (chunkSize <= 0) {
            return null;
        }
        int rest = arrayToSplit.length % chunkSize;
        int chunks = arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0);
        int[][] arrays = new int[chunks][];
        for (int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++) {
            arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
        }
        if (rest > 0) {
            arrays[chunks - 1] = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
        }
        return arrays;
    }

    public static void main(String[] args) {
        int[] NArray = new int[N];
        Arrays.setAll(NArray, operand -> operand);
        for (int numberOfThreads = 1; numberOfThreads < MAX_NUMBER_OF_THREADS; numberOfThreads++) {
            int[][] chunks = splitArray(NArray, N / numberOfThreads);
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            List<Future<List<Integer>>> list = new ArrayList<>();
            var start = System.currentTimeMillis();
            for (int range = 0; range < Objects.requireNonNull(chunks).length; range++) {
                PrimeNumbers primeNumbers = new PrimeNumbers(chunks[range]);
                Future<List<Integer>> future = executor.submit(primeNumbers);
                list.add(future);
            }
            System.out.println("Execution time is: " + (System.currentTimeMillis() - start));
            for (Future<List<Integer>> fut : list) {
                try {
                    System.out.println("Prime numbers in range::" + fut.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executor.shutdown();
        }
    }
}

