import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream, AvroSchema, Record, RecordFormat}





object Main extends App {
  println("Hello, World!")
  case class Composer(name: String, birthplace: String, compositions: Seq[String])

  val ennio: Composer = Composer("ennio morricone", "rome", Seq("legend of 1900", "ecstasy of gold"))
  val format: RecordFormat[Composer] = RecordFormat[Composer]
  // record is of type GenericRecord
  val record: Record = format.to(ennio)

  // is an instance of Composer
  val ennioNew = format.from(record)

  val baos = new ByteArrayOutputStream()
  val output = AvroOutputStream.binary[Composer].to(baos).build(AvroSchema[Composer])
  output.write(ennio)
  output.close()
  val bytes = baos.toByteArray

  val in = new ByteArrayInputStream(bytes)
  val input = AvroInputStream.binary[Composer].from(in).build(AvroSchema[Composer])
  val result = input.iterator.toSeq

  println(s"the result is ${result}")
//  result shouldBe Vector(ennio)

}