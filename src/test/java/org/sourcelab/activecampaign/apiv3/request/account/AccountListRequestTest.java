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
package org.sourcelab.activecampaign.apiv3.request.account;

import org.junit.jupiter.api.Test;
import org.sourcelab.activecampaign.apiv3.request.AbstractRequestTest;
import org.sourcelab.activecampaign.apiv3.response.account.Account;
import org.sourcelab.activecampaign.apiv3.response.account.AccountListResponse;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountListRequestTest extends AbstractRequestTest {

    /**
     * Test parsing a mocked response.
     */
    @Test
    void testParsingResponse() throws IOException {
        final String input = readFile("accountsList.json");
        final AccountListResponse parsed = new AccountListRequest()
            .parseResponse(input);

        // Validate accounts
        assertEquals(1, parsed.getAccounts().size(), "Should have a single account");

        final Account account = parsed.getAccounts().get(0);
        assertEquals(1L, account.getId());
        assertEquals("My Account Name 1575283515866", account.getName());
        assertEquals("https://www.test.com/blah", account.getAccountUrl());
        assertEquals(0L, account.getDealCount());
        assertEquals(0L, account.getContactCount());

        // 2019-12-02T04:45:17-06:00
        assertNotNull(account.getCreatedTimestamp());
        assertEquals(ZonedDateTime.parse("2019-12-02T04:45:17-06:00"), account.getCreatedTimestamp());

        // 2019-03-02T04:55:22-06:00
        assertNotNull(account.getUpdatedTimestamp());
        assertEquals(ZonedDateTime.parse("2019-03-02T04:55:22-06:00"), account.getUpdatedTimestamp());

        // Validate links
        assertEquals(5,account.getLinks().size());
        assertEquals("https://accountName.api-us1.com/api/3/accounts/1/notes", account.getLinks().get("notes"));
        assertEquals("https://accountName.api-us1.com/api/3/accounts/1/accountCustomFieldData", account.getLinks().get("accountCustomFieldData"));
        assertEquals("https://accountName.api-us1.com/api/3/accounts/1/accountContacts", account.getLinks().get("accountContacts"));
        assertEquals("https://accountName.api-us1.com/api/3/accounts/1/emailActivities", account.getLinks().get("emailActivities"));
        assertEquals("https://accountName.api-us1.com/api/3/accounts/1/contactEmails", account.getLinks().get("contactEmails"));

        // Validate meta
        assertEquals(1, parsed.getMeta().getTotal(), "Should have a single result");
    }
}