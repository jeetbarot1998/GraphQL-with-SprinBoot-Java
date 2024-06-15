package com.API.GraphQL.Controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class helloController {

    private List<Customer> customerList = List.of(
            new Customer(1,"A"),
            new Customer(2, "B"));

    @QueryMapping
    String hello(){
        return "Hello World";
    }

    @QueryMapping
    String helloWithName(@Argument String name){
        return "Hello, " + name;
    }

    @QueryMapping
    Collection<Customer> customers(){
        return this.customerList;
    }

//    For Each customer, this schema mapping is being called
//    @SchemaMapping(typeName = "Customer")
//    Account account(Customer customer){
////        This is being called for every customer.
////        It is in-efficient as there will be multiple calls
////        Supposing there is a DB call and instead of saying
////        Select * from Accounts where account id in (1,2,3)
////        It will make multiple calls lik
////        Select * from Accounts where account id = 1, then
////        Select * from Accounts where account id = 2, then
////        Select * from Accounts where account id = 3
////        This way it becomes slow and in-efficient
////        To improve this we have batch requests
//        System.out.println("Calling " + customer.id());
//        return new Account(customer.id());
//    }

    @BatchMapping
    Map<Customer, Account> account (List<Customer> customers){
        System.out.println("Calling account for " + customers);
        return customers.stream().collect(Collectors.toMap(
                customer -> customer, customer ->  new Account(customer.id())
        ));
    }


}

record Customer(Integer id, String name){}
record Account(Integer id){}