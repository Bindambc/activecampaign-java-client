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
import org.sourcelab.activecampaign.reseller.response.AccountEditResponse;
import org.sourcelab.http.rest.request.RequestMethod;

import java.io.IOException;

/**
 * Edit existing account settings and plan, just like you would on the Manage Accounts page of the reseller panel.
 */
public class AccountEditRequest extends AbstractRequest<AccountEditRequest, AccountEditResponse> {

    public AccountEditRequest() {
        super("account_edit");
    }

    public AccountEditRequest withAccount(final String account) {
        return setParam("account", account);
    }

    public AccountEditRequest withCname(final String cname) {
        return setParam("cname", cname);
    }

    public AccountEditRequest withNotification(final String emailAddress) {
        return setParam("notification", emailAddress);
    }

    public AccountEditRequest withPlan(final int planId) {
        return setParam("plan", Integer.toString(planId));
    }

    @Override
    public AccountEditResponse parseResponse(final String response) throws IOException {
        return JacksonFactory.newInstance().readValue(response, AccountEditResponse.class);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}

