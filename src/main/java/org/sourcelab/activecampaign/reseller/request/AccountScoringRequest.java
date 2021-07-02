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
package org.sourcelab.activecampaign.reseller.request;

import org.sourcelab.activecampaign.JacksonFactory;
import org.sourcelab.activecampaign.reseller.response.AccountScoringResponse;
import org.sourcelab.http.rest.request.RequestMethod;

import java.io.IOException;

/**
 * Account Scoring Request.
 */
public class AccountScoringRequest extends AbstractRequest<AccountScoringRequest, AccountScoringResponse> {

    public AccountScoringRequest() {
        super("account_scoring");
    }

    public AccountScoringRequest withAccount(final String account) {
        return setParam("account", account);
    }

    public AccountScoringRequest withActive() {
        return withStatus(RequestedStatus.ACTIVE);
    }

    public AccountScoringRequest withInactive() {
        return withStatus(RequestedStatus.INACTIVE);
    }

    public AccountScoringRequest withStatus(final RequestedStatus status) {
        if (RequestedStatus.INACTIVE.equals(status)) {
            // 0 == inactive
            return setParam("status", "0");
        } else {
            // 1 == active
            return setParam("status", "1");
        }
    }

    @Override
    public AccountScoringResponse parseResponse(final String response) throws IOException {
        return JacksonFactory.newInstance().readValue(response, AccountScoringResponse.class);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
