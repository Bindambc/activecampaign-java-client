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
package org.sourcelab.activecampaign.apiv3;

import org.sourcelab.activecampaign.AbstractClient;
import org.sourcelab.activecampaign.apiv3.request.LoginRequest;
import org.sourcelab.activecampaign.apiv3.request.account.AccountCreateRequest;
import org.sourcelab.activecampaign.apiv3.request.account.AccountDeleteRequest;
import org.sourcelab.activecampaign.apiv3.request.account.AccountListRequest;
import org.sourcelab.activecampaign.apiv3.request.account.AccountRetrieveRequest;
import org.sourcelab.activecampaign.apiv3.request.account.AccountUpdateRequest;
import org.sourcelab.activecampaign.apiv3.request.contact.Contact;
import org.sourcelab.activecampaign.apiv3.request.contact.ContactCreateRequest;
import org.sourcelab.activecampaign.apiv3.request.contact.ContactRetrieveRequest;
import org.sourcelab.activecampaign.apiv3.request.contactList.ContactListSubscribeRequest;
import org.sourcelab.activecampaign.apiv3.request.contactTag.ContactTag;
import org.sourcelab.activecampaign.apiv3.request.contactTag.ContactTagCreateRequest;
import org.sourcelab.activecampaign.apiv3.request.contactTag.ContactTagDeleteRequest;
import org.sourcelab.activecampaign.apiv3.request.customField.CustomFieldListRequest;
import org.sourcelab.activecampaign.apiv3.request.list.ListListRequest;
import org.sourcelab.activecampaign.apiv3.request.tag.TagCreateRequest;
import org.sourcelab.activecampaign.apiv3.request.tag.TagListRequest;
import org.sourcelab.activecampaign.apiv3.request.user.UsersMeRequest;
import org.sourcelab.activecampaign.apiv3.response.account.Account;
import org.sourcelab.activecampaign.apiv3.response.account.AccountListResponse;
import org.sourcelab.activecampaign.apiv3.response.account.AccountResponse;
import org.sourcelab.activecampaign.apiv3.response.contact.ContactCreateResponse;
import org.sourcelab.activecampaign.apiv3.response.contact.ContactRetrieveResponse;
import org.sourcelab.activecampaign.apiv3.response.contactTag.ContactTagCreateResponse;
import org.sourcelab.activecampaign.apiv3.response.contactTag.ContactTagDeleteResponse;
import org.sourcelab.activecampaign.apiv3.response.customField.CustomFieldListResponse;
import org.sourcelab.activecampaign.apiv3.response.list.ListListResponse;
import org.sourcelab.activecampaign.apiv3.response.tag.TagCreateResponse;
import org.sourcelab.activecampaign.apiv3.response.tag.TagListResponse;
import org.sourcelab.activecampaign.apiv3.response.user.UsersMeResponse;
import org.sourcelab.activecampaign.exception.InvalidCredentialsException;
import org.sourcelab.http.rest.RestResponse;

/**
 * ActiveCampaign API client.
 */
public class ActiveCampaignClient extends AbstractClient {

    /**
     * Constructor.
     * @param apiConfig API Configuration properties.
     */
    public ActiveCampaignClient(final ApiConfig apiConfig) {
        super(apiConfig);
    }

    /**
     * Method to validate if the APi credentials provided are valid.
     *
     * @return true if the credentials are accepted, false if not.
     */
    public boolean loginTest() {
        try {
            return submitRequest(new LoginRequest());
        } catch (final InvalidCredentialsException exception) {
            return false;
        }
    }

    /**
     * Retrieve the logged-in user
     * @return details about the currently logged in user.
     */
    public UsersMeResponse usersMe() {
        return submitRequest(new UsersMeRequest());
    }

    /**
     * Retrieve all existing accounts.
     * @return all accounts.
     */
    public AccountListResponse accountList() {
        return submitRequest(new AccountListRequest());
    }

    public AccountResponse accountsRetrieve(final Long id) {
        return submitRequest(new AccountRetrieveRequest(id));

    }

    /**
     * Create a new Account.
     * @param account the account to create.
     * @return Account create response.
     */
    public AccountResponse accountCreate(final Account account) {
        return submitRequest(new AccountCreateRequest(account));
    }

    /**
     * Update an existing account instance.
     * @param account defines the account to update.
     * @return Account update response.
     */
    public AccountResponse accountUpdate(final Account account) {
        return submitRequest(new AccountUpdateRequest(account));
    }

    /**
     * Update an existing account instance.
     * @param id defines the account to delete.
     * @return Account update response.
     */
    public boolean accountDelete(final long id) {
        return submitRequest(new AccountDeleteRequest(id));
    }

    /**
     * Update an existing account instance.
     * @param account defines the account to delete.
     * @return Account update response.
     */
    public boolean accountDelete(final Account account) {
        return submitRequest(new AccountDeleteRequest(account));
    }

    public ContactCreateResponse contactCreate(final Contact contactToCreate) {
        return submitRequest(new ContactCreateRequest(contactToCreate));
    }

    public ContactCreateResponse contactSync(final Contact contactToCreate) {
        return submitRequest(new ContactCreateRequest(contactToCreate));
    }

    public ContactRetrieveResponse contactRetrieve(final long contactId) {
        return submitRequest(new ContactRetrieveRequest(contactId));
    }

    public TagListResponse tagList() {
        return submitRequest(new TagListRequest());
    }

    public TagCreateResponse tagCreate(final TagCreateRequest request) {
        return submitRequest(request);
    }

    public ContactTagCreateResponse contactTagCreate(final ContactTag contactTag) {
        return submitRequest(new ContactTagCreateRequest(contactTag));
    }

    public ContactTagDeleteResponse contactTagDelete(final ContactTag contactTag) {
        return submitRequest(new ContactTagDeleteRequest(contactTag));
    }

    /**
     * Retrieve all Custom Fields defined.
     * @return Custom Field List Response.
     */
    public CustomFieldListResponse customFieldList() {
        return submitRequest(new CustomFieldListRequest());
    }

    public ListListResponse listList() {
        return submitRequest(new ListListRequest());
    }

    public String contactList(final ContactListSubscribeRequest contactListSubscribeRequest) {
        return submitRequest(contactListSubscribeRequest);
    }

    @Override
    protected void validateResponseForInvalidCredentials(final RestResponse restResponse) {
        // Not needed.
    }
}
