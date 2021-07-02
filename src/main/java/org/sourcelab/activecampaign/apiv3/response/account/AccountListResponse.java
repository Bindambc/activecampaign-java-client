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
package org.sourcelab.activecampaign.apiv3.response.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.sourcelab.activecampaign.apiv3.response.Meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the response returned from the accounts list api resource.
 */
public class AccountListResponse {
    private final List<Account> accounts;
    private final Meta meta;

    /**
     * Constructor.
     */
    @JsonCreator
    public AccountListResponse(
        @JsonProperty("accounts") final List<Account> accounts,
        @JsonProperty("meta") final Meta meta
    ) {
        if (accounts == null) {
            this.accounts = Collections.emptyList();
        } else {
            this.accounts = Collections.unmodifiableList(new ArrayList<>(accounts));
        }
        this.meta = meta;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Meta getMeta() {
        return meta;
    }

    @Override
    public String toString() {
        return "AccountListResponse{"
            + "accounts=" + accounts
            + ", meta=" + meta
            + '}';
    }
}
