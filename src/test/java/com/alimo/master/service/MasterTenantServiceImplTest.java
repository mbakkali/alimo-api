package com.alimo.master.service;

import com.alimo.master.model.MasterTenant;
import com.alimo.master.repository.MasterTenantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories("com.alimo")
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
