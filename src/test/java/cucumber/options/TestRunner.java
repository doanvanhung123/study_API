package cucumber.options;
import io.cucumber.junit.Cucumber;
import  io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/java/cucumber/feature",
        plugin="json:target/jsonReports/cucumber-report.json",
        glue = {"cucumber.stepDefinitions"}
)
public class TestRunner {
}
