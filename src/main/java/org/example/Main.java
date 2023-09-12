package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Main {
    static final int N = 1000;

    static final int MAX_NUMBER_OF_THREADS = 25;

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

    public static void main(String[] args) throws IOException {

        int [] NArray = new int[N];
        ArrayList<Long> TArray = new ArrayList<>();
        Arrays.setAll(NArray, operand -> operand);
        FileWriter file= new FileWriter("Exp" + N + "threads" + MAX_NUMBER_OF_THREADS + ".txt",true);
        BufferedWriter writer = new BufferedWriter(file);
        writer.write("Experiment with range 0 - " + N + ".\n" +
                "Experiment with " + (MAX_NUMBER_OF_THREADS) + " number of threads.\n");

        for (int numberOfThreads = 1; numberOfThreads <= MAX_NUMBER_OF_THREADS; numberOfThreads++) {
            int[][] chunks = splitArray(NArray, N / numberOfThreads);
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            List<Future<List<Integer>>> list = new ArrayList<>();
            var startThread = System.currentTimeMillis();
            for (int range = 0; range < Objects.requireNonNull(chunks).length; range++) {
                PrimeNumbers primeNumbers = new PrimeNumbers(chunks[range]);
                Future<List<Integer>> future = executor.submit(primeNumbers);
                list.add(future);
            }
            String currentText = "Number of threads " + numberOfThreads + ".\n";
            for (Future<List<Integer>> fut : list) {
                try {
                    currentText = currentText.concat("Prime numbers in range :: " + fut.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executor.shutdown();
            var timeThread = System.currentTimeMillis() - startThread;
            TArray.add(timeThread);
            currentText = currentText.concat("Execution time " + timeThread + ".\n");
            writer.write(currentText);
        }
        List<Integer> ThreadArray = IntStream.iterate(1, i -> i + 1)
                .limit(MAX_NUMBER_OF_THREADS)
                .boxed().toList();
        writer.write("Time Array: " + TArray + "\n");
        writer.write("Thread Array: " + ThreadArray + "\n");
        writer.close();
    }
}

