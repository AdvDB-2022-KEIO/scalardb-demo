package com.example.demo;

import com.example.demo.exception.RepositoryConflictException;
import com.example.demo.exception.RepositoryCrudException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Booking;
import com.example.demo.model.Hotel;
import com.example.demo.model.Room;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.exception.transaction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
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

    public Room getRoom(Integer id) {
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
            Room room = apiRepository.getRoom(tx, id);
            tx.commit();
            return room;
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
        return null;
    }

    public List<Room> getRoomList() {
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
            List<Room> roomList = apiRepository.getRoomList(tx);
            tx.commit();
            return roomList;
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
        return null;
    }

    public List<Hotel> getHotelList() {
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
            List<Hotel> hotelList = apiRepository.getHotelList(tx);
            tx.commit();
            return hotelList;
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
        return null;
    }


    public Booking getBooking(Integer id) {
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
            Booking booking = apiRepository.getBooking(tx, id);
            tx.commit();
            return booking;
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
        return null;
    }

    public Booking booking(Booking bookingReq) {
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
            Booking bookingRes = apiRepository.booking(tx, bookingReq);
            tx.commit();
            return bookingRes;
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
        } catch (AbortException e) {
            try {
                System.out.println(" ####### abort");
                tx.abort();
                return null;
            } catch (AbortException ex) {
                throw new RuntimeException(ex);
            }
        } catch (CrudException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Booking> getBookingList() {
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
            List<Booking> bookingList = apiRepository.getBookingList(tx);
            tx.commit();
            return bookingList;
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
        return null;
    }
}
