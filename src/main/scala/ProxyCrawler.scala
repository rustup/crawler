import com.marekkadek.scrawler.crawlers.Crawler
import com.marekkadek.scrawler.crawlers._
import fs2.Task
import fs2._
import com.marekkadek.scraper.jsoup.JsoupBrowser
import com.marekkadek.scraper.Document
/**
  * Created by dove on 17/3/15.
  */

case class FreeProxy(ip: String, port :String, anon:String, https :String)
/*
class ProxyCrawler(browser: Seq[JsoupBrowser[Task]]) extends Crawler[Task, FreeProxy](browser) {
  override protected def onDocument(document: Document): Stream[Task, Yield[FreeProxy]] = {
    val title = YieldData(document.location)
    val tableRows = document.root.select("#proxylisttable tbody tr ")
    var td =for (row <- tableRows ; td<- row.select("td") ) yield  td
    var freeProxies = List[FreeProxy]()
    while (td.nonEmpty) {
      val ip = td.head.text
      td = td.tail
      val port = td.head.text
      td = td.tail
      val code = td.head.text
      td = td.tail
      val country = td.head.text
      td = td.tail
      val anon = td.head.text
      td = td.tail
      val google = td.head.text
      td = td.tail
      val https = td.head.text
      td = td.tail
      val lastcheck = td.head.text
      td = td.tail
      freeProxies = FreeProxy(ip, port, anon, https)::freeProxies
    }
    val fps = freeProxies.filter(x=> x.anon=="elite proxy" ).map(x=>YieldData(x)).toSeq
    Stream.emits(fps)
  }
}
*/

class GatherProxyCrawler(browser: Seq[JsoupBrowser[Task]]) extends Crawler[Task, FreeProxy](browser) {
  override protected def onDocument(document: Document): Stream[Task, Yield[FreeProxy]] = {
    val title = YieldData(document.location)
    val tableRows = document.root.select("#tblproxy")
    println(tableRows)
    var td =for (row <- tableRows ; td<- row.select("td") ) yield  td
    var freeProxies = List[FreeProxy]()
    println(td)
    while (td.nonEmpty) {
      val lastcheck = td.head.text
      td = td.tail
      val ip = td.head.text
      td = td.tail
      val port = td.head.text
      td = td.tail
      val anon = td.head.text
      td = td.tail
      val country = td.head.text
      td = td.tail
      val code = td.head.text
      td = td.tail
      val google = td.head.text
      td = td.tail
      val speed= td.head.text
      td = td.tail
      freeProxies = FreeProxy(ip, port, anon, speed)::freeProxies
    }

    val fps = freeProxies.filter(x=> x.anon=="Elite" ).map(x=>YieldData(x)).toSeq
    println(fps)
    Stream.emits(fps)
  }
}


class MipuProxyCrawler(browser: Seq[JsoupBrowser[Task]]) extends Crawler[Task, FreeProxy](browser) {
  override protected def onDocument(document: Document): Stream[Task, Yield[FreeProxy]] = {
    //port is a img  eggache
    val title = YieldData(document.location)
    val tableRows = document.root.select(".table")
    println(tableRows)

    var td =for (row <- tableRows ; td<- row.select("td") ) yield  td
    var freeProxies = List[FreeProxy]()
    println(td)
    while (td.nonEmpty) {
      val id = td.head.text
      td = td.tail
      val ip = td.head.text
      td = td.tail
      val port = td.head.text
      td = td.tail

      val proxyType = td.head.text
      td = td.tail

      val anon = td.head.text
      td = td.tail
      val country = td.head.text
      td = td.tail

      val resp = td.head.text
      td = td.tail
      val speed= td.head.text
      td = td.tail
      val validate = td.head.text
      td = td.tail
      freeProxies = FreeProxy(ip, port, anon, proxyType)::freeProxies
    }

    val fps = freeProxies.filter(x=> x.anon=="高匿" ).filter(x=>x.https=="HTTP/HTTPS").map(x=>YieldData(x)).toSeq
    Stream.emits(fps)
  }
}

class SpysProxyCrawler(browser: Seq[JsoupBrowser[Task]]) extends Crawler[Task, FreeProxy](browser) {
    //http://spys.ru/free-proxy-list/CN/
  override protected def onDocument(document: Document): Stream[Task, Yield[FreeProxy]] = {
    //port is a img  eggache
    val title = YieldData(document.location)
    val tableRows = document.root.select("table tbody table tr").filter( row=>row.children.size==9).tail
    var td =for (row <- tableRows ; td<- row.select("td") ) yield  td
    td = td.filter(x=>x.select("table").size == 0 && x.attr("colspan").getOrElse("")!="" )
    var freeProxies = List[FreeProxy]()
    //println(td)
    while (td.nonEmpty) {
      val ip = td.head.text
      td = td.tail

      val proxyType = td.head.text
      td = td.tail


      val anon = td.head.text
      td = td.tail

      val country = td.head.text
      td = td.tail

      val hostname = td.head.text
      td = td.tail

      val latency = td.head.text
      td = td.tail


      val uptime = td.head.text
      td = td.tail

      val uptime2 = td.head.text
      td = td.tail

      freeProxies = FreeProxy(ip, uptime, anon, proxyType)::freeProxies
    }

    val fps = freeProxies.filter(x=> x.anon=="HIA" ).map(x=>YieldData(x)).toSeq
    Stream.emits(fps)
  }
}

class NCrawler(browser: Seq[JsoupBrowser[Task]]) extends  Crawler[Task, String](browser) {
  override protected def onDocument(document: Document): Stream[Task, Yield[String]] = {
    //port is a img  eggache
    val title = YieldData(document.location)
    val cpuinfo = document.root.select("#subSectionB div .hide-expanded").toSeq.take(5)
   val fps = cpuinfo.map(x=>x.text).toSeq
    println(fps)
    Stream.emits(fps.map(x=>YieldData(x)))
  }
}


class LaGouCrawler(browser:Seq[JsoupBrowser[Task]]) extends Crawler[Task,String](browser) {
  override protected def onDocument(document: Document): Stream[Task, Yield[String]] = {
    //port is a img  eggache
    // a class=position_link href="http://www.baidu.com"
    val title = YieldData("")
    val urls = document.root.select(".position_link[href]").map(href=>href.attr("href").getOrElse("")).toSeq.take(20)
    Stream.emits(urls.map(x=>YieldData(x)))
  }

}
