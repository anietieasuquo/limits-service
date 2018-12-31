package com.qooria.banksticks.limitsservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qooria.banksticks.limitsservice.Configuration;
import com.qooria.banksticks.limitsservice.bean.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration getLimitsFromConfiguration() {
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}

    @GetMapping("/limits-fault")
    @HystrixCommand(fallbackMethod = "fallbackGetLimits")
    public LimitConfiguration getLimits() {
        throw new RuntimeException("Not available");
    }

    public LimitConfiguration fallbackGetLimits() {
        return new LimitConfiguration(0, 0);
    }
}
