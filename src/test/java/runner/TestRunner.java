package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/funcionalTests",
		glue = "stepDefinitions",
		snippets = SnippetType.CAMELCASE,
		monochrome = true, 
		dryRun = false, 
		strict = true
		)
public class TestRunner {

}
