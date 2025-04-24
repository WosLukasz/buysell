package com.wosarch.buysell.admin.model.synchronizations;

public interface Synchronization {

    void run(SynchronizationItem synchronizationItem);

    void failed(SynchronizationItem synchronizationItem);

    void failed(SynchronizationItem synchronizationItem, Exception e);

    void log(SynchronizationItem synchronizationItem, String message);

    void success(SynchronizationItem synchronizationItem);

    String getCode();
}
