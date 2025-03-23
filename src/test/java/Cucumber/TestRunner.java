package Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "/Users/abhayverma/IdeaProjects/SeleniumFrameWork/src/test/java/Cucumber/SubmitOrder.feature", glue="StepDefinitions.StepDefinitionImpl"
,monochrome = true, plugin={"html: target/cucumber.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {



}
