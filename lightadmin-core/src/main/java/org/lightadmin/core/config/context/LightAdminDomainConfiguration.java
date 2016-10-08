/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lightadmin.core.config.context;

import org.lightadmin.api.config.management.rmi.DataManipulationService;
import org.lightadmin.api.config.management.rmi.GlobalConfigurationManagementService;
import org.lightadmin.core.config.domain.GlobalAdministrationConfiguration;
import org.lightadmin.core.config.management.rmi.DataManipulationServiceImpl;
import org.lightadmin.core.config.management.rmi.GlobalConfigurationManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import javax.sql.DataSource;

@Configuration
public class LightAdminDomainConfiguration {

    @Bean
    @Autowired
    public GlobalConfigurationManagementService globalConfigurationManagementService(GlobalAdministrationConfiguration globalAdministrationConfiguration, RepositoryRestConfiguration repositoryRestConfiguration) {
        return new GlobalConfigurationManagementServiceImpl(globalAdministrationConfiguration, repositoryRestConfiguration);
    }

    @Bean
    @Autowired
    public DataManipulationService dataManipulationService(DataSource dataSource) {
        return new DataManipulationServiceImpl(dataSource);
    }
}