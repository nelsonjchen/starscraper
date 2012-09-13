package models

import org.openqa.selenium.htmlunit.HtmlUnitDriver
import play.api.Play._
import play.api.http
import java.io.{InputStreamReader, InputStream}
import xml.{Node, NodeSeq}

case class Request(reference_number: Int, title: String, transactions: List[Transaction], properties: List[Property], user_defined_fields: List[UserDefinedField])

case class Transaction(who: String, department: String, time: String, memos: List[Memo])

case class Property(name:String, value: String)

case class Memo(kind: String, content: String)

case class UserDefinedField(name: String, value: String)

object HelpSTAR {
  def getTicket(id: String = "5432"): String = {
    val driver = new HtmlUnitDriver()
    driver.setJavascriptEnabled(true)
    driver.get("http://resnetservice.housing.ucsb.edu/")
    val username = current.configuration.getString("helpstar.username").getOrElse("None")
    val password = current.configuration.getString("helpstar.password").getOrElse("None")
    driver.findElementByName("txtUserName").sendKeys(username)
    driver.findElementByName("txtPassword").sendKeys(password)
    driver.findElementByName("btnLogin").click()
    driver.get("http://resnetservice.housing.ucsb.edu/hsPages/RB_RequestTemplate.aspx?requestId="+ id + "&TabTobeLoaded=tabTransactions&LoadPartially=0&Preview=1")
    val transactions_src = driver.getPageSource
    driver.get("http://resnetservice.housing.ucsb.edu/hsPages/RB_RequestTemplate.aspx?requestId="+id+"&TabTobeLoaded=tabRequestProperties")
    val details_src = driver.getPageSource
    driver.get("http://resnetservice.housing.ucsb.edu/hsPages/RB_UDFTemplate.aspx?ObjectId=" + id + "&ActiveTabIndex=0&TabTobeLoaded=tabUDFs ")
    val udf_src = driver.getPageSource
    driver.close()
    transactions_src + details_src + udf_src
  }

  def readDirtyHTMLInputSteam(is: InputStream): Node = {
    val sax_parser = (new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl).newSAXParser()
    val in = new org.xml.sax.InputSource(new InputStreamReader(is))
    val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
    adapter.loadXML(in, sax_parser)
  }

  def parseDetails(in: Node): List[Property] = {
    List[Property]()
  }

  def parseTransactions(in: Node): List[Transaction] = {
    List[Transaction]()
  }

  def parseUDF(in: Node): List[UserDefinedField] = {
    List[UserDefinedField]()
  }

}
