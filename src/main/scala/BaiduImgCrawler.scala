/**
  * Created by dove on 17/4/1.
  */
import com.marekkadek.scrawler.crawlers.Crawler
import com.marekkadek.scrawler.crawlers._
import fs2.Task
import fs2._
import com.marekkadek.scraper.jsoup.JsoupBrowser
import com.marekkadek.scraper.Document


case class Img(from :String, content :String, name :String)

case class BaiduImgCrawler(ip: String, port :String, anon:String, https :String)
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

