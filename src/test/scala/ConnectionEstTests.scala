import com.quiz.scala.ConnectionEst
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.scalatest.funsuite.AnyFunSuite

class ConnectionEstTests extends AnyFunSuite{

  val est = new ConnectionEst
  val client: MongoClient = est.getClient(est.mongoUrl)
  val db: MongoDatabase = est.getDB(client, est.dbName)
  val collection: MongoCollection[BsonDocument] = est.getCollection(db, est.collectionName)

  test("getDB should return DataBase called 'ScalaQuiz' "){
    val received : String = db.name
    val expected : String = "ScalaQuiz"
    assert(received.equals(expected))
  }

  test("getCollection should return collection called 'Questions' ") {
    val received: String = collection.namespace.getCollectionName
    val expected: String = "Questions"
    assert(received.equals(expected))
  }

}
