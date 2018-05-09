package com.example.demo.controller;

import com.example.demo.model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public CustomerController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public List<CustomerModel> getAll(){
         List<CustomerModel> customers = mongoTemplate.findAll(CustomerModel.class);
         return customers;
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public CustomerModel get(@PathVariable(name = "id") String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        CustomerModel customer = mongoTemplate.findOne(query,CustomerModel.class);
        return customer;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(@RequestBody CustomerModel customer){
        mongoTemplate.insert(customer);
        return "added! customer_id :"+customer.get_id();
    }

    @RequestMapping(value = "/remove/{id}",method = RequestMethod.DELETE)
    public String remove(@PathVariable(name = "id") String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query,CustomerModel.class);
        return "removed!";
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    public String update(@PathVariable(name = "id") String id,
                         @RequestBody CustomerModel customer){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        CustomerModel customer_update = mongoTemplate.findOne(query,CustomerModel.class);
        customer_update.setCust_fname(customer.getCust_fname());
        customer_update.setCust_lname(customer.getCust_lname());
        customer_update.setCust_email(customer.getCust_email());
        mongoTemplate.save(customer_update);
        return "updated!";
    }


}
