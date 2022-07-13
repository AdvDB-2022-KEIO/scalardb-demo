package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Room {

    int room_id;
    int room_number;
//    1可预订，0被定出
    int room_status;
    int hotel_id;
}
