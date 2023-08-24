package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class PrimeNumbers implements Callable<List<Integer>> {
    final int[] range;

    public PrimeNumbers(int[] range) {
        this.range = range;
    }

    public List<Integer> findPrimesInRange() {
        List<Integer> primes = new ArrayList<>();
        Arrays.stream(range).forEach(
                i -> {
                    if (i > 1) {
                        if (IntStream.range(2, i).noneMatch(n -> i % n == 0)) {
                            primes.add(i);
                        }
                    }
                }
        );
        return primes;
    }

    @Override
    public List<Integer> call() throws Exception {
        return findPrimesInRange();
    }
}
