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
package com.thoughtmechanix.authentication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thoughtmechanix.authentication.model.Users;

/**
 * The interface for customizing queries to users table
 * 
 * <p>This interface is extending {@code CrudRepository}, so you MUST NOT 
 * implement the methods in this interface. The Spring JPA repository will 
 * automatically implement the basic functions (save, delete, findOne, etc.) 
 * in the parent {@code CrudRepository} interface and also implements your 
 * customized query functions defined in this interface based on the method 
 * names.
 * 
 * @see <a href="http://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.query-methods.query-creation">QueryCreation</a>
 * 
 * @author  Wuyi Chen
 * @date    06/26/2019
 * @version 1.0
 * @since   1.0
 */
@Repository
public interface UsersRepository extends CrudRepository<Users,String>  {
	public Users findByUsername(String username);
}
