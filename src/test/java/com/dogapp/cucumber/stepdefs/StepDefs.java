package com.dogapp.cucumber.stepdefs;

import com.dogapp.DogappApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = DogappApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
