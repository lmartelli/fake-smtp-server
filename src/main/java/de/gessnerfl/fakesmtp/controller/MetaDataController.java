package de.gessnerfl.fakesmtp.controller;

import de.gessnerfl.fakesmtp.config.WebappAuthenticationProperties;
import de.gessnerfl.fakesmtp.model.ApplicationMetaData;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meta-data")
public class MetaDataController {

    private final BuildProperties buildProperties;
    private final WebappAuthenticationProperties authProperties;

    public MetaDataController(BuildProperties buildProperties, WebappAuthenticationProperties authProperties) {
        this.buildProperties = buildProperties;
        this.authProperties = authProperties;
    }

    @GetMapping
    public ApplicationMetaData get() {
        return new ApplicationMetaData(buildProperties.getVersion(), authProperties.isAuthenticationEnabled());
    }

}
