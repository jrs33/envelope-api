package com.budget.envelope.envelopeapi.core;

import com.budget.envelope.envelopeapi.security.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SourceController {

    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping(path = "/sources")
    public List<Source> getSources(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(value = "from", defaultValue = "0") long from
    ) {
        String userId = AuthHelper.getUserIdFromHeader(token);
        return sourceService.getSources(userId, from, 10);
    }

    @PostMapping(path = "/source/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Source createSource(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Source source
    ) throws SourceException {
        String userId = AuthHelper.getUserIdFromHeader(token);
        source.setUserId(userId);
        return sourceService.createSource(source);
    }
}
