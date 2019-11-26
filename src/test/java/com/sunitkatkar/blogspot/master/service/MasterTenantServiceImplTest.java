package com.sunitkatkar.blogspot.master.service;

import com.sunitkatkar.blogspot.master.config.MasterDatabaseConfig;
import com.sunitkatkar.blogspot.master.config.MasterDatabaseConfigProperties;
import com.sunitkatkar.blogspot.master.model.MasterTenant;
import com.sunitkatkar.blogspot.master.repository.MasterTenantRepository;
import com.sunitkatkar.blogspot.tenant.config.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.sunitkatkar.blogspot.tenant.config.TenantDatabaseConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories("com.sunitkatkar.blogspot")
@EnableConfigurationProperties
@ActiveProfiles(profiles = "test")
public class MasterTenantServiceImplTest {

    @Autowired
    MasterTenantRepository masterTenantRepository;

    @Autowired
    MasterTenantService masterTenantService;

    @Before
    public void setup(){
        assertThat(masterTenantRepository.findAll().isEmpty(),is(false));
    }


    @Test
    public void findTenant() {
        MasterTenant tenant1 = masterTenantService.findByTenantId("tenant1");
        assertNotNull(tenant1);
        assertThat(tenant1.getId(),is(1L));
    }

    @Test
    public void addTenant() {
        MasterTenant masterTenant = new MasterTenant();
        masterTenant.setId(2L);
        masterTenant.setPassword("password");
        masterTenant.setTenantId("tenant2");
        masterTenant.setUsername("tenant2");
        masterTenant.setUrl("jdbc:h2:mem:db/tenant2");
        masterTenant.setVersion(0);
        masterTenantService.addTenant(masterTenant);
        assertThat(masterTenantRepository.findAll().size(),is(2));
        MasterTenant tenant2 = masterTenantRepository.findByTenantId("tenant2");
        assertThat(tenant2.getId(),is(2L));
    }


}
