package com.wosarch.buysell.common.services.parallel;

import com.wosarch.buysell.common.model.exception.BuysellException;
import com.wosarch.buysell.common.model.exception.BuysellExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;

public class CompletionIterator<T> implements Iterator<T>, AutoCloseable {

    Logger logger = LoggerFactory.getLogger(CompletionIterator.class);

    private CompletionService<T> completionService;

    private ExecutorService executorService;

    private final Queue<Future<T>> tasks;

    private final List<Throwable> errors;

    private int maxAwaitTimeout = 6;

    private TimeUnit maxAwaitTimeoutUnit = TimeUnit.HOURS;

    public CompletionIterator(int threadsPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadsPoolSize);
        this.completionService = new ExecutorCompletionService<>(this.executorService);
        this.tasks = new ConcurrentLinkedDeque<>();
        this.errors = new ArrayList<>();
    }

    public CompletionIterator(int threadsPoolSize, int maxAwaitTimeout, TimeUnit maxAwaitTimeoutUnit) {
        this.executorService = Executors.newFixedThreadPool(threadsPoolSize);
        this.completionService = new ExecutorCompletionService<>(this.executorService);
        this.tasks = new ConcurrentLinkedDeque<>();
        this.errors = new ArrayList<>();
        this.maxAwaitTimeout = maxAwaitTimeout;
        this.maxAwaitTimeoutUnit = maxAwaitTimeoutUnit;
    }

    public void submit(Callable<T> task) {
        tasks.add(completionService.submit(task));
    }

    @Override
    public void close() throws Exception {
        try {
            executorService.shutdown();
            executorService.awaitTermination(this.maxAwaitTimeout, this.maxAwaitTimeoutUnit);
            executorService = null;
            completionService = null;
            this.tasks.clear();
        } catch (Exception e) {
            throw new BuysellException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return !CollectionUtils.isEmpty(tasks);
    }

    @Override
    public T next() {
        if (CollectionUtils.isEmpty(tasks)) {
            throw new NoSuchElementException();
        }

        try {
            Future<T> task = tasks.poll();
            if (Objects.isNull(task)) {
                return null;
            }

            return task.get();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted");

            return null;
        } catch (Exception e) {
            throw new BuysellException(e);
        }
    }

    public void waitWithoutResponses() {
        processResponses(false);
    }

    public Optional<List<T>> waitAndGetResponse() {
        return processResponses(true);
    }

    private Optional<List<T>> processResponses(boolean gatherResponses) {
        List<T> responses = new ArrayList<>();

        while (hasNext()) {
            try {
                T finished = next();
                logger.debug("Task returned object: {}", finished);
                if (gatherResponses) {
                    responses.add(finished);
                }
            } catch (Exception e) {
                logger.error("Error occurred while task processing", e);
                errors.add(e);
            }
        }

        if (!CollectionUtils.isEmpty(errors)) {
            throw new BuysellExceptions("Error occurred while task processing", errors);
        }

        if (!gatherResponses) {
            return Optional.empty();
        }

        return Optional.of(responses);

    }
}
