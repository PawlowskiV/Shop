package com.pawlowskisolutions.ecommerce.config;

import com.pawlowskisolutions.ecommerce.entity.Product;
import com.pawlowskisolutions.ecommerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
        // Disable PUT, POST, DELETE for Product Repository
        config.getExposureConfiguration().forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) ->  httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) ->httpMethods.disable(theUnsupportedActions));
        // Disable PUT, POST, DELETE for Product Category Repository
        config.getExposureConfiguration().forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) ->  httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) ->httpMethods.disable(theUnsupportedActions));
        // Helper method
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // Expose entity id
        // First get list of all entity classes from manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        // Create array of those entity types
        List<Class> entityClasses = new ArrayList<>();
        // Get the types
        for (EntityType tempEntityType: entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }
        // Expose ids for entity
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}