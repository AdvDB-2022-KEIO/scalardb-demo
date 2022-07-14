package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    1可预订，0被房间故障，是否可预订根据booking表里是否有这个时间段被预定出去了来确定
    int room_status;
    int hotel_id;

    @JsonIgnore
    String common_key;
}
