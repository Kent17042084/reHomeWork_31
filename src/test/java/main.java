import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {

        System.setProperty("webdriver.chrome.driver", "/Users/user/IdeaProjects/reHomeWork_27/chromedriver");
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\IdeaProjects\\HomeTask29\\Files\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = (new WebDriverWait(driver, 10));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Открыть https://rozetka.com.ua/
        driver.get("https://rozetka.com.ua");
    }
    @Test
    public void test() throws IOException {

        //Перейти в раздел «Компьютеры и ноутбуки»
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Ноутбуки и компьютеры")).click();

        //Перейти в раздел «Ноутбуки»
        wait = (new WebDriverWait(driver, 10));
        WebElement notebooks = driver.findElement(By.xpath("//img[@alt='Ноутбуки']"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", notebooks);


        WebElement title = driver.findElement(By.xpath("//a[@class='goods-tile__heading']"));
        String titleCatalog = title.getAttribute("innerText");
        //System.out.println(titleCatalog);

        //Добавить первый товар в корзину
        driver.findElement(By.xpath("//button[@class='buy-button goods-tile__buy-button']")).click();

        //Проверить что в корзину добавился один товар

        WebElement GoodsCounter = driver.findElement(By.xpath("//span[@class='header-actions__button-counter']"));
        String count = GoodsCounter.getText();
        Assert.assertEquals(count,"1","В корзине более одного товара");

        //Перейти в корзину и проверить, что добавился правильный товар
        driver.findElement(By.xpath("//a[@class='header-actions__button header-actions__button_type_basket header-actions__button_state_active']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/single-modal-window/div[2]/div[2]/rz-shopping-cart/div/div[1]/a")));
        WebElement RecycleBinGoodTitle = driver.findElement(By.xpath("/html/body/app-root/single-modal-window/div[2]/div[2]/rz-shopping-cart/div/ul/li/rz-cart-product/div/div[1]/div[2]/a"));
        String goodTitle = RecycleBinGoodTitle.getText();
        Assert.assertEquals(titleCatalog, goodTitle, "Товар корзины не является первым товаров на странице каталога");
    }
    @AfterMethod
    public void after() {
        driver.quit();
    }
}
