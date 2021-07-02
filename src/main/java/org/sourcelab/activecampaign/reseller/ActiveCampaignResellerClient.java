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
package org.sourcelab.activecampaign.reseller;

import org.sourcelab.activecampaign.AbstractClient;
import org.sourcelab.activecampaign.JacksonFactory;
import org.sourcelab.activecampaign.exception.InvalidCredentialsException;
import org.sourcelab.activecampaign.reseller.request.AccountAddRequest;
import org.sourcelab.activecampaign.reseller.request.AccountCancelRequest;
import org.sourcelab.activecampaign.reseller.request.AccountConversationsRequest;
import org.sourcelab.activecampaign.reseller.request.AccountCreditsApplyRequest;
import org.sourcelab.activecampaign.reseller.request.AccountEditRequest;
import org.sourcelab.activecampaign.reseller.request.AccountEmailTestCreditsApplyRequest;
import org.sourcelab.activecampaign.reseller.request.AccountListRequest;
import org.sourcelab.activecampaign.reseller.request.AccountNameCheckRequest;
import org.sourcelab.activecampaign.reseller.request.AccountPlansRequest;
import org.sourcelab.activecampaign.reseller.request.AccountScoringRequest;
import org.sourcelab.activecampaign.reseller.request.AccountStatusRequest;
import org.sourcelab.activecampaign.reseller.request.AccountStatusSetRequest;
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
import org.sourcelab.http.rest.RestResponse;

import java.io.IOException;

/**
 * Active Campaign Reseller API client.
 */
public class ActiveCampaignResellerClient extends AbstractClient {
    /**
     * Constructor.
     *
     * @param apiConfig API Configuration properties.
     */
    public ActiveCampaignResellerClient(final ResellerApiConfig apiConfig) {
        super(apiConfig);
    }

    /**
     * List all accounts under your reseller account.
     * @return List of accounts.
     */
    public AccountListResponse accountList(final AccountListRequest request) {
        return submitRequest(request);
    }

    /**
     * Check if the request account name is available.
     * @param account name of account to check.
     * @return Response.
     */
    public AccountNameCheckResponse accountNameCheck(final String account) {
        return submitRequest(new AccountNameCheckRequest(account));
    }

    public AccountPlansResponse accountPlans(final AccountPlansRequest request) {
        return submitRequest(request);
    }

    public AccountStatusResponse accountStatus(final String account) {
        return submitRequest(new AccountStatusRequest(account));
    }

    public AccountStatusSetResponse accountStatusSet(final AccountStatusSetRequest request) {
        return submitRequest(request);
    }

    public AccountScoringResponse accountScoring(final AccountScoringRequest request) {
        return submitRequest(request);
    }

    /**
     * Turn Conversations product on or off and updates purchased seats count.
     *
     * @param request defines the request.
     * @return AccountConversations Response.
     */
    public AccountConversationsResponse accountConversations(final AccountConversationsRequest request) {
        return submitRequest(request);
    }

    /**
     * Allows you to cancel an active account.
     *
     * @param request defines the request.
     * @return Account Cancel Response.
     */
    public AccountCancelResponse accountCancel(final AccountCancelRequest request) {
        return submitRequest(request);
    }

    /**
     * Add reseller's email sending credits to an account.
     *
     * @param request defines the request.
     * @return Account Credits Apply Response.
     */
    public AccountCreditsApplyResponse accountCreditsApply(final AccountCreditsApplyRequest request) {
        return submitRequest(request);
    }

    /**
     * Add reseller's email test sending credits to an account.
     *
     * @param request defines the request.
     * @return Account EmailTest Credits Response.
     */
    public AccountEmailTestCreditsApplyResponse accountEmailTestCreditsApply(final AccountEmailTestCreditsApplyRequest request) {
        return submitRequest(request);
    }

    /**
     * Edit existing account settings and plan, just like you would on the Manage Accounts page of the reseller panel.
     *
     * @param request defines the request.
     * @return AccountEdit Response.
     */
    public AccountEditResponse accountEdit(final AccountEditRequest request) {
        return submitRequest(request);
    }

    /**
     * Add a new account, just like you would on the Manage Accounts page of the reseller panel.
     *
     * @param request defines the request.
     * @return AccountAdd Response.
     */
    public AccountAddResponse accountAdd(final AccountAddRequest request) {
        return submitRequest(request);
    }

    @Override
    protected void validateResponseForInvalidCredentials(final RestResponse restResponse) {
        final String responseStr = restResponse.getResponseStr();

        // Poor man's attempt to parse for invalid api token response.
        try {
            final BaseResponse baseResponse = JacksonFactory.newInstance().readValue(responseStr, BaseResponse.class);
            final Integer code = baseResponse.getResultCode();
            final String msg = baseResponse.getResultMessage();
            if ((code != null && code.equals(0))
                && (msg != null && msg.startsWith("Authentication for API key '") && msg.endsWith("' failed."))
            ) {
                // Invalid credentials
                throw new InvalidCredentialsException("The ActiveCampaign API token and/or account name is not valid.", 0);
            }
        } catch (final IOException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }
}
