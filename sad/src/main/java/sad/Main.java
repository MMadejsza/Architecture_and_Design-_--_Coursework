package sad;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.template.JadeTemplate;
import io.javalin.Javalin;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import sad.CoreManagementSystem.Database;
import sad.CoreManagementSystem.IRetrieveData;
import sad.Customer_Manager.Customer;
import sad.Graph_Manager.Graph;
import sad.Graph_Manager.IRetrieveGraph;
import sad.StocksInfo_Manager.Stocks;

public class Main {

  public static void main(String[] args) {
    Stocks stocksManager = new Stocks();
    Database.usersList.add(new Customer("a", "a"));
    Database databaseManager = new Database();
    // Create a new Javalin instance
    Javalin app = Javalin
      .create(config -> {
        // Specify the directory to serve static files from
        String resourcePath = getResourcePath();
        config.staticFiles.add(resourcePath);

        // Specify the location of CSS and JavaScript files
        config.staticFiles.add(resourcePath + "/css");
        config.staticFiles.add(resourcePath + "/js");
      })
      .start(3001);

    // Define a route to handle the button click
    // app.get("/", ctx -> ctx.html(getFileContent("index.html")));
    // app.get("/login", ctx -> ctx.html(getFileContent("login.html")));
    // app.get("/portfolio", ctx -> ctx.html(getFileContent("portfolio.html")));
    // app.get("/about", ctx -> ctx.html(getFileContent("about.html")));

    // app.get("/", ctx -> ctx.result(getFileContent("index.pug")));
    // app.get("/login", ctx -> ctx.result(getFileContent("login.pug")));
    // app.get("/portfolio", ctx -> ctx.result(getFileContent("portfolio.pug")));
    app.get("/about", ctx -> ctx.result(getFileContent("about.pug")));

    app.get(
      "/fetchedStocks",
      ctx -> {
        // Get the startDate and endDate query parameters from the frontend to use in yahoo stocks call
        String name = ctx.queryParam("name");
        String startDate = ctx.queryParam("startDate");
        String endDate = ctx.queryParam("endDate");
        // Sample data

        //Make the function selfcontaining, and when the stock is called produce the data
        String test = stocksManager.StocksInfo("AAPL");

        ctx.contentType("application/json").result(test);
      }
    );

    app.get(
      "/loginCredentials",
      ctx -> {
        // Get the startDate and endDate query parameters from the frontend to use in yahoo stocks call
        String login = ctx.queryParam("name");
        String password = ctx.queryParam("password");
        // Sample data

        //Make the function selfcontaining, and when the stock is called produce the data
        boolean test = databaseManager.checkUser(login, password);
        // Create a JSON object representing the result
        // Construct JSON string representing the result
        String resultJson = "{\"authorized\": " + test + "}";

        ctx.contentType("application/json").result(resultJson);
      }
    );
  }

  // Method to read file content as a String
  private static String getFileContent(String fileName) {
    try {
      String resourceDir = getResourcePath();
      JadeConfiguration config = new JadeConfiguration();
      config.setPrettyPrint(true); // Optional: Makes the generated HTML readable

      // Load the Pug template file
      JadeTemplate template = config.getTemplate(
        new File(resourceDir, fileName).getPath()
      );

      // Render the template with an empty model (if no model is needed)
      Map<String, Object> model = new HashMap<>();
      return config.renderTemplate(template, model); // Render Pug template
    } catch (Exception e) {
      e.printStackTrace();
      return "Error loading file";
    }
  }

  // Method to get the absolute path to the resources directory
  public static String getResourcePath() {
    try {
      String resourcePath =
        Main.class.getClassLoader().getResource("resources").getPath();
      System.out.println("Resource path: " + resourcePath);
      return resourcePath;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}