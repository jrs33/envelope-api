package com.budget.envelope.envelopeapi.core;

import com.budget.envelope.envelopeapi.security.AuthHelper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EnvelopeController {

    // TODO: need auth
    static final long HARD_CODED_USER_ID = 1L;

    private final EnvelopeService envelopeService;

    public EnvelopeController(EnvelopeService envelopeService) {
        this.envelopeService = envelopeService;
    }

    @GetMapping("/envelopes")
    public List<Envelope> getEnvelopes(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(value = "from") long from
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        return envelopeService.getEnvelopes(userId, from, 10);
    }

    @GetMapping("/envelope")
    public Envelope getEnvelope(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(value = "id") long id
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        Optional<Envelope> envelopeOptional = envelopeService.findEnvelope(userId, id);
        if(!envelopeOptional.isPresent()) {
            throw new IllegalArgumentException("invalid_id");
        }
        return envelopeOptional.get();
    }

    @PostMapping(path = "/envelope/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEnvelope(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Envelope envelope
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        int numRowsChanged = envelopeService.createEnvelope(userId, envelope);
        if(numRowsChanged == 0) {
            throw new RuntimeException("error_creating_envelope");
        }
    }
}
