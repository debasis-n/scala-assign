import scala.io.Source
import java.io.File
import scala.collection.immutable.ListMap

object ReadSensor extends App {

  if (args.length == 0) {
    println("Pass one argument!!!")
  }
  val filepath = args(0)


  println("sensor_id" + ", min" + ", avg" + ", max")

  val src = Source.fromFile(filepath)
  val tuples = src.getLines().drop(1).map { x => val k = x.split(","); (k(0), k(1)) }
  val tupleToList = tuples.toList.filter(_._2 != "NaN").groupBy(_._1)
  val max = tupleToList.mapValues(_.map(_._2).max)
  val min = tupleToList.mapValues(_.map(_._2).min)
  val avg = tupleToList.mapValues { x => x.map(_._2.toInt).sum / x.length }

  val sortedAvg = ListMap(avg.toSeq.sortWith(_._2 > _._2):_*)
  val highestAvg = sortedAvg.head._1
  //println(min, max, avg)
  val data = min.toList ++ sortedAvg.toList ++ max.toList
  val mData = data.groupBy(_._1).map { case (k, v) => k -> v.map(_._2).toSeq }
  mData.keys.foreach { i => println(i, mData(i)) }

  println(s"Sensor with highest avg humidity: ${highestAvg}")

}