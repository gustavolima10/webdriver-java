package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTestData.csv")

public class InformacoesUsuarioTests {
    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){
        navegador = Web.createChrome();

        //Clicar no Link que possui texto Sign In
        WebElement linkSignIn = navegador.findElement(By.linkText("Sign in"));
        linkSignIn.click();

        //Identificando o formulario de login
        WebElement formularioSignBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo com Name Login que esta dentro do formulario id "sign box" o texto "julio001"
        formularioSignBox.findElement(By.name("login")).sendKeys("julio0001");

        //Digitar no campo com Name Password que esta dentro do formulario id "sign box" o texto "123456"
        formularioSignBox.findElement(By.name("password")).sendKeys("123456");

        //Clicar no link com o texto "Sign In"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Validar que dentro do elemento class me esta o Txto Hi Julio
        WebElement me = navegador.findElement(By.className("me"));
        String textoNoElementoMe = me.getText();
        assertEquals("Hi, Julio", textoNoElementoMe);

        //Clicar em um link que possui a class "me"
        navegador.findElement(By.className("me")).click();

        //Clicar em um link que possui o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();

    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario (@Param(name="tipo")String tipo, @Param(name="contato")String contato, @Param(name="mensagem")String mensagemEsperada) {
        //Clicar no botão atraves do seu xpath '//div[@id='moredata']//button[@data-target='addmoredata']'
        navegador.findElement(By.xpath("//div[@id='moredata']//button[@data-target='addmoredata']")).click();

        //Identificar a pop-up onde esta o formulario de ID addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //Na combo de name "type" escolhe a opção Phone
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        //No campo de name "contact" digitar +5511999998888
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);

        //No link de text Save clicar no link de text 'save' que esta na pop-up
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        //Na mensagem de id "toast-container" validar que o texto é "Your contact has been added!"

        //assertEquals(mensagemEsperada, mensagem);
    }
        @Test
        public void removerUmContatoDeUmUsuario () {
            //Clicar no elemento pelo seu xpath '//span[text()= WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
            //        String mensagem = mensagemPop.getText();'+5511998999989']/following-sibling::a'
            navegador.findElement(By.xpath("//span[text()='+5511998999989']/following-sibling::a")).click();

            //Confirmar a janela Javascript
            navegador.switchTo().alert().accept();

            //Validar que a mensagem apresentada foi 'Rest in peace, dear phone!'
            WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
            String mensagem = mensagemPop.getText();
            assertEquals("Rest in peace, dear phone!", mensagem);

            String screenshotArquivo = "C:\\Users\\gusta\\Pictures\\Saved Pictures\\"+ Generator.dataHoraParaArquivo()+ test.getMethodName()+".png";
            Screenshot.tirar(navegador, screenshotArquivo);

            //Aguardar ate 10 segundos para que a janela desapareça
            WebDriverWait aguardar = new WebDriverWait(navegador, 10);
            aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

            //Clicar no link com o texto logout
            navegador.findElement(By.linkText("Logout")).click();


        }
        @After
        public void tearDown () {
            //Fechar o navegaador
            //navegador.quit();
        }
    }
