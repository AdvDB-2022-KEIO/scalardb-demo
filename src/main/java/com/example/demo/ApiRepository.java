package com.example.demo;

import com.example.demo.exception.RepositoryConflictException;
import com.example.demo.exception.RepositoryCrudException;
import com.example.demo.model.Room;
import com.scalar.db.api.*;
import com.scalar.db.exception.transaction.CrudConflictException;
import com.scalar.db.exception.transaction.CrudException;
import com.scalar.db.io.Key;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ApiRepository {

    public void addRoom(DistributedTransaction tx, Room room) {
//        try {
//            Key pk = createPk(userId);
//            Delete delete = new Delete(pk)
//                    .forNamespace(NAMESPACE)
//                    .forTable(TABLE_NAME);
//            tx.delete(delete);
//        } catch (CrudConflictException e) {
//            throw new RepositoryConflictException(e.getMessage(), e);
//        } catch (CrudException e) {
//            throw new RepositoryCrudException("Deleting User failed", e);
//        }

        try {
            System.out.println("############################################"+room.toString());
            Key pk = new Key("room_id", room.getRoom_id());
            Put put = new Put(pk)
                    .forNamespace("hotel")
                    .forTable("room")
//                    .withValue("room_id",room.getRoom_id())
                    .withValue("room_status",room.getRoom_status())
                    .withValue("room_number",room.getRoom_number())
                    .withValue("hotel_id",room.getHotel_id());
            tx.put(put);
        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }
}
