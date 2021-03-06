/**
 * Copyright 2011-2012 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.bank.web.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.excilys.ebi.bank.model.entity.User;
import com.excilys.ebi.bank.model.entity.ref.Role;
import com.excilys.ebi.bank.model.entity.ref.RoleRef;

public class SecurityUtils {

	private SecurityUtils() {
		throw new UnsupportedOperationException();
	}

	public static User getCurrentUser() {
		if (isAuthenticated()) {
			return CustomUserDetails.class.cast(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		}
		return null;
	}

	public static boolean isAuthenticated() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		return ctx != null //
				&& ctx.getAuthentication() != null //
				&& ctx.getAuthentication().isAuthenticated();
	}

	public static boolean isWithRole(Role role) {

		if (isAuthenticated()) {
			for (RoleRef roleRef : getCurrentUser().getRoles()) {
				if (roleRef.getId() == role) {
					return true;
				}
			}
		}

		return false;
	}
}
