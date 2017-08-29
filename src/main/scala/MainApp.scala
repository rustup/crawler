import java.io.{FileInputStream, FileOutputStream}
import java.net.InetSocketAddress

import com.marekkadek.scrawler.crawlers.Crawler
import com.marekkadek.scrawler.crawlers._
import com.marekkadek.scraper.proxy.{HttpProxy, SocksProxy}
import fs2.Task
import fs2.Task
import fs2._
import com.marekkadek.scraper.jsoup.JsoupBrowser

import scala.concurrent.duration._
import scalaj.http._
import scalaj.http.HttpRequest
object MainApp extends App {
  override def main(args:Array[String]): Unit = {

    //val browser = JsoupBrowser[Task](proxySettings = Some(proxySettings), connectionTimeout = 10.seconds)
    val browser = JsoupBrowser[Task]()
    val crawler = new LaGouCrawler(Seq(browser))
    println(crawler.sequentialCrawl("").take(50).runLog.unsafeRun())

  }

}


//crawl cpuinfo of dell
/*    val proxySettings = SocksProxy( InetSocketAddress.createUnresolved("127.0.0.1", 1080) )
    val browser = JsoupBrowser[Task](proxySettings = Some(proxySettings), connectionTimeout = 10.seconds)
    val crawler = new NCrawler(Seq(browser))
    // crawl wikipedia sequentially and take 10 elements (titles of visited websites)
    val snList:Seq[String] = Seq("6Z8Z7C2", "CH5V7C2", "CH4V7C2", "CH2Y7C2",
      "CH2S7C2", "CH0S7C2", "BL9W7C2", "BL7Y7C2",
      "B00MHG2", "B00JHG2", "B00HHG2", "9ZZMHG2", "9ZZLHG2", "9ZSLHG2", "9ZPHHG2",
      "9ZMMHG2", "9ZGJHG2", "9ZGHHG2", "9ZFQHG2",
      "9YWMHG2", "9YWHHG2", "9YVLHG2", "9YTHHG2",
      "8PKNHG2", "8PKHHG2", "8PJKHG2", "8PGPHG2",
      "8PGMHG2", "8PFLHG2", "8PDNHG2", "8PDKHG2",
      "822PHG2", "822MHG2", "822KHG2", "822JHG2",
      "821KHG2", "820GHG2", "81ZMHG2", "81ZJHG2",
      "81YJHG2", "81YGHG2", "81XPHG2", "81XMHG2",
      "81XHHG2", "81WMHG2", "81WJHG2", "81WFHG2",
      "81VLHG2", "81VJHG2", "81VHHG2", "81TPHG2",
      "81TLHG2", "75GX7C2", "75GS7C2", "75BX7C2",
      "759X7C2", "758T7C2", "757V7C2", "71DS7C2",
      "718Z7C2", "70208C2", "700W7C2", "700V7C2",
      "6ZZV7C2", "6ZZT7C2", "6ZYW7C2", "6ZXV7C2",
      "6ZSW7C2", "6ZQW7C2", "6ZQT7C2", "6ZPZ7C2",
      "6ZPW7C2", "6ZPT7C2", "6ZNY7C2", "6ZNW7C2",
      "6ZNS7C2", "6ZN08C2", "6ZMX7C2", "6ZMT7C2",
      "6ZLX7C2", "6ZLV7C2", "6ZLT7C2", "BNSS7C2",
      "BNLY7C2", "BNJY7C2", "BNCS7C2", "BNBV7C2",
      "BN9W7C2", "BN8S7C2", "BN808C2", "BN718C2",
      "BN5Y7C2", "BMVS7C2", "BMTW7C2", "BMRS7C2",
      "BMPS7C2", "BMJT7C2", "BM7Z7C2", "BM6Y7C2",
      "BM6V7C2", "BM5X7C2", "BM1Y7C2", "BM118C2",
      "BM0T7C2", "BLZT7C2", "BLHS7C2", "BLH18C2",
      "BLGZ7C2", "BLGY7C2", "BLFV7C2", "BLDZ7C2",
      "BLDY7C2", "BLCY7C2", "BLCV7C2", "8QMNHG2",
      "8QMMHG2", "8QMKHG2", "8QMJHG2", "8QMHHG2",
      "8QLNHG2", "8QLLHG2", "8QLJHG2", "8QLGHG2",
      "8QKNHG2", "8QKKHG2", "8QKGHG2", "8QJNHG2",
      "8QHGHG2", "8QGNHG2", "8QFPHG2", "8QFLHG2",
      "8PYKHG2", "8PXPHG2", "8PXKHG2", "8PXFHG2",
      "8PWFHG2", "8PRHHG2", "8PQMHG2", "8PPPHG2",
      "8PNGHG2", "8PMKHG2", "8PLLHG2", "81TJHG2",
      "81TGHG2", "81RPHG2", "81NMHG2", "81NGHG2",
      "81MKHG2", "81KPHG2", "81JKHG2", "81JHHG2",
      "81HKHG2", "81HGHG2", "81GLHG2", "81FJHG2",
      "81DNHG2", "6ZKX7C2", "6ZKW7C2", "6ZJZ7C2",
      "6ZJY7C2", "6ZJ08C2", "6ZHX7C2", "6ZH18C2",
      "6ZG18C2", "6ZG08C2", "6ZFX7C2", "6ZFV7C2",
      "6ZF18C2", "6ZDS7C2", "6ZC08C2", "6ZB18C2",
      "6Z9V7C2", "6Z908C2", "6Z8W7C2","6Z808C2")
    println(snList.length)

    //val titles: Vector[String] = crawler.sequentialCrawl("http://www.dell.com/support/home/cn/zh/cnbsd1/product-support/servicetag/BL9Z7C2/configuration?ref=captchasuccess").take(100).runLog.unsafeRun
   var allStream = for ( sn <- snList) yield {
     try {
       crawler.sequentialCrawl(s"http://www.dell.com/support/home/cn/zh/cnbsd1/product-support/servicetag/$sn/configuration?ref=captchasuccess").take(10).runLog.unsafeRun
     } catch {
       case e: java.net.SocketTimeoutException => Vector[String]("timeout")
     }
    }


    println(allStream.head)
    import java.io._
    import java.io.BufferedWriter
    val f = new File("cpuinfo.txt" )
    val fw = new BufferedWriter( new FileWriter(f))

    snList.foreach( sn=> {
      fw.write(sn+":"+allStream.head+"\n");
      allStream = allStream.tail
    })

    fw.close()*/
