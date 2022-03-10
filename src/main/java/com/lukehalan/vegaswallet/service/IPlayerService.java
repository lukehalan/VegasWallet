package com.lukehalan.vegaswallet.service;

import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.model.request.PlayerRequest;

public interface IPlayerService {

    Player find(Integer id);
    Player save(PlayerRequest player);
}
