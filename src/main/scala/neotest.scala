import org.neo4j.graphdb._
import org.neo4j.graphdb.factory._

object Main {
  def main(args: Array[String]) {
    val db = new GraphDatabaseFactory()
               .newEmbeddedDatabaseBuilder("db")
               .setConfig(GraphDatabaseSettings.cache_type, "none")
               .newGraphDatabase()
    val label = DynamicLabel.label("testlabel")
    val prop = "prop"
    try {
      val tx = db.beginTx
      val idxDef = db.schema().indexCreator(label).on(prop).create
      tx.success
      tx.finish
      db.schema().awaitIndexOnline(idxDef, 20, java.util.concurrent.TimeUnit.SECONDS);
    } catch {
      case e:Exception => println(e)
    }

    var alphanumeric = new scala.util.Random(System.currentTimeMillis()).alphanumeric

    var count:Long = 0
    for(i <- 1 to 1000) {
      val tx = db.beginTx
      for(j <- 1 to 1000) {
        db.createNode(label).setProperty(prop, alphanumeric.take(10).mkString)
        alphanumeric = alphanumeric.take(10)
        count = count+1
      }
      tx.success
      tx.finish
      println("inserted: " + count)
    }

  }
}
