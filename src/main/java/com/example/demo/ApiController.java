package com.example.demo;


import com.example.demo.model.Booking;
import com.example.demo.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ApiController {
    @Autowired
    private ApiService apiService;

    @PostMapping("/room")
    @ResponseBody
    public ResponseResult addRoom(@RequestBody Room room) {
        apiService.addRoom(room);
        return getResult(room);
    }

    @PostMapping("/booking")
    @ResponseBody
    public ResponseResult bookRoom(@RequestBody Booking bookingReq) {
        Booking bookingRes = apiService.booking(bookingReq);
        System.out.println(bookingReq.toString());
        if(bookingRes==null){
            return new ResponseResult(HttpStatus.SUCCESS,"booking failed");
        }
        return getResult(bookingRes);
    }

    @GetMapping("/room/{id}")
    @ResponseBody
    public ResponseResult getRoom(@PathVariable Integer id) {
        return getResult(apiService.getRoom(id));
    }

    @GetMapping("/room/")
    @ResponseBody
    public ResponseResult getRoomList() {
        return getResult(apiService.getRoomList());
    }

    @GetMapping("/booking/{id}")
    @ResponseBody
    public ResponseResult getBooking(@PathVariable Integer id) {
        return getResult(apiService.getBooking(id));
    }

    /**
     * 对象类型响应消息
     * @param data
     * @return
     */
    public ResponseResult getResult(Object data) {
        return ResponseResult.success(data);
    }
}
