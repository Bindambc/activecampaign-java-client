/**
 * Copyright 2019, 2020, 2021 SourceLab.org https://github.com/SourceLabOrg/activecampaign-java-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.sourcelab.activecampaign;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.activecampaign.apiv1.ActiveCampaignApiV1Client;
import org.sourcelab.activecampaign.apiv1.ApiV1Config;
import org.sourcelab.activecampaign.apiv1.response.user.UsersMeResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration test of client API.
 */
@Tag("IntegrationTest")
class ActiveCampaignV1ClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ActiveCampaignV1ClientTest.class);

    private static ActiveCampaignApiV1Client apiV1Client = null;

    @BeforeAll
    static void setup() throws IOException {
        final InputStream inputStream = ActiveCampaignV1ClientTest.class.getClassLoader().getResourceAsStream("test_credentials.properties");

        // Load properties
        final Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();

        final String activeCampaignAccountName = properties.getProperty("account_name");
        final String activeCampaignUsername = properties.getProperty("username");
        final String activeCampaignPassword = properties.getProperty("password");

        if (activeCampaignUsername == null || activeCampaignPassword == null) {
            throw new RuntimeException(
                "To run the integration tests you must create a resource file named test_credentials.properties which contains the properties:\n"
                + " account_name \n"
                + " username\n"
                + " password\n\n"
            );
        }

        /**
         * Set during class bootstrap.
         */
        ApiV1Config apiConfig = new ApiV1Config(activeCampaignAccountName, activeCampaignUsername, activeCampaignPassword);
        apiV1Client = new ActiveCampaignApiV1Client(apiConfig);
    }

    /**
     * Cleanup.
     */
    @AfterAll
    static void tearDown() {
        if (apiV1Client != null) {
            apiV1Client.close();
            apiV1Client = null;
        }
    }

    /**
     * Smoke tests our API configuration is valid.
     */
    @Test
    void smokeTest() {
        //final boolean isLoginValid = apiV3Client.loginTest();
        //assertTrue(isLoginValid, "Response should be true.");
    }

    /**
     * Smoke test about me end point.
     */
    @Test
    void testAboutMe() {
        final UsersMeResponse response = apiV1Client.usersMe();
        logger.info("V1: {}", response);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getUsername());
        assertNotNull(response.getApiKey());
        assertNotNull(response.getApiUrl());
    }
}