package com.budget.envelope.envelopeapi.core;

import com.budget.envelope.envelopeapi.security.AuthHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvelopeStatisticsController {

    private final EnvelopeStatisticsService envelopeStatisticsService;

    public EnvelopeStatisticsController(EnvelopeStatisticsService envelopeStatisticsService) {
        this.envelopeStatisticsService = envelopeStatisticsService;
    }

    @GetMapping("/envelope/statistics/remaining")
    public Double getRemaining(@RequestHeader(name = "Authorization", required = true) String token) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        return envelopeStatisticsService.getRemaining(userId);
    }
}
