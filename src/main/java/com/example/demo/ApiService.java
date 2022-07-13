package com.example.demo;

import com.example.demo.exception.RepositoryConflictException;
import com.example.demo.exception.RepositoryCrudException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Room;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.exception.transaction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiService {

    @Autowired
    private DistributedTransactionManager manager;

    @Autowired
    private ApiRepository apiRepository;

    private DistributedTransaction tx;

    public void addRoom(Room room) {
        try {
            tx = manager.start();
        } catch (TransactionException e) {
            try {
                tx.abort();
            } catch (AbortException ex) {
            }
            throw new ServiceException("An error occurred when adding a group", e);
        }
        try {
            apiRepository.addRoom(tx, room);
            tx.commit();
        } catch (CommitConflictException | RepositoryConflictException e) {
            try {
                tx.abort();
            } catch (AbortException ex) {
            }
        } catch (CommitException | RepositoryCrudException | UnknownTransactionStatusException e) {
            try {
                tx.abort();
            } catch (AbortException ex) {
            }
            throw new ServiceException("An error occurred when adding a user", (Throwable) e);
        }
    }
}
