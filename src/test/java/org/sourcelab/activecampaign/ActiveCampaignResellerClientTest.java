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
import org.sourcelab.activecampaign.exception.InvalidCredentialsException;
import org.sourcelab.activecampaign.reseller.ActiveCampaignResellerClient;
import org.sourcelab.activecampaign.reseller.ResellerApiConfig;
import org.sourcelab.activecampaign.reseller.request.AccountAddRequest;
import org.sourcelab.activecampaign.reseller.request.AccountCancelRequest;
import org.sourcelab.activecampaign.reseller.request.AccountConversationsRequest;
import org.sourcelab.activecampaign.reseller.request.AccountCreditsApplyRequest;
import org.sourcelab.activecampaign.reseller.request.AccountEditRequest;
import org.sourcelab.activecampaign.reseller.request.AccountEmailTestCreditsApplyRequest;
import org.sourcelab.activecampaign.reseller.request.AccountListRequest;
import org.sourcelab.activecampaign.reseller.request.AccountPlansRequest;
import org.sourcelab.activecampaign.reseller.request.AccountScoringRequest;
import org.sourcelab.activecampaign.reseller.request.AccountStatusSetRequest;
import org.sourcelab.activecampaign.reseller.request.Language;
import org.sourcelab.activecampaign.reseller.response.AccountAddResponse;
import org.sourcelab.activecampaign.reseller.response.AccountCancelResponse;
import org.sourcelab.activecampaign.reseller.response.AccountConversationsResponse;
import org.sourcelab.activecampaign.reseller.response.AccountCreditsApplyResponse;
import org.sourcelab.activecampaign.reseller.response.AccountEditResponse;
import org.sourcelab.activecampaign.reseller.response.AccountEmailTestCreditsApplyResponse;
import org.sourcelab.activecampaign.reseller.response.AccountListResponse;
import org.sourcelab.activecampaign.reseller.response.AccountNameCheckResponse;
import org.sourcelab.activecampaign.reseller.response.AccountPlansResponse;
import org.sourcelab.activecampaign.reseller.response.AccountScoringResponse;
import org.sourcelab.activecampaign.reseller.response.AccountStatusResponse;
import org.sourcelab.activecampaign.reseller.response.AccountStatusSetResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test of Reseller API.
 */
