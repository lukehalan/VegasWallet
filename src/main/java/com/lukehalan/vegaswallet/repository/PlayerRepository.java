package com.lukehalan.vegaswallet.repository;

import com.lukehalan.vegaswallet.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {}
