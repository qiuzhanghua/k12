package com.example.k12

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.FileOutputStream
import java.lang.Math.sqrt
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

@SpringBootApplication
class K12Application

fun main(args: Array<String>) {
  SpringApplication.run(K12Application::class.java, *args)

  // teach my daughter prime numbers
  var primes = BooleanArray(1000, { _ -> true });
  primes[0] = false;
  primes[1] = false;
  for (i in 2..sqrt(primes.size * 1.0).toInt()) {
    if (primes[i]) {
      var j = 2
      while (i * j < primes.size) {
        primes[i * j] = false
        j++
      }
    }
  }
  for (i in 2..100) {
    if (primes[i]) {
      print(i)
      print(" ")
    }
  }
  println()
  println(primes[157])
  println(primes[191])


  // convert
  //   sink.file=http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/file-sink-kafka-10/1.2.0.RELEASE/file-sink-kafka-10-1.2.0.RELEASE.jar
  // to
  //   maven:org.springframework.cloud.stream.app:file-sink-kafka-10:1.2.0.RELEASE
  // source from
  // http://cloud.spring.io/spring-cloud-stream-app-starters/

  val map = HashMap<String, String>()
  map.put("rabbit", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Bacon.RELEASE/spring-cloud-stream-app-descriptor-Bacon.RELEASE.rabbit-apps-maven-repo-url.properties")
  map.put("kafka-09", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Bacon.RELEASE/spring-cloud-stream-app-descriptor-Bacon.RELEASE.kafka-09-apps-maven-repo-url.properties")
  map.put("kafka-10", "http://repo.spring.io/libs-release/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Bacon.RELEASE/spring-cloud-stream-app-descriptor-Bacon.RELEASE.kafka-10-apps-maven-repo-url.properties")

  for (key in map.keys) {
    val url = map.get(key)
    val connection = URL(url).openConnection() as HttpURLConnection

    val prop0 = Properties()

    val prop1 = Properties()

    prop1.load(connection.inputStream)
    for (name in prop1.stringPropertyNames()) {
      val v: String = prop1.getProperty(name).drop("http://repo.spring.io/libs-release/".length);
      val ss = v.split("/");
      val sb = StringBuilder()
      sb.append("maven:")
      for (i in 0..ss.lastIndex - 3) {
        sb.append(ss[i])
        sb.append(".")
      }
      sb.deleteCharAt(sb.lastIndex)
      sb.append(":")
      sb.append(ss[ss.lastIndex - 2])
      sb.append(":")
      sb.append(ss[ss.lastIndex - 1])
      prop0.put(name, sb.toString())
    }
    val output = FileOutputStream(key + ".properties");
    prop0.store(output, "");
    connection.disconnect()
    output.close()
  }
}
