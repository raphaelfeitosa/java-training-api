package br.com.training.v1.service.validation;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private String object;
    private String fieldName;

    @Override
    public void initialize(UniqueValue constraintValue) {
        object = constraintValue.domainClass().getSimpleName();
        fieldName = constraintValue.fieldName();
    }

    @Override
    @Transactional
    public boolean isValid(String uniqueValue, ConstraintValidatorContext context) {
        if (uniqueValue == null) {
            return false;
        }

        Query query = this.entityManager.createQuery("SELECT 1 FROM " + object + " WHERE " + fieldName + " =:VALUE");
        query.setParameter("VALUE", uniqueValue);

        return query.getResultList().isEmpty();
    }

}