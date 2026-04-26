package TestComponents;

import PageObject.LandingPage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.github.dockerjava.core.DockerClientConfig;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.epam.healenium.*;

public class BaseTest {
    public SelfHealingDriver driver;
    public LandingPage page;

    public SelfHealingDriver intializeDriver() throws IOException, InterruptedException {
        // 1️⃣ Set up Docker client configuration
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();

        // 2️⃣ Use the Zero-dependency HTTP client
        DockerHttpClient httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())  // Auto-detects Docker daemon
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        // 3️⃣ Create Docker Client
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);

        // 4️⃣ Start Existing Containers (Without Pulling)
        System.out.println("🔄 Checking and starting existing Docker containers...");

        startContainerIfNotRunning(dockerClient, "postgres-db", "postgres:latest");
        startContainerIfNotRunning(dockerClient, "healenium", "healenium/healenium:latest");
        startContainerIfNotRunning(dockerClient, "selector-imitator", "healenium/selector-imitator:latest");

        System.out.println("✅ All required containers are running!");

        // 5️⃣ Wait for Healenium to be Ready
        System.out.println("⏳ Waiting for Healenium to be ready...");
        waitForContainer(dockerClient, "healenium");
        waitForContainer(dockerClient, "postgres-db");
        waitForContainer(dockerClient, "selector-imitator");

        WebDriver delegate = null;
        //Setting Global Properties

        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/Resources/GlobalData.properties");
        properties.load(fis);


        //This code decide from where you want your browser data
        //System.getProperty(""); take variable input from terminal as well

        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");


        //Logic to Select Browser
        //     String browser = properties.getProperty("browser");
        if (browser.contains("Chrome")) {  //To Run in Headless Mode
            ChromeOptions options = new ChromeOptions();
            if (browser.contains("headless")) {
                options.addArguments("headless");
            }
            delegate = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
            delegate = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("edge")) {
            delegate = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("safari")) {
            delegate = new SafariDriver();

        }
        driver = SelfHealingDriver.create(delegate);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;


    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenShot = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
        File file = new File(dest);
        FileUtils.copyFile(file, screenShot);
        return dest;
    }



    // Helper method to check and start a container if it is not running
    private void startContainerIfNotRunning(DockerClient dockerClient, String containerName, String imageName) {
        boolean isRunning = dockerClient.listContainersCmd().withShowAll(true).exec().stream()
                .anyMatch(container -> container.getNames()[0].equals("/" + containerName) && container.getState().equalsIgnoreCase("running"));

        boolean exists = dockerClient.listContainersCmd().withShowAll(true).exec().stream()
                .anyMatch(container -> container.getNames()[0].equals("/" + containerName));

        if (isRunning) {
            System.out.println("✅ Container '" + containerName + "' is already running.");
        } else if (exists) {
            System.out.println("▶️ Starting existing container: " + containerName);
            dockerClient.startContainerCmd(containerName).exec();
        } else {
            System.out.println("🚀 Creating and starting new container: " + containerName);
            CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                    .withName(containerName)
                    .withHostConfig(HostConfig.newHostConfig().withAutoRemove(true)) // Auto-remove after stop
                    .exec();
            dockerClient.startContainerCmd(container.getId()).exec();
        }
    }

    // Helper method to wait until a container is fully running
    private void waitForContainer(DockerClient dockerClient, String containerName) throws InterruptedException {
        for (int i = 0; i < 10; i++) { // Retry for ~50 seconds
            Thread.sleep(5000); // Wait 5 seconds
            boolean isRunning = dockerClient.listContainersCmd().exec().stream()
                    .anyMatch(container -> container.getNames()[0].equals("/" + containerName));
            if (isRunning) {
                System.out.println("✅ " + containerName + " is running.");
                return;
            }
        }
        throw new RuntimeException("❌ " + containerName + " failed to start.");
    }








    //Converting Json to Hash Map
    public List<HashMap<String, String>> getJsonDataToMap(String FilePath) throws IOException {
        //This is inside the common.io dependency
        //UTF 8 is Standard to Convert JSON file to String
        String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir") + FilePath),
                StandardCharsets.UTF_8);

        //To Convert String into HashMap Jackson DataBind Dependency is needed
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;


    }

    @BeforeMethod(alwaysRun = true) //This method send driver information to the page object classes
    // This is used to avoid while running for specific groups
    public LandingPage launchApplication() throws IOException, InterruptedException {
        driver = intializeDriver();
        page = new LandingPage(driver);
        page.goTo();
        return page;

    }

    @AfterMethod(alwaysRun = true)   //This is used to avoid while running for specific groups
    public void tearDown() {
        driver.quit();
    }


}
