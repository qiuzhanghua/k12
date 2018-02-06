package com.example.k12

import com.hankcs.hanlp.HanLP
import com.hankcs.hanlp.tokenizer.StandardTokenizer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.FileOutputStream
import java.lang.Math.sqrt
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.util.CoreMap;
import java.io.PrintWriter
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation
import com.hankcs.hanlp.tokenizer.NLPTokenizer
import com.hankcs.hanlp.tokenizer.IndexTokenizer







@SpringBootApplication
class K12Application

fun main(args: Array<String>) {
  SpringApplication.run(K12Application::class.java, *args)

  // teach my daughter prime numbers
//  var primes = BooleanArray(1000, { _ -> true });
//  primes[0] = false;
//  primes[1] = false;
//  for (i in 2..sqrt(primes.size * 1.0).toInt()) {
//    if (primes[i]) {
//      var j = 2
//      while (i * j < primes.size) {
//        primes[i * j] = false
//        j++
//      }
//    }
//  }
//  for (i in 2..100) {
//    if (primes[i]) {
//      print(i)
//      print(" ")
//    }
//  }
//  println()
//  println(primes[157])
//  println(primes[191])
//

  // convert
  //   sink.file=http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/file-sink-kafka-10/1.2.0.RELEASE/file-sink-kafka-10-1.2.0.RELEASE.jar
  // to
  //   maven:org.springframework.cloud.stream.app:file-sink-kafka-10:1.2.0.RELEASE
  // source from
  // http://cloud.spring.io/spring-cloud-stream-app-starters/

  val map = HashMap<String, String>()
  // 1.2.x
//  map.put("rabbit", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Bacon.RELEASE/spring-cloud-stream-app-descriptor-Bacon.RELEASE.rabbit-apps-maven-repo-url.properties")
//  map.put("kafka-09", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Bacon.RELEASE/spring-cloud-stream-app-descriptor-Bacon.RELEASE.kafka-09-apps-maven-repo-url.properties")
//  map.put("kafka-10", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Bacon.RELEASE/spring-cloud-stream-app-descriptor-Bacon.RELEASE.kafka-10-apps-maven-repo-url.properties")

  // 1.3.x
  map.put("rabbit", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Celsius.SR1/spring-cloud-stream-app-descriptor-Celsius.SR1.rabbit-apps-maven-repo-url.properties")
  map.put("kafka-10", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Celsius.SR1/spring-cloud-stream-app-descriptor-Celsius.SR1.kafka-10-apps-maven-repo-url.properties")

  for (key in map.keys) {
    val url = map.get(key)
    val connection = URL(url).openConnection() as HttpURLConnection

    val prop0 = Properties()

    val prop1 = Properties()

    prop1.load(connection.inputStream)
    for (name in prop1.stringPropertyNames()) {
      val v: String = prop1.getProperty(name).drop("http://repo.spring.io/libs-release/".length - 5);
//      println(v)
      val ss = v.split("/");
//      println(ss)

      val sb = StringBuilder()
      val sb2 = StringBuilder()

//      sb.append("maven:")
      for (i in 0..ss.lastIndex - 3) {
        sb.append(ss[i])
        sb.append(".")
      }
      sb.deleteCharAt(sb.lastIndex)
      sb2.append("<dependency><groupId>")
      sb2.append(sb.toString())
      sb2.append("</groupId>")
      sb2.append("<artifactId>")
      sb2.append(ss[ss.lastIndex - 2])
      sb2.append("</artifactId>")
      sb2.append("<version>")
      sb2.append(ss[ss.lastIndex - 1])
      sb2.append("</version></dependency>")
      println(sb2.toString())

      sb.append(":")
      sb.append(ss[ss.lastIndex - 2])
      sb.append(":")
      sb.append(ss[ss.lastIndex - 1])
      sb.insert(0, "maven:")
      prop0.put(name, sb.toString())
    }
    val output = FileOutputStream(key + ".properties");
    prop0.store(output, "");
    connection.disconnect()
    output.close()

  }

//  val props = Properties()
//  props.setProperty("annotators",
//      "tokenize, ssplit, pos, lemma, ner, parse")
//  val pipeline = StanfordCoreNLP("CoreNLP-chinese.properties")
//  val annotation = Annotation(
//      "This is a short sentence。 And this is another。 我爱北京天安门，天安门上太阳升。")
//  pipeline.annotate(annotation);
//  val sentences = annotation.get(
//      CoreAnnotations.SentencesAnnotation::class.java)
//    if (sentences != null && sentences.size > 0) {
//      for(i in sentences.indices) {
//        val tree = sentences[i].get(TreeAnnotation::class.java)
//        val out = PrintWriter(System.out)
//        out.println("The $i sentence parsed is:")
//        tree.pennPrint(out)
//        val tokens = sentences[i].get(CoreAnnotations.TokensAnnotation::class.java)
//        println("字/词" + "\t " + "词性" + "\t " + "实体标记")
//        println("-----------------------------")
//        for (token in tokens) {
//          val word = token.getString(TextAnnotation::class.java)
//          val pos = token.getString(PartOfSpeechAnnotation::class.java)
//          val ner = token.getString(NamedEntityTagAnnotation::class.java)
//          println("$word\t $pos\t $ner")
//        }
//      }
//    }

  println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
  val termList = StandardTokenizer.segment("商品和服务")
  println(termList)
  val termList2 = NLPTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程")
  println(termList2)
  val termList3 = IndexTokenizer.segment("主副食品")
  for (term in termList3) {
    println(term.toString() + " [" + term.offset + ":" + (term.offset + term.word.length) + "]")
  }
}
