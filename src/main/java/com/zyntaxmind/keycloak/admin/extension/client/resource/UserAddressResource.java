/*******************************************************************************
 *    Copyright 2023  the original author.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package com.zyntaxmind.keycloak.admin.extension.client.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.zyntaxmind.keycloak.admin.extension.model.representation.UserAddressRepresentation;

/**
 * @author dush
 *
 */
public interface UserAddressResource {
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  UserAddressRepresentation toRepresentation();
  
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  Response update(UserAddressRepresentation userAddressRepresentation);

}
