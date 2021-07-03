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
package org.sourcelab.activecampaign.apiv3.request.contact;

import org.junit.jupiter.api.Test;
import org.sourcelab.activecampaign.apiv3.request.AbstractRequestTest;
import org.sourcelab.activecampaign.apiv3.request.account.AccountRetrieveRequest;
import org.sourcelab.activecampaign.apiv3.response.account.Account;
import org.sourcelab.activecampaign.apiv3.response.contact.ContactRetrieveResponse;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContactRetrieveRequestTest extends AbstractRequestTest {
    /**
     * Test parsing a mocked response.
     */
    @Test
    void testParsingResponse() throws IOException {
        final String input = readFile("getContact.json");
        final ContactRetrieveResponse response = new ContactRetrieveRequest(1L)
            .parseResponse(input);


        // Parse and validate contact
        final Contact contact = response.getContact();
        assertEquals(1L, contact.getId());
        assertEquals("Test_FirstName", contact.getFirstName());
        assertEquals("Test_LastName", contact.getLastName());
        assertEquals("contact.email@example.com", contact.getEmail());
        assertEquals("123-123-1234", contact.getPhone());

        // Parse and validate links
    }
}