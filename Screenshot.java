package suporte;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import sun.plugin.com.Utils;

import javax.rmi.CORBA.Util;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class Screenshot {
    public static void tirar(WebDriver navegador, String arquivo) {
        File screenshot = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(arquivo));
        } catch (Exception e) {
            System.out.println("Houver um problema ao copiar arquivo para pasta" + e.getMessage());
        }
    }
}