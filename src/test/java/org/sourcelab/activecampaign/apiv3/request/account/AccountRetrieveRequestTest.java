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

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountRetrieveRequestTest extends AbstractRequestTest {
    /**
     * Test parsing a mocked response.
     */
    @Test
    void testParsingResponse() throws IOException {
        final String input = readFile("accountsRetrieve.json");
        final Account account = new AccountRetrieveRequest(1L)
            .parseResponse(input)
            .getAccount();

        assertEquals(1L, account.getId());
        assertEquals("Example Account", account.getName());
        assertEquals("https://www.example.com", account.getAccountUrl());
        assertEquals(0L, account.getDealCount());
        assertEquals(0L, account.getContactCount());

        // 2019-12-02T04:45:17-06:00
        assertNotNull(account.getCreatedTimestamp());
        assertEquals(ZonedDateTime.parse("2019-05-15T15:58:16-05:00"), account.getCreatedTimestamp());

        // 2019-03-02T04:55:22-06:00
        assertNotNull(account.getUpdatedTimestamp());
        assertEquals(ZonedDateTime.parse("2019-05-15T15:58:16-05:00"), account.getUpdatedTimestamp());

        // Validate links
        assertEquals(0,account.getLinks().size());
    }
}