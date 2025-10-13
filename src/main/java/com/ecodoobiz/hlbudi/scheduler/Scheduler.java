package com.ecodoobiz.hlbudi.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.ecodoobiz.hlbudi.service.LotteTokenService;

import java.util.Date;

@Component
public class Scheduler {
    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private final LotteTokenService lotteTokenService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void iamLive() {

        logger.info("I am live: {}", new Date());
    }

    public Scheduler(LotteTokenService lotteTokenService) {
        this.lotteTokenService = lotteTokenService;
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void fetchLotteToken() {
        try {
//            String accessToken = lotteTokenService.getAccessToken();
//            logger.info("Successfully fetched Lotte access token: {}", accessToken);
            // 여기에서 발급받은 accessToken을 사용하여 롯데택배 API를 호출하는 로직을 추가할 수 있습니다.
        } catch (Exception e) {
            logger.error("Failed to fetch Lotte access token", e);
        }
    }
}
