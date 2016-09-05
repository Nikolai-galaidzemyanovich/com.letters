import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 * Created by user on 22.07.2016.
 */
public class LettersTest {

    WebDriver driver;

    @BeforeTest
    public void setup(){
        System.setProperty("phantomjs.binary.path","C:\\driver\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        driver = new PhantomJSDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void numberOfLettersCalculationFirstTest() throws IOException {

        driver.get("https://mail.ru/");

        // вводим имя почтового ящика
        WebElement login = driver.findElement(By.id("mailbox__login"));
        login.sendKeys("strong.zubovich");

        // создаем объект класса Select для работы с drop-down list
        Select mailDomainsDropDownList = new Select(driver.findElement(By.id("mailbox__login__domain")));

        // проверяем размер drop-down list
        assertEquals(4, mailDomainsDropDownList.getOptions().size());

        // выбираем необходимый вариант почтового ящика
        mailDomainsDropDownList.selectByValue("bk.ru");

        // работа с полем пароля
        WebElement password = driver.findElement(By.id("mailbox__password"));
        password.sendKeys("ZXCvbn123!");

        // нажатие на кнопку "Войти"
        WebElement enterButton = driver.findElement(By.id("mailbox__auth__button"));
        enterButton.click();
        File screenShot1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot1,new File("C:\\tets\\screenshot1.png"));


        /*
         Для подсчета писем в почтовом ящике сперва подсчитываем количесвто чек-боксов, затем
         отнимаем из полученного числа 1, поскольку 1 чек-бокс используется под контрол "Выделить все"
        */

//        List<WebElement> numberOfCheckBox = driver.findElements(By.className("b-checkbox__box"));
//
//        int numberOfLetters = numberOfCheckBox.size()-1;
//        System.out.println("There are " + numberOfLetters + " letters in a Box");

        int letters;
        while (true){
            letters = driver.findElements(By.cssSelector("div.b-datalist__item__panel")).size();
            System.out.println("letters " + letters);
            if (!driver.findElement(By.cssSelector("div[data-name='next']")).getAttribute("class").contains("b-toolbar__btn_disabled")) {
                driver.findElement(By.cssSelector("i.ico_toolbar_arrow_right")).click();
            } else
                break;
        }
        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot,new File("C:\\tets\\screenshot.png"));

    }


    @AfterTest
    public void teardown(){
        driver.quit();
    }
}
