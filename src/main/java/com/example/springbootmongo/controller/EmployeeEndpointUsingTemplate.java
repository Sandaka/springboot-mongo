package com.example.springbootmongo.controller;

import com.example.springbootmongo.entity.EmployeeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/template")
public class EmployeeEndpointUsingTemplate {

    /*
    here we do not use the repository.
    uses only MongoTemplate.
    also we can use the layered architecture with MongoTemplate.
     */

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MongoTemplate mongoTemplate;

    public EmployeeEndpointUsingTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<EmployeeModel> getAllUsers() {
        logger.info("Getting all Employees.");
        return mongoTemplate.findAll(EmployeeModel.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EmployeeModel getEmployee(@PathVariable long id) {
        logger.info("Getting Employee with ID: {}.", id);

        /*EmployeeModel employeeModel = mongoTemplate.findById(id, EmployeeModel.class);
        return employeeModel;*/

        /* https://www.journaldev.com/18156/spring-boot-mongodb */
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, EmployeeModel.class);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public EmployeeModel add(@RequestBody EmployeeModel employeeModel) {
        logger.info("Saving Employee.");
        return mongoTemplate.save(employeeModel);
    }
}
