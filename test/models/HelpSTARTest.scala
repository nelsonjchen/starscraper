import java.io.InputStreamReader
import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import scala.xml._

class HelpSTARTest extends Specification {

  "The 'Hello world' string" should {
    "contain 11 characters" in {
      "Hello world" must have size (11)
    }
    "start with 'Hello'" in {
      "Hello world" must startWith("Hello")
    }
    "end with 'world'" in {
      "Hello world" must endWith("world")
    }
  }

  "File loading" should {
    "load details from getresource" in {
      "Getting detail.html from getclass" ! (null != getClass.getResource("details.html"))
    }
    "Load details as Scala XML" in {
      val saxparser = (new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl).newSAXParser()
      val inputsource = new org.xml.sax.InputSource(new InputStreamReader(getClass.getResourceAsStream("details.html")))
      val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
      adapter.loadXML(inputsource, saxparser)

      "true" ! true
    }
  }
}