package com.lukehalan.vegaswallet.controller;


import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.model.request.PlayerRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface IPlayerController {

    @GetMapping("/info/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    Player getPlayer(@PathVariable Integer id);

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    Player createPlayer(@RequestBody PlayerRequest player);

}
