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
public class Guest {
    int guest_id;
    String guest_name;
//    其实是电话号码
    String guest_number;
    String guest_mail_address;

    @JsonIgnore
    String common_key;
}