@Tag("IntegrationTest")
class ActiveCampaignResellerClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ActiveCampaignClientTest.class);

    /**
     * Set during class bootstrap.
     */
    private static ResellerApiConfig apiConfig = null;
    private static ActiveCampaignResellerClient apiClient = null;

    @BeforeAll
    static void setup() throws IOException {
        final InputStream inputStream = ActiveCampaignClientTest.class.getClassLoader().getResourceAsStream("test_reseller_credentials.properties");

        // Load properties
        final Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();

        final String resellerApiToken = properties.getProperty("api_token");

        if (resellerApiToken == null) {
            throw new RuntimeException(
                "To run the integration tests you must create a resource file named test_reseller_credentials.properties which contains the properties:\n"
                + " api_token\n\n"
            );
        }

        apiConfig = new ResellerApiConfig(resellerApiToken);
        apiClient = new ActiveCampaignResellerClient(apiConfig);
    }

    /**
     * Cleanup.
     */
    @AfterAll
    static void tearDown() {
        if (apiClient != null) {
            apiClient.close();
            apiClient = null;
        }
    }

    /**
     * Test what happens if you use an invalid api token.
     */
    @Test
    void testInvalidApiToken() {
        final ActiveCampaignResellerClient badClient = new ActiveCampaignResellerClient(
            new ResellerApiConfig("not-a-real-token")
        );

        // This should throw an invalid credentials exception.
        assertThrows(InvalidCredentialsException.class, () -> badClient.accountNameCheck("Test123"));
    }

    /**
     * Test listing accounts.
     */
    @Test
    void testAccountList() {
        final AccountListResponse result = apiClient.accountList(new AccountListRequest());
        logger.info("Resp: {}", result);
    }

    /**
     * Test account name check.
     */
    @Test
    void testAccountNameCheck() {
        final AccountNameCheckResponse responseTaken = apiClient.accountNameCheck("bob");
        assertTrue(responseTaken.isTaken());
        assertFalse(responseTaken.isAvailable());

        final AccountNameCheckResponse responseAvailable = apiClient.accountNameCheck("bo123123213b" + System.currentTimeMillis());
        assertFalse(responseAvailable.isTaken());
        assertTrue(responseAvailable.isAvailable());
    }

    /**
     * Test listing accounts.
     */
    @Test
    void testAccountPlans() {
        final AccountPlansResponse result = apiClient.accountPlans(new AccountPlansRequest());
        logger.info("Resp: {}", result);
    }

    /**
     * Test getting account status.
     */
    @Test
    void testAccountStatus() {
        final AccountStatusResponse result = apiClient.accountStatus("effortlessdemo");
        logger.info("Resp: {}", result);
    }

    /**
     * Test listing accounts.
     */
    @Test
    void testAccountStatusSet() {
        final AccountStatusSetRequest request = new AccountStatusSetRequest()
            .withAccount("bob")
            .withActive()
            .withMessage("Test Message Here");

        final AccountStatusSetResponse result = apiClient.accountStatusSet(request);
        logger.info("Resp: {}", result);
    }

    /**
     * Test enabling/disabling account scoring.
     */
    @Test
    void testAccountScoring() {
        final AccountScoringRequest request = new AccountScoringRequest()
            .withAccount("bob")
            .withActive();

        final AccountScoringResponse result = apiClient.accountScoring(request);
        logger.info("Resp: {}", result);
    }

    /**
     * Test enabling/disabling account conversations feature.
     */
    @Test
    void testAccountConversations() {
        final AccountConversationsRequest request = new AccountConversationsRequest()
            .withAccount("bob")
            .withStatusTrial()
            .withSeats(1);

        final AccountConversationsResponse result = apiClient.accountConversations(request);
        logger.info("Resp: {}", result);
    }

    /**
     * Test enabling/disabling account conversations feature.
     */
    @Test
    void testAccountCancel() {
        final AccountCancelRequest request = new AccountCancelRequest()
            .withAccount("bob")
            .withRetainingData()
            .withReason("Cancelled");

        final AccountCancelResponse result = apiClient.accountCancel(request);
        logger.info("Resp: {}", result);
    }

    /**
     * Test enabling/disabling account conversations feature.
     */
    @Test
    void testAccountCreditsApply() {
        final AccountCreditsApplyRequest request = new AccountCreditsApplyRequest()
            .withAccount("bob")
            .withCredits(1);

        final AccountCreditsApplyResponse result = apiClient.accountCreditsApply(request);
        logger.info("Resp: {}", result);
    }

    /**
     * Test enabling/disabling account conversations feature.
     */
    @Test
    void testAccountEmailTestCreditsApply() {
        final AccountEmailTestCreditsApplyRequest request = new AccountEmailTestCreditsApplyRequest()
            .withAccount("bob")
            .withCredits(1);

        final AccountEmailTestCreditsApplyResponse result = apiClient.accountEmailTestCreditsApply(request);
        logger.info("Resp: {}", result);
    }

    /**
     * Test enabling/disabling account conversations feature.
     */
    @Test
    void testAccountEdit() {
        final AccountEditRequest request = new AccountEditRequest()
            .withAccount("bob")
            .withCname("mycname")
            .withNotification("test@example.com")
            .withPlan(-1);

        final AccountEditResponse result = apiClient.accountEdit(request);
        logger.info("Resp: {}", result);
    }

    /**
     * Test enabling/disabling account conversations feature.
     */
    @Test
    void testAccountAdd() {
        final AccountAddRequest request = new AccountAddRequest()
            .withAccount("bob")
            .withCname("mycname")
            .withPlan(123)
            .withNotification("test@example.com")
            .withLanguage(Language.ENGLISH)
            .withTimezone("America/Chicago");

        final AccountAddResponse result = apiClient.accountAdd(request);
        logger.info("Resp: {}", result);
    }
}