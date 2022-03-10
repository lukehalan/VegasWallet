package com.lukehalan.vegaswallet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukehalan.vegaswallet.config.JacksonConfiguration;
import com.lukehalan.vegaswallet.repository.PlayerRepository;
import com.lukehalan.vegaswallet.repository.TransactionHistoryRepository;
import com.lukehalan.vegaswallet.repository.WalletRepository;
import com.lukehalan.vegaswallet.service.impl.PlayerService;
import com.lukehalan.vegaswallet.util.JsonUtil;
import java.io.IOException;
import org.junit.Before;
import org.mockito.Mock;

public class GenericServiceUtil {

    protected ObjectMapper objectMapper = new JacksonConfiguration().objectMapper();
    protected final Integer PLAYER_ID = 1;

    @Mock protected PlayerRepository playerRepository;
    @Mock protected WalletRepository walletRepository;
    @Mock protected TransactionHistoryRepository transactionRepository;

    protected PlayerService playerService;

    @Before
    public void setup() {
        playerService = new PlayerService(playerRepository,walletRepository,transactionRepository);
    }

    public <T> T buildResponse(String filename, Class<T> t) throws IOException {
        String jsonNode = JsonUtil.readJsonFile(filename);
        return objectMapper.readValue(jsonNode, t);
    }
}
