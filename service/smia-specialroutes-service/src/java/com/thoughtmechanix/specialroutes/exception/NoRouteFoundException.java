/*
 * Copyright 2019 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thoughtmechanix.specialroutes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The exception of not finding the route for a certain service.
 * 
 * @author  Wuyi Chen
 * @date    06/10/2019
 * @version 1.0
 * @since   1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRouteFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
}
