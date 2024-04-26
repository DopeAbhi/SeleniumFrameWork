package TestComponents;

import PageObject.LandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
   public WebDriver driver;
    public WebDriver intializeDriver() throws IOException {

        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Resources/GlobalData.properties");
        properties.load(fis);
        String browser = properties.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
             driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

        else if (browser.equalsIgnoreCase("firefox"))
        {
             driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
        else if (browser.equalsIgnoreCase("edge"))
        {
         driver = new EdgeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

        else if (browser.equalsIgnoreCase("safari"))
        {
             driver = new SafariDriver();

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;



    }
    public  LandingPage launchApplication() throws IOException {
        driver=intializeDriver();
        LandingPage page = new LandingPage(driver);
        page.goTo();
        return page;

    }


}
